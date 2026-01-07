package com.luigiercrest.inventapi.routes

import com.luigiercrest.inventapi.models.dto.AsignacionCompraDTO
import com.luigiercrest.inventapi.repository.AsignacionCompraRepo
import io.ktor.server.routing.*
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.response.respondText


fun Route.asignacionCompraRouting(){

    route("/asignacioncompras"){
        //GET todas asignaciones y compras
        get{
            val asignacionCompras = AsignacionCompraRepo().getAllAsignacionCompra()
            if (asignacionCompras.isNotEmpty()) {
                call.respond(asignacionCompras)
            } else {
                call.respond(HttpStatusCode.OK,emptyList<AsignacionCompraDTO>())

            }
        }


    }
}