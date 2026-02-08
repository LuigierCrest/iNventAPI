package com.luigiercrest.inventapi.routes

import com.luigiercrest.inventapi.models.dto.UsuarioDTO
import com.luigiercrest.inventapi.repository.UsuarioRepo
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.log
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.direRouting() {
    // Las rutas relacionadas con DIRE están limitadas a su centro
    route("/usuarioscentro") {
        // GET usuarios por centro
        get ("/{idCentro}"){
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val usuarios = UsuarioRepo().getUsuariosByCentro(idCentro)
            call.respond(usuarios)
        }
        // POST crear usuario
        post("/nuevousuario") {

            try {
                println("[DIRE] Recibido nuevo usuario: ${call.request}")
                val dto = call.receive<UsuarioDTO>()
                println("[DIRE] Recibido nuevo usuario: $dto")
                UsuarioRepo().addUsuario(dto)
                call.respond(HttpStatusCode.Created, "Usuario agregado correctamente")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de usuario inválidos")
            }
        }
        // PUT actualizar usuario
        put ("/{dni}"){
            val dni = call.parameters["dni"] ?: ""
            try {
                val dto = call.receive<UsuarioDTO>()
                val updated = UsuarioRepo().updateUsuario(dni, dto)
                if (updated) {
                    call.respondText("Usuario actualizado correctamente", status = HttpStatusCode.OK)
                } else {
                    call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de usuario inválidos")
            }
        }
        //DELETE eliminar usuario por dni, desde el cliente se manda el idCentro automáticamente
        delete ("/{dni}"){
            val dni = call.parameters["dni"] ?: ""
            val deleted = UsuarioRepo().deleteUsuario(dni)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Usuario eliminado correctamente")
            } else {
                call.respond(HttpStatusCode.NotFound, "Usuario no encontrado")
            }
        }
    }
}