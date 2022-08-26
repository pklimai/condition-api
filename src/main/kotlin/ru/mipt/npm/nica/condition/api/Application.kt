package ru.mipt.npm.nica.condition.api

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

val URL = "jdbc:postgresql://192.168.65.52:5001/bmn_db"
val DRIVER = "org.postgresql.Driver"
val USER = "user"
val PASS = "user_pass"

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureHTTP()
        configureAPIRouting()
        configureHomepage()
        Database.connect(URL, driver = DRIVER, user = USER, password = PASS)
        transaction {
//            RunPeriods.selectAll().forEach {
//                println(it[RunPeriods.id])
//            }

//            Runs.selectAll().forEach {
//                println(it[Runs.file_path])
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

//            Runs.selectAll().forEach {
//                print(
//                    Json.encodeToString(
//                        SerializableRun(
//                            period_number = it[Runs.period_number],
//                            run_number = it[Runs.run_number],
//                            file_path = it[Runs.file_path],
//                            beam_particle = it[Runs.beam_particle],
//                            target_particle = it[Runs.target_particle],
//                            energy = it[Runs.energy],
//                            start_datetime = it[Runs.start_datetime].toString(),
//                            end_datetime = it[Runs.end_datetime].toString(),
//                            event_count = it[Runs.event_count],
//                            field_voltage = it[Runs.field_voltage],
//                            file_size = it[Runs.file_size],
//                            geometry_id = it[Runs.geometry_id],
//                            file_md5 = it[Runs.file_md5]
//                        )
//                    )
//                )
//            }

        }


    }.start(wait = true)
}
