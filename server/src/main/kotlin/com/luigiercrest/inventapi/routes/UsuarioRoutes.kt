package com.luigiercrest.inventapi.routes

import com.luigiercrest.inventapi.repository.UsuarioRepo
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.*

fun Route.usuarioRouting(){

    route ("/usuarios"){
        get {
            val usuarios = UsuarioRepo().getAllUsuarios()
            if (usuarios.isNotEmpty()) {
                call.respond(usuarios)
            } else {
                call.respondText ( "No hay usuarios", status = HttpStatusCode.OK )
            }
        }

    }
}