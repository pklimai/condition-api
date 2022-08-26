package ru.mipt.npm.nica.condition.api

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.html.*
import kotlinx.html.*

fun BODY.sampleUseWidget(header: String, url: String) {
    h3 { +header }
    a(href = url) { +url }
}

fun BODY.sampleUseWidget(header: String, url: List<String>) {
    h3 { +header }
    url.forEach {
        p {
            a(href = it) { +it }
        }
    }
}

fun Application.configureHomepage() {
    routing {
        get("/") {
            call.respondHtml {
                head {
                    title("API Explorer")
                }
                body {
                    h1 { +"Condition DB API Explorer" }
                    sampleUseWidget("All periods", "$API_ROOT_URL/period_numbers")
                    sampleUseWidget("Period by period_number", "$API_ROOT_URL/period_number/7")
                    sampleUseWidget("Run by period_number and run_number", "$API_ROOT_URL/run/7/5000")
                    sampleUseWidget("All runs", "$API_ROOT_URL/runs")
                    sampleUseWidget(
                        "Search Runs table examples", listOf(
                            "$API_ROOT_URL/runs?period_number=7&run_number=5000-5005",
                            "$API_ROOT_URL/runs?run_number=3970-4000&file_size=50000000000+&beam_particle=Ar&target_particle=C"
                        )
                    )
                }
            }

        }
    }
}