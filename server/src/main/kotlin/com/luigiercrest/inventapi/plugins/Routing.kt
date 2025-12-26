package com.luigiercrest.inventapi.plugins

import com.luigiercrest.inventapi.routes.usuarioRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World! We are routing")
        }
        usuarioRouting()
    }
}