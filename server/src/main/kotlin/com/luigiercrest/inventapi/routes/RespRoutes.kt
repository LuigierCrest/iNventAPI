package com.luigiercrest.inventapi.routes

import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import com.luigiercrest.inventapi.repository.DispositivoRepo
import io.ktor.server.response.respond

fun Route.respRouting() {
    // Rutas para RESP limitadas al centro
    // Dispositivos
    route("/dispositivoscentro") {
        get("/{idCentro}") {
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val dispositivos = DispositivoRepo().getDispositivosByCentro(idCentro)
            call.respond(dispositivos)
        }
        // Buscar por id
        // Buscar por Nombre
        // Buscar por marca y modelo
        // Buscar por número de serie
        // Buscar por última actualización
        // Buscar por ubicación
        // Buscar por uso
        // Buscar por categoría
        // Buscar por estado
        // Buscar por idAsignacion
        // Actualizar dispositivo

    }
    // Incidencias
        // Buscar incidencias por idCentro
        // Añadir una incidencia
        // Actualizar una incidencia
        // Eliminar una incidencia
    // Servicio Técnico
        // Buscar servicios técnicos


}