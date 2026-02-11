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
        //GET por nombre
        get("/nombre/{nombre}"){
            val nombre = call.parameters["nombre"] ?: ""
            val dispositivos = DispositivoRepo().getDispositivosByNombre(nombre)
            call.respond(dispositivos)
        }
        //GET por número de serie
        get("/numserie/{numSerie}"){
            val numSerie = call.parameters["numSerie"] ?: ""
            val dispositivos = DispositivoRepo().getDispositivosByNumSerie(numSerie)
            call.respond(dispositivos)
        }
        //GET por centro
        get ("/centro/{idCentro}"){
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val dispositivos = DispositivoRepo().getDispositivosByCentro(idCentro)
            call.respond(dispositivos)
        }
        //GET por Marca Modelo
        get ("/marcamodelo/{marcaModelo}"){
            val marcaModelo = call.parameters["marcaModelo"] ?: ""
            val dispositivos = DispositivoRepo().getDispositivosByMarcaModelo(marcaModelo)
            call.respond(dispositivos)
        }
        //GET por actualización
        get ("/ultimaactualizacion/{ultimaActualizacion}"){
            val ultimaActualizacion = call.parameters["ultimaActualizacion"] ?: ""
            val dispositivos = DispositivoRepo().getDispositivosByUltimaActualizacion(ultimaActualizacion)
            call.respond(dispositivos)
        }
        //GET por estado
        get ("/estado/{estado}"){
            val estado = call.parameters["estado"] ?: ""
            val dispositivos = DispositivoRepo().getDispositivosByEstado(estado)
            call.respond(dispositivos)
        }
        //GET por categoria
        get ("/categoria/{categoria}"){
            val categoria = call.parameters["categoria"]?: ""
            val dispositivos = DispositivoRepo().getDispositivosByCategoria(categoria)
            call.respond(dispositivos)
        }
        //GET por ubicación
        get ("/ubicacion/{ubicacion}"){
            val ubicacion = call.parameters["ubicacion"]?: ""
            val dispositivos = DispositivoRepo().getDispositivosByUbicacion(ubicacion)
            call.respond(dispositivos)
        }
        //GET por uso
        get ("/uso/{uso}"){
            val uso = call.parameters["uso"]?: ""
            val dispositivos = DispositivoRepo().getDispositivosByUso(uso)
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
                println(e)
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
                println(e)
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