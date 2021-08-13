package ru.mipt.npm.nica.condition.api

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import io.ktor.serialization.*

import kotlinx.serialization.*
import kotlinx.serialization.json.*

val URL = "jdbc:postgresql://192.168.65.52:5001/bmn_db"
val DRIVER = "org.postgresql.Driver"
val USER = "user"
val PASS = "user_pass"

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureHTTP()
        configureRouting()
        configureSerialization()
        val conn = Database.connect(URL, driver = DRIVER, user = USER, password = PASS)
        transaction {
//            RunPeriods.selectAll().forEach {
//                println(it[RunPeriods.period_number])
//            }

            RunPeriod.all().forEach {
                println(
                    Json.encodeToString(
                        SerializableRunPeriod(
                            it.id.value,
                            it.start_datetime.toString(),
                            it.end_datetime.toString()
                        )
                    )
                )
            }

        }


    }.start(wait = true)
}
