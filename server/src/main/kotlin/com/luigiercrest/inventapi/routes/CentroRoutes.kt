package com.luigiercrest.inventapi.routes

import com.luigiercrest.inventapi.plugins.DatabaseFactory.init
import com.luigiercrest.inventapi.repository.CentroRepo
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import io.ktor.server.response.respondText
import org.jetbrains.exposed.sql.Database


fun Route.centroRouting() {

    route("/centros"){
        get {
            val centros = CentroRepo().getAllCentros()
            if (centros.isNotEmpty()) {
                call.respond(centros)
            } else {
                call.respondText("No hay centros", status = HttpStatusCode.OK)
            }
        }
        //POST
        //PUT
        //DELETE
    }
}