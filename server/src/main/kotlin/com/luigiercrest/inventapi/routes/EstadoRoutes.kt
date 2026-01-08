package com.luigiercrest.inventapi.routes

import com.luigiercrest.inventapi.models.dto.EstadoDTO
import com.luigiercrest.inventapi.repository.EstadoRepo
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.estadoRouting() {
    route("/estados") {
        //GET todos los estados
        get {
            val estado = EstadoRepo().getAllEstados()
            if (estado.isNotEmpty()) {
                call.respond(estado)
            } else {
                call.respond(emptyList<Any>())
            }
        }
        // GET por id
        get ("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(
                "Id inválido"
            )
            val estado = EstadoRepo().getEstadoById(id)
            if (estado != null) {
                call.respond(estado)
            } else {
                call.respond(HttpStatusCode.NotFound,"Estado no encontrado")
            }

        }
        // POST crear estado
        post {
            try {
              val dto = call.receive<EstadoDTO>()
                EstadoRepo().addEstado(dto)
                call.respond(HttpStatusCode.Created,"Estado agregado correctamente")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest,"Datos de estado inválidos")
                print(e)
            }
        }
        // PUT actualizar estado por id
        put ("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            try {
                val dto = call.receive<EstadoDTO>()
                val updated = EstadoRepo().updateEstado(id, dto)
                if (updated) {
                    call.respond(HttpStatusCode.OK, "Estado actualizado correctamente")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Estado no encontrado")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de estado inválidos")
                print(e)
            }
        }
        // DELETE eliminar estado por id
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            val deleted = EstadoRepo().deleteEstado(id)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Estado eliminado correctamente")
            } else {
                call.respond(HttpStatusCode.NotFound, "Estado no encontrado")
            }
        }
    }

}