package ru.mipt.npm.nica.condition.api

import io.ktor.serialization.*
import io.ktor.features.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

val API_ROOT_URL = "/unidb-api/v1"

fun Application.configureAPIRouting() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        route(API_ROOT_URL) {

            get("/period_numbers") {
                val res = ArrayList<SerializableRunPeriod>()
                transaction {
                    RunPeriods.selectAll().forEach {
                        res.add(it.toSerializableRunPeriod())
                    }
                }
                call.respond(res)
            }

            get("/period_number/{period_number}") {
                val intPeriodNumber = call.parameters["period_number"]!!.toIntOrNull()
                if (intPeriodNumber == null) {
                    call.respond("Not a number input")
                } else {
                    var periodNumber: SerializableRunPeriod? = null
                    transaction {
                        periodNumber = RunPeriods.select(RunPeriods.period_number.eq(intPeriodNumber)).first().toSerializableRunPeriod()
                    }
                    call.respond(periodNumber ?: "Not found in DB")
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
//                var ops = ArrayList<Op<Boolean>>()
//
//                fun getIntParameterOperation(strParamValue: String): Op<Boolean> {
//
//                }

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

