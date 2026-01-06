package com.luigiercrest.inventapi.routes

import com.luigiercrest.inventapi.repository.AsignacionCompraRepo
import io.ktor.server.routing.*
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.response.respondText


fun Route.asignacionCompraRouting(){
    route("/asignacionCompras"){
        get{
            val asignacionCompras = AsignacionCompraRepo().getAllAsignacionCompra()
            if (asignacionCompras.isNotEmpty()) {
                call.respond(asignacionCompras)
            } else {
                call.respondText("No hay asignaciones de compra", status = HttpStatusCode.OK)

            }
        }

    }
}