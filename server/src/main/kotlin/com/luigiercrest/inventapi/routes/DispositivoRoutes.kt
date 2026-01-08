package com.luigiercrest.inventapi.routes

import com.luigiercrest.inventapi.models.dto.DispositivoDTO
import com.luigiercrest.inventapi.repository.DispositivoRepo
import io.ktor.server.routing.*
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond

fun Route.dispositivoRouting(){

    route("/dispositivos"){
        //GET todos los dipositivos
        get {
            val dispositivos  = DispositivoRepo().getAllDispositivos()
            if (dispositivos.isNotEmpty()) {
                call.respond(dispositivos)
            } else {
                call.respond(HttpStatusCode.OK,emptyList<DispositivoDTO>())
            }
        }
        //GET por id
        get ("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            val dispositivo = DispositivoRepo().getDispositivoById(id)
            if (dispositivo != null) {
                call.respond(dispositivo)
            } else {
                call.respond(HttpStatusCode.NotFound, "Dispositivo no encontrado")
            }
        }
        //GET por centro
        get ("/centro/{idCentro}"){
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val dispositivos = DispositivoRepo().getDispositivosByCentro(idCentro)
            call.respond(dispositivos)
        }
        //GET por Marca Modelo
        get ("/marcaModelo/{marcaModelo}"){
            val marcaModelo = call.parameters["marcaModelo"] ?: ""
            val dispositivos = DispositivoRepo().getDispositivosByMarcaModelo(marcaModelo)
            call.respond(dispositivos)
        }
        //GET por actualización
        get ("/ultimaActualizacion/{ultimaActualizacion}"){
            val ultimaActualizacion = call.parameters["ultimaActualizacion"] ?: ""
            val dispositivos = DispositivoRepo().getDispositivosByUltimaActualizacion(ultimaActualizacion)
            call.respond(dispositivos)
        }
        //GET por estado
        get ("/estado/{idEstado}"){
            val idEstado = call.parameters["idEstado"]?.toIntOrNull() ?: -1
            val dispositivos = DispositivoRepo().getDispositivosByEstado(idEstado)
            call.respond(dispositivos)
        }
        //GET por categoria
        get ("/categoria/{idCategoria}"){
            val idCategoria = call.parameters["idCategoria"]?.toIntOrNull() ?: -1
            val dispositivos = DispositivoRepo().getDispositivosByCategoria(idCategoria)
            call.respond(dispositivos)
        }
        //GET por ubicación
        get ("/ubicacion/{idUbicacion}"){
            val idUbicacion = call.parameters["idUbicacion"]?.toIntOrNull() ?: -1
            val dispositivos = DispositivoRepo().getDispositivosByUbicacion(idUbicacion)
            call.respond(dispositivos)
        }
        //GET por uso
        get ("/uso/{idUso}"){
            val idUso = call.parameters["idUso"]?.toIntOrNull() ?: -1
            val dispositivos = DispositivoRepo().getDispositivosByUso(idUso)
            call.respond(dispositivos)
        }
        //GET por asignación
        get ("/asignacion/{idAsignacion}"){
            val idAsignacion = call.parameters["idAsignacion"]?.toIntOrNull() ?: -1
            val dispositivos = DispositivoRepo().getDispositivosByAsignacion(idAsignacion)
            call.respond(dispositivos)
        }
        //POST crear dispositivo
        post {
            try {
                val dto = call.receive<DispositivoDTO>()
                DispositivoRepo().addDispositivo(dto)
                call.respond(HttpStatusCode.Created, "Dispositivo agregado correctamente")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de dispositivo inválidos")
            }
        }
        //PUT actualizar dispositivo por id
        put ("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            try {
                val dto = call.receive<DispositivoDTO>()
                val updated = DispositivoRepo().updateDispositivo(id, dto)
                if (updated) {
                    call.respond(HttpStatusCode.OK, "Dispositivo actualizado correctamente")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Dispositivo no encontrado")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de dispositivo inválidos")
            }
        }
        //DELETE dispositivo por id
        delete ("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            val deleted = DispositivoRepo().deleteDispositivo(id)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Dispositivo eliminado correctamente")
            } else {
                call.respond(HttpStatusCode.NotFound, "Dispositivo no encontrado")
            }
        }
    }
}