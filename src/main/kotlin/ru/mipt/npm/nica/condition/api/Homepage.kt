package ru.mipt.npm.nica.condition.api

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.html.*
import kotlinx.html.*

fun BODY.sampleUseWidget(header: String, url: String) {
    h3 { +header }
    a(href = url) { +url }
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
                    sampleUseWidget("Period by number", "$API_ROOT_URL/period_number/7")
                    sampleUseWidget("Run by period_number and run_number", "$API_ROOT_URL/run/7/5000")
                    sampleUseWidget("Search Runs table", "$API_ROOT_URL/runs?period_number=7&run_number=5005")
                }
            }

        }
    }
}