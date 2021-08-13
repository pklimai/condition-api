package ru.mipt.npm.nica.condition.api

import io.ktor.serialization.*
import io.ktor.features.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureSerialization() {
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
        }
    }

}
