package com.luigiercrest.inventapi.routes

import com.luigiercrest.inventapi.models.dto.CentroDTO
import com.luigiercrest.inventapi.repository.CentroRepo
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route


fun Route.centroRouting() {

    route("/centros"){
        //GET todos
        get {
            val centros = CentroRepo().getAllCentros()
            if (centros.isNotEmpty()) {
                call.respond(centros)
            } else {
                call.respond(HttpStatusCode.OK, emptyList<CentroDTO>())
            }
        }
        // GET id
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?:
                return@get call.respond(HttpStatusCode.BadRequest, "Id invalido")
            val centro = CentroRepo().getCentrosById(id)
            if (centro != null) {
                call.respond(centro)
            } else{
                call.respond(HttpStatusCode.NotFound, "Centro no encontrado")
            }

        }

        //GET por municipio
        get("/municipio/{municipio}"){
            val municipio = call.parameters["municipio"] ?: ""
            val centros = CentroRepo().getCentrosByMunicipio(municipio)
            call.respond(centros)
        }

        //GET por tipo
        get("/tipo/{tipo}"){
            val tipo = call.parameters["tipo"] ?: ""
            val centros = CentroRepo().getCentrosByTipo(tipo)
            call.respond(centros)
        }

        //POST
        post {
            try {
                val dto = call.receive<CentroDTO>()
                CentroRepo().addCentro(dto)
                call.respond(HttpStatusCode.Created, "Centro agregado correctamente")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de centro inválidos")
            }
        }

        //PUT
        put("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull() ?:
            return@put call.respond(HttpStatusCode.BadRequest, "Id inválido")
            try {
                val dto = call.receive<CentroDTO>()
                val updated = CentroRepo().updateCentro(id, dto)
                if (updated) {
                    call.respond(HttpStatusCode.OK, "Centro actualizado correctamente")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Centro no encontrado")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de centro inválidos")
            }
        }

        //DELETE
        delete("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull() ?:
                return@delete call.respond(HttpStatusCode.BadRequest, "Id inválido")
            val eliminated = CentroRepo().deleteCentro(id)
            if (eliminated) {
                call.respond(HttpStatusCode.OK, "Centro eliminado correctamente")
            } else {
                call.respond(HttpStatusCode.NotFound, "Centro no encontrado")
            }
        }
    }
}