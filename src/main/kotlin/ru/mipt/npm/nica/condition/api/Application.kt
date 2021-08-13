package ru.mipt.npm.nica.condition.api

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

val URL = "jdbc:postgresql://192.168.65.52:5001/bmn_db"
val DRIVER = "org.postgresql.Driver"
val USER = "user"
val PASS = "user_pass"

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureHTTP()
        configureAPIRouting()
        Database.connect(URL, driver = DRIVER, user = USER, password = PASS)
        transaction {
//            RunPeriods.selectAll().forEach {
//                println(it[RunPeriods.id])
//            }
//            RunPeriod.all().forEach {
//                println(
//                    Json.encodeToString(
//                        SerializableRunPeriod(
//                            it.id.value,
//                            it.start_datetime.toString(),
//                            it.end_datetime.toString()
//                        )
//                    )
//                )
//            }

        }


    }.start(wait = true)
}
