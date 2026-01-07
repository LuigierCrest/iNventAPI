package com.luigiercrest.inventapi.plugins

import com.luigiercrest.inventapi.routes.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Conexión establecida")
        }
        usuarioRouting()
        centroRouting()
        asignacionCompraRouting()
        dispositivoRouting()
    }
}