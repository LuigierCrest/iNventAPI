package com.luigiercrest.inventapi.routes

import com.luigiercrest.inventapi.entities.Usuario
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.*

private val usuarios = mutableListOf(
    Usuario(1, "admin", "luis@mail.com"),
    Usuario(2, "dire", "dire@mail.com"),
)

fun Route.usuarioRouting(){
    route ("/usuario"){
        get {
            if (usuarios.isNotEmpty()) {
                call.respond("usuarios")
            } else {
                call.respondText ( "No hay usuarios", status = HttpStatusCode.OK )
            }
        }
    }
}