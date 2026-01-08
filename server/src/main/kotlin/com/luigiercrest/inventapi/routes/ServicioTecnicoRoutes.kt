package com.luigiercrest.inventapi.routes

import com.luigiercrest.inventapi.models.dto.ServicioTecnicoDTO
import com.luigiercrest.inventapi.repository.ServicioTecnicoRepo
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.servicioTecnicoRouting(){
    route ("/serviciotecnicos"){
        // GET todos los servicios técnicos
        get {
            val serviciosTecnicos = ServicioTecnicoRepo().getAllServicioTecnicos()
            if (serviciosTecnicos.isNotEmpty()) {
                call.respond(serviciosTecnicos)
            } else {
                call.respond(HttpStatusCode.OK, emptyList<ServicioTecnicoDTO>())
            }
        }
        // GET por id
        get ("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            val servicioTecnico = ServicioTecnicoRepo().getServicioTecnicoById(id)
            if (servicioTecnico != null) {
                call.respond(servicioTecnico)
            } else {
                call.respond(HttpStatusCode.NotFound, "Servicio técnico no encontrado")
            }
        }
        // POST crear servicio técnico
        post{
            try {
                val servicioTecnico = call.receive<ServicioTecnicoDTO>()
                val nuevoServicioTecnico = ServicioTecnicoRepo().addServicioTecnico(servicioTecnico)
                call.respond(HttpStatusCode.Created, nuevoServicioTecnico)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error al crear el servicio técnico: ${e.message}")
            }
        }
        // PUT actualizar servicio técnico por id
        put ("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            try {
                val servicioTecnico = call.receive<ServicioTecnicoDTO>()
                val actualizado = ServicioTecnicoRepo().updateServicioTecnico(id, servicioTecnico)
                if (actualizado) {
                    call.respond(HttpStatusCode.OK, "Servicio técnico actualizado correctamente")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Servicio técnico no encontrado")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error al actualizar el servicio técnico: ${e.message}")
            }
        }
        // DELETE eliminar servicio técnico por id
        delete ("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            val eliminado = ServicioTecnicoRepo().deleteServicioTecnico(id)
            if (eliminado) {
                call.respond(HttpStatusCode.OK, "Servicio técnico eliminado correctamente")
            } else {
                call.respond(HttpStatusCode.NotFound, "Servicio técnico no encontrado")
            }
        }
    }
}

