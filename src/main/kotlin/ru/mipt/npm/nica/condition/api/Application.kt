package ru.mipt.npm.nica.condition.api

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

val DRIVER = "org.postgresql.Driver"
val API_ROOT_URL = System.getenv("CDBAPI_API_ROOT_URL") ?: "/unidb-api/v1"
val USER = System.getenv("CDBAPI_PSQL_USER") ?: "user"
val PASS = System.getenv("CDBAPI_PSQL_PASSWORD") ?: "user_pass"
val URL = System.getenv("CDBAPI_PSQL_URL") ?: "jdbc:postgresql://192.168.65.52:5001/bmn_db"
val PORT = System.getenv("CDBAPI_LISTEN_PORT")?.toInt() ?: 8080

fun main() {
    embeddedServer(Netty, port = PORT, host = "0.0.0.0") {
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
