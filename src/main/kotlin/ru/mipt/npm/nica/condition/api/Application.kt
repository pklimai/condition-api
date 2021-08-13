package ru.mipt.npm.nica.condition.api

import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureHTTP()
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
