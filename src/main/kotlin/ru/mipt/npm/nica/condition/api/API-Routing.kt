package ru.mipt.npm.nica.condition.api

import io.ktor.serialization.*
import io.ktor.features.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureAPIRouting() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        route("/unidb-api/v1") {
            get("/run_periods") {
                val res = ArrayList<SerializableRunPeriod>()
                transaction {
                    RunPeriod.all().forEach {
                        res.add(it.asSerializable())
                    }
                }
                call.respond(res)
            }

            get("/run_period/{id}") {
                val intId = call.parameters["id"]!!.toIntOrNull()
                if (intId == null) {
                    call.respond("Not a number input")
                } else {
                    var runPeriod: RunPeriod? = null
                    transaction {
                        runPeriod = RunPeriod.findById(intId)
                    }
                    call.respond(runPeriod?.asSerializable() ?: "Not found in DB")
                }
            }

            get("/runs-all") {
                val res = ArrayList<SerializableRun>()
                transaction {
                    Runs.selectAll().forEach {
                        res.add(it.toSerializableRun())
                    }
                }
                call.respond(res)
            }

            get("/runs/{period_number}/{run_number}") {
                val period_number = call.parameters["period_number"]!!.toInt()
                val run_number = call.parameters["run_number"]!!.toInt()
                val res = ArrayList<SerializableRun>()
                transaction {
                    Runs.select(
                        Runs.period_number.eq(period_number) and Runs.run_number.eq(run_number)
                    ).forEach {
                        res.add(it.toSerializableRun())
                    }
                }
                // shall it be a list or just one object?
                call.respond(res)
            }

            get("/runs") {

                var op: Op<Boolean>? = null

                val period_number = call.parameters["period_number"]?.toInt()
                if (period_number != null) {
                    op = Runs.period_number.eq(period_number)
                }

                val run_number = call.parameters["run_number"]?.toInt()
                if (run_number != null) {
                    val addOp = Runs.run_number.eq(run_number)
                    if (op == null) {
                        op = addOp
                    } else {
                        op = op and addOp
                    }
                }

                // TODO...

                if (op != null) {
                    val res = ArrayList<SerializableRun>()
                    transaction {
                        Runs.select(op).forEach {
                            res.add(it.toSerializableRun())
                        }
                    }
                    call.respond(res)
                }
            }

        }
    }

}

fun ResultRow.toSerializableRun() = SerializableRun(
    period_number = this[Runs.period_number],
    run_number = this[Runs.run_number],
    file_path = this[Runs.file_path],
    beam_particle = this[Runs.beam_particle],
    target_particle = this[Runs.target_particle],
    energy = this[Runs.energy],
    start_datetime = this[Runs.start_datetime].toString(),
    end_datetime = this[Runs.end_datetime].toString(),
    event_count = this[Runs.event_count],
    field_voltage = this[Runs.field_voltage],
    file_size = this[Runs.file_size],
    geometry_id = this[Runs.geometry_id],
    file_md5 = this[Runs.file_md5]
)