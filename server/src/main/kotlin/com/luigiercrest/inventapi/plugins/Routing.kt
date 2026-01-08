package com.luigiercrest.inventapi.plugins

import com.luigiercrest.inventapi.routes.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            //Comporbar el tipo de usuario a través de la sesión o token
            // responde con el tipo de usuario
            call.respondText("Conexión establecida")
        }
        usuarioRouting()
        centroRouting()
        asignacionCompraRouting()
        dispositivoRouting()
        usoRouting()
        asignacionCompraRouting()
        ubicacionRouting()
        dispositivoRouting()
        categoriaRouting()
        estadoRouting()
        incidenciaRouting()
        proveedorRouting()
        servicioTecnicoRouting()
    }
}