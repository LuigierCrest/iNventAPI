package com.luigiercrest.inventapi.routes

import com.luigiercrest.inventapi.models.dto.DispositivoDTO
import com.luigiercrest.inventapi.repository.DispositivoRepo
import io.ktor.server.routing.*
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.response.respondText

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
    }
}