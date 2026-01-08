package com.luigiercrest.inventapi.routes

import com.luigiercrest.inventapi.models.dto.UbicacionDTO
import com.luigiercrest.inventapi.repository.UbicacionRepo
import io.ktor.http.HttpStatusCode
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.request.receive
import io.ktor.server.response.respond

fun Route.ubicacionRouting(){
    route("/ubicaciones"){
        //GET todos las ubicaciones
        get {
            val ubicaciones  = UbicacionRepo().getAllUbicaciones()
            if (ubicaciones.isNotEmpty()) {
                call.respond(ubicaciones)
            } else {
                call.respond(HttpStatusCode.OK, emptyList<UbicacionDTO>())
            }
        }
        // GET por id
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?:
                return@get call.respond(HttpStatusCode.BadRequest, "Id inválido")
            val ubicacion = UbicacionRepo().getUbicacionById(id)
            if (ubicacion != null) {
                call.respond(ubicacion)
            } else{
                call.respond(HttpStatusCode.NotFound, "Ubicación no encontrada")
            }

        }
        // POST crear ubicacion
        post {
            try {
                val dto = call.receive<UbicacionDTO>()
                UbicacionRepo().addUbicacion(dto)
                call.respond(HttpStatusCode.Created, "Ubicación agregada correctamente")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de ubicación inválidos")
            }
        }
        // PUT actualizar ubicacion por id
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?:
                return@put call.respond(HttpStatusCode.BadRequest, "Id inválido")
            try {
                val dto = call.receive<UbicacionDTO>()
                val updated = UbicacionRepo().updateUbicacion(id, dto)
                if (updated) {
                    call.respond(HttpStatusCode.OK, "Ubicación actualizada correctamente")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Ubicación no encontrada")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de ubicación inválidos")
            }
        }
        // DELETE eliminar ubicacion por id
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?:
                return@delete call.respond(HttpStatusCode.BadRequest, "Id inválido")
            val deleted = UbicacionRepo().deleteUbicacion(id)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Ubicación eliminada correctamente")
            } else {
                call.respond(HttpStatusCode.NotFound, "Ubicación no encontrada")
            }
        }
    }
}