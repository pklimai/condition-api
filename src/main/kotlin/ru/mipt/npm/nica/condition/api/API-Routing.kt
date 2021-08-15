package ru.mipt.npm.nica.condition.api

import io.ktor.serialization.*
import io.ktor.features.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

const val API_ROOT_URL = "/unidb-api/v1"

fun Application.configureAPIRouting() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        route(API_ROOT_URL) {

            // Plural - returns all
            get("/period_numbers") {
                val res = ArrayList<RunPeriod>()
                transaction {
                    RunPeriods.selectAll().forEach {
                        res.add(it.toRunPeriod())
                    }
                }
                call.respond(res)
            }

            // Single - return one
            get("/period_number/{period_number}") {
                val intPeriodNumber = call.parameters["period_number"]!!.toIntOrNull()
                if (intPeriodNumber == null) {
                    call.respond("Not a number input")
                } else {
                    var periodNumber: RunPeriod? = null
                    transaction {
                        periodNumber = RunPeriods.select(RunPeriods.period_number.eq(intPeriodNumber)).first()
                            .toRunPeriod()
                    }
                    call.respond(periodNumber ?: "Not found in DB")
                }
            }

            // Create one period
            post("/period-number") {
                val newPeriod = call.receive<RunPeriod>()
                try {
                    val st = transaction {
                        RunPeriods.insert {
                            it[period_number] = newPeriod.period_number
                            it[start_datetime] = DateTime.parse(newPeriod.start_datetime)
                            it[end_datetime] = DateTime.parse(newPeriod.end_datetime)
                        }
                    }
                    if (st.resultedValues?.size == 1) {
                        call.respond(HttpStatusCode.OK)
                    } else {
                        call.respond(HttpStatusCode.Conflict)  // never works...
                    }
                } catch (_: ExposedSQLException) { }
                call.respond(HttpStatusCode.Conflict)
            }

            // Single - returns one
            get("/run/{period_number}/{run_number}") {
                val period_number = call.parameters["period_number"]!!.toInt()
                val run_number = call.parameters["run_number"]!!.toInt()
                var res: Run? = null
                transaction {
                    res = Runs.select(Runs.period_number.eq(period_number) and Runs.run_number.eq(run_number))
                        .first().toRun()
                }
                if (res != null) {
                    call.respond(res!!)
                }
            }

            // Plural - return multiple with search
            // Shall we return all when no criteria specified?
            get("/runs") {
                val ops = ArrayList<Op<Boolean>>()
                intParameterOperation(call.parameters["period_number"], Runs.period_number)?.let { ops.add(it) }
                intParameterOperation(call.parameters["run_number"], Runs.run_number)?.let { ops.add(it) }
                // skip file_path
                stringParameterOperation(call.parameters["beam_particle"], Runs.beam_particle)?.let { ops.add(it) }
                stringParameterOperation(call.parameters["target_particle"], Runs.target_particle)?.let { ops.add(it) }
                doubleParameterOperation(call.parameters["energy"], Runs.energy)?.let { ops.add(it) }
                // skip start_datetime
                // skip end_datetime
                intParameterOperation(call.parameters["event_count"], Runs.event_count)?.let { ops.add(it) }
                doubleParameterOperation(call.parameters["field_voltage"], Runs.field_voltage)?.let { ops.add(it) }
                longParameterOperation(call.parameters["file_size"], Runs.file_size)?.let { ops.add(it) }
                intParameterOperation(call.parameters["geometry_id"], Runs.geometry_id)?.let { ops.add(it) }
                // skip file_md5

                if (ops.isNotEmpty()) {
                    val op = ops.reduce { a, b -> a and b }
                    val res = ArrayList<Run>()
                    transaction {
                        Runs.select(op).forEach {
                            res.add(it.toRun())
                        }
                    }
                    call.respond(res)
                } else {
                    // Return all - shall we do it?
                    val res = ArrayList<Run>()
                    transaction {
                        Runs.selectAll().forEach {
                            res.add(it.toRun())
                        }
                    }
                    call.respond(res)
                }
            }

        }
    }

}

