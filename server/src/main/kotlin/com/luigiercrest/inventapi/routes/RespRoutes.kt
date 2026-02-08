package com.luigiercrest.inventapi.routes

import com.luigiercrest.inventapi.models.dto.DispositivoDTO
import com.luigiercrest.inventapi.models.dto.IncidenciaDTO
import com.luigiercrest.inventapi.models.dto.ServicioTecnicoDTO
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import com.luigiercrest.inventapi.repository.DispositivoRepo
import com.luigiercrest.inventapi.repository.IncidenciaRepo
import com.luigiercrest.inventapi.repository.ServicioTecnicoRepo
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.ContentTransformationException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.put

fun Route.respRouting() {
    // Rutas para RESP limitadas al centro
    // Dispositivos
    route("/dispositivoscentro") {
        get("/{idCentro}") {
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val dispositivos = DispositivoRepo().getDispositivosByCentro(idCentro)
            call.respond(dispositivos)
        }
        // Buscar en el centro por id
        get("/{idCentro}/id/{id}") {
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val id = call.parameters["id"]?.toIntOrNull() ?: -1
            val dispositivo = DispositivoRepo().getDispositivoByCentroAndId(idCentro, id)
            if (dispositivo != null) {
                call.respond(dispositivo)
            } else {
                call.respond(HttpStatusCode.NotFound,"Dispositivo no encontrado en el centro")
            }
        }
        // Buscar en el centro por nombre
        get("/{idCentro}/nombre/{nombre}") {
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val nombre = call.parameters["nombre"] ?: ""
            val dispositivos = DispositivoRepo().getDispositivosByCentroAndNombre(idCentro, nombre)
            call.respond(dispositivos)
        }
        // Buscar en el centro por número de serie
        get("/{idCentro}/numserie/{numSerie}") {
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val numSerie = call.parameters["numSerie"] ?: ""
            val dispositivos = DispositivoRepo().getDispositivosByCentroAndNumSerie(idCentro, numSerie)
            call.respond(dispositivos)
        }
        // Buscar en el centro por marca y modelo
        get("/{idCentro}/marcamodelo/{marcaModelo}") {
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val marcaModelo = call.parameters["marcaModelo"] ?: ""
            val dispositivos = DispositivoRepo().getDispositivosByCentroAndMarcaModelo(idCentro, marcaModelo)
            call.respond(dispositivos)
        }
        // Buscar en el centro por última actualización
        get("/{idCentro}/actualizacion/{fechaActualizacion}") {
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val fechaActualizacion = call.parameters["fechaActualizacion"] ?: ""
            val dispositivos = DispositivoRepo().getDispositivosByCentroAndFechaActualizacion(idCentro, fechaActualizacion)
            call.respond(dispositivos)
        }
        // Buscar en el centro por ubicación
        get("/{idCentro}/ubicacion/{ubicacion}") {
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val ubicacion = call.parameters["ubicacion"] ?: ""
            val dispositivos = DispositivoRepo().getDispositivosByCentroAndUbicacion(idCentro, ubicacion)
            call.respond(dispositivos)
        }
        // Buscar en el centro por uso
        get("/{idCentro}/uso/{uso}") {
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val uso = call.parameters["uso"] ?: ""
            val dispositivos = DispositivoRepo().getDispositivosByCentroAndUso(idCentro, uso)
            call.respond(dispositivos)
        }
        // Buscar en el centro por categoría
        get("/{idCentro}/categoria/{categoria}") {
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val categoria = call.parameters["categoria"] ?: ""
            val dispositivos = DispositivoRepo().getDispositivosByCentroAndCategoria(idCentro, categoria)
            call.respond(dispositivos)
        }
        // Buscar en el centro por estado
        get("/{idCentro}/estado/{estado}") {
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val estado = call.parameters["estado"] ?: ""
            val dispositivos = DispositivoRepo().getDispositivosByCentroAndEstado(idCentro, estado)
            call.respond(dispositivos)
        }
        // Buscar en el centro por idAsignacion
        get("/{idCentro}/asignacion/{idAsignacion}") {
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val idAsignacion = call.parameters["idAsignacion"]?.toIntOrNull() ?: -1
            val dispositivos = DispositivoRepo().getDispositivosByCentroAndAsignacion(idCentro, idAsignacion)
            call.respond(dispositivos)
        }
        // Actualizar dispositivo del centro
        put("/{idCentro}/actualizar/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido")
            try {
                val dispositivoActualizado = call.receive<DispositivoDTO>()
                val resultado = DispositivoRepo().updateDispositivo(id, dispositivoActualizado)
                if (resultado) {
                    call.respond("Dispositivo actualizado correctamente")
                } else {
                    call.respond("No se pudo actualizar el dispositivo")
                }
            } catch (e: Exception) {
                call.respond("Datos de dispositivo inválidos")
            }
        }


    }
    // Incidencias
    route("/incidenciascentro") {
        // Buscar incidencias por idCentro
        get("/{idCentro}") {
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val incidencias = IncidenciaRepo().getIncidenciasByCentro(idCentro)
            call.respond(incidencias)
        }
        get("/{idCentro}/dispositivo/{idDispositivo}") {
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val idDispositivo = call.parameters["idDispositivo"]?.toIntOrNull() ?: -1
            val incidencias = IncidenciaRepo().getIncidenciasByCentroAndDispositivo(idCentro, idDispositivo)
            call.respond(incidencias as Any)
        }

        // Actualizar una incidencia por id de incidencia
        put("/actualizar/{idIncidencia}") {
            val idIncidencia = call.parameters["idIncidencia"]?.toIntOrNull() ?: return@put call.respond(
                HttpStatusCode.BadRequest,
                "Id de incidencia inválido")
            try {
                val incidenciaActualizada = call.receive<IncidenciaDTO>()
                val resultado = IncidenciaRepo().updateIncidencia(idIncidencia, incidenciaActualizada)
                if (resultado) {
                    call.respond(HttpStatusCode.OK,"Incidencia actualizada correctamente")
                } else {
                    call.respond(HttpStatusCode.BadRequest,"No se pudo actualizar la incidencia")
                }
            } catch (e: ContentTransformationException){
                call.respond(HttpStatusCode.BadRequest,"Formato de datos de incidencia inválido")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError,"Datos de incidencia inválidos")
            }
        }

        // Añadir una incidencia
        post("/{idCentro}/nuevaincidencia") {
            try {
                val nuevaIncidencia = call.receive<IncidenciaDTO>()
                IncidenciaRepo().addIncidencia(nuevaIncidencia)
                call.respond(HttpStatusCode.Created, "Incidencia agregada correctamente")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de incidencia inválidos")
            }
        }

    }

    // Servicio Técnico
        // Buscar servicios técnicos
    route("/serviciostecnicos"){
        get {
            val serviciosTecnicos = ServicioTecnicoRepo().getAllServicioTecnicos()
            if (serviciosTecnicos.isNotEmpty()) {
                call.respond(serviciosTecnicos)
            } else {
                call.respond(HttpStatusCode.OK, emptyList<ServicioTecnicoDTO>())
            }
        }

    }


}