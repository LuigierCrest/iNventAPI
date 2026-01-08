package com.luigiercrest.inventapi.routes

import com.luigiercrest.inventapi.models.dto.UsoDTO
import com.luigiercrest.inventapi.repository.UsoRepo
import io.ktor.http.HttpStatusCode
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.request.receive
import io.ktor.server.response.respond

fun Route.usoRouting() {
    route("/usos") {
        //GET todos los usos
        get {
            val usos = UsoRepo().getAllUsos()
            if (usos.isNotEmpty()) {
                call.respond(usos)
            } else {
                call.respond(HttpStatusCode.OK, emptyList<UsoDTO>())
            }
        }
        // GET por id
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            val uso = UsoRepo().getUsoById(id)
            if (uso != null) {
                call.respond(uso)
            } else {
                call.respond(HttpStatusCode.NotFound, "Uso no encontrado")
            }

        }
        // POST crear uso
        post {
            try {
                val dto = call.receive<UsoDTO>()
                UsoRepo().addUso(dto)
                call.respond(HttpStatusCode.Created, "Uso agregado correctamente")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de uso inválidos")
                print(e)
            }
        }
        // PUT actualizar uso por id
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            try {
                val dto = call.receive<UsoDTO>()
                val updated = UsoRepo().updateUso(id, dto)
                if (updated) {
                    call.respond(HttpStatusCode.OK, "Uso actualizado correctamente")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Uso no encontrado")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de uso inválidos")
                print(e)
            }
        }
        // DELETE eliminar uso por id
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            val deleted = UsoRepo().deleteUso(id)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Uso eliminado correctamente")
            } else {
                call.respond(HttpStatusCode.NotFound, "Uso no encontrado")
            }
        }
    }
}
