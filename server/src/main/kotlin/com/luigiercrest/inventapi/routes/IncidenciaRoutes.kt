package com.luigiercrest.inventapi.routes

import com.luigiercrest.inventapi.models.dto.IncidenciaDTO
import com.luigiercrest.inventapi.repository.IncidenciaRepo
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.incidenciaRouting() {
    route("/incidencias") {
        //GET todas las incidencias
        get {
            val incidencias = IncidenciaRepo().getAllIncidencias()
            if (incidencias.isNotEmpty()) {
                call.respond(incidencias)
            } else {
                call.respond(HttpStatusCode.OK, emptyList<IncidenciaDTO>())
            }
        }
        //GET por id
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            val incidencia = IncidenciaRepo().getIncidenciaById(id)
            if (incidencia != null) {
                call.respond(incidencia)
            } else {
                call.respond(HttpStatusCode.NotFound, "Incidencia no encontrada")
            }
        }
        //GET por centro
        get("/centro/{idCentro}") {
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val incidencias = IncidenciaRepo().getIncidenciasByCentro(idCentro)
            call.respond(incidencias)
        }
        //GET por dispositivo
        get("/dispositivo/{idDispositivo}") {
            val idDispositivo = call.parameters["idDispositivo"]?.toIntOrNull() ?: -1
            val incidencias = IncidenciaRepo().getIncidenciasByDispositivo(idDispositivo)
            call.respond(incidencias)
        }
        //GET por servicio técnico
        get("/serviciotecnico/{idServicioTecnico}") {
            val idServicioTecnico = call.parameters["idServicioTecnico"]?.toIntOrNull() ?: -1
            val incidencias = IncidenciaRepo().getIncidenciasByServicioTecnico(idServicioTecnico)
            call.respond(incidencias)
        }
        //GET por responsable
        get("/responsable/{dniResponsable}") {
            val dniResponsable = call.parameters["dniResponsable"] ?: ""
            val incidencias = IncidenciaRepo().getIncidenciasByResponsable(dniResponsable)
            call.respond(incidencias)
        }
        //GET por estado
        get("/estado/{estado}") {
            val estado = call.parameters["estado"] ?: ""
            val incidencias = IncidenciaRepo().getIncidenciasByEstado(estado)
            call.respond(incidencias)
        }
        //GET por fecha de reporte
        get("/fechareporte/{fechaReporte}") {
            val fechaReporte = call.parameters["fechaReporte"] ?: ""
            val incidencias = IncidenciaRepo().getIncidenciasByFechaReporte(fechaReporte)
            call.respond(incidencias)
        }
        //GET por fecha de cierre
        get("/fechacierre/{fechaCierre}") {
            val fechaCierre = call.parameters["fechaCierre"] ?: ""
            val incidencias = IncidenciaRepo().getIncidenciasByFechaCierre(fechaCierre)
            call.respond(incidencias)
        }
        // POST crear incidencia
        post {
            try {
                val dto = call.receive<IncidenciaDTO>()
                IncidenciaRepo().addIncidencia(dto)
                call.respond(HttpStatusCode.Created, "Incidencia agregada correctamente")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de incidencia inválidos")
                print(e)
            }
        }
        // PUT actualizar incidencia por id
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            try {
                val dto = call.receive<IncidenciaDTO>()
                val updated = IncidenciaRepo().updateIncidencia(id, dto)
                if (updated) {
                    call.respond(HttpStatusCode.OK, "Incidencia actualizada correctamente")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Incidencia no encontrada")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de incidencia inválidos")
                print(e)
            }
        }
        // PUT actualizar estado de incidencia por id
        put("/estado/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            try {
                val dto = call.receive<String>()
                val updated = IncidenciaRepo().updateEstadoIncidencia(id, dto)
                if (updated) {
                    call.respond(HttpStatusCode.OK, "Estado de incidencia actualizado correctamente")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Incidencia no encontrada")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de incidencia inválidos")
                print(e)
            }
        }
        // DELETE eliminar incidencia por id
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            val deleted = IncidenciaRepo().deleteIncidencia(id)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Incidencia eliminada correctamente")
            } else {
                call.respond(HttpStatusCode.NotFound, "Incidencia no encontrada")
            }
        }
    }
}