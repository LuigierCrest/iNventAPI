package com.luigiercrest.inventapi.plugins

import com.luigiercrest.inventapi.routes.asignacionCompraRouting
import com.luigiercrest.inventapi.routes.centroRouting
import com.luigiercrest.inventapi.routes.usuarioRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Conexión establecida")
        }
        usuarioRouting()
        centroRouting()
        asignacionCompraRouting()
    }
}