package com.luigiercrest.inventapi.routes

import com.luigiercrest.inventapi.models.dto.AsignacionCompraDTO
import com.luigiercrest.inventapi.repository.AsignacionCompraRepo
import io.ktor.server.routing.*
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond


fun Route.asignacionCompraRouting() {

    route("/asignacioncompras") {
        //GET todas asignaciones y compras
        get {
            val asignacionCompras = AsignacionCompraRepo().getAllAsignacionCompra()
            if (asignacionCompras.isNotEmpty()) {
                call.respond(asignacionCompras)
            } else {
                call.respond(HttpStatusCode.OK, emptyList<AsignacionCompraDTO>())

            }
        }
        //GET por id
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            val asignacionCompra = AsignacionCompraRepo().getAsignacionCompraById(id)
            if (asignacionCompra != null) {
                call.respond(asignacionCompra)
            } else {
                call.respond(HttpStatusCode.NotFound, "AsignacionCompra no encontrada")
            }
        }
        //GET por idCentro
        get("/centro/{idCentro}") {
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val asignacionCompras = AsignacionCompraRepo().getAsignacionCompraByCentro(idCentro)
            call.respond(asignacionCompras)
        }
        //GET por proveedor
        get("/proveedor/{idproveedor}") {
            val idProveedor = call.parameters["proveedor"]?.toIntOrNull() ?: -1
            val asignacionCompras = AsignacionCompraRepo().getAsignacionCompraByProveedor(idProveedor)
            call.respond(asignacionCompras)
        }
        //GET por fechaEntrega
        get("/fechaentrega/{fechaEntrega}") {
            val fechaEntrega = call.parameters["fechaEntrega"] ?: ""
            val asignacionCompras = AsignacionCompraRepo().getAsignacionCompraByFechaEntrega(fechaEntrega)
            call.respond(asignacionCompras)
        }
        //POST crear asignacionCompra
        post {
            try {
                val dto = call.receive<AsignacionCompraDTO>()
                AsignacionCompraRepo().addAsignacionCompra(dto)
                call.respond(HttpStatusCode.Created, "AsignacionCompra agregada correctamente")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de AsignacionCompra inválidos")
            }
        }

        //PUT actualizar asignacionCompra por id
        put("/{id}") {
            val id =
                call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(
                    HttpStatusCode.BadRequest,
                    "Id inválido"
                )
            try {
                val dto = call.receive<AsignacionCompraDTO>()
                val updated = AsignacionCompraRepo().updateAsignacionCompra(id, dto)
                if (updated) {
                    call.respond(HttpStatusCode.OK, "AsignacionCompra actualizada correctamente")
                } else {
                    call.respond(HttpStatusCode.NotFound, "AsignacionCompra no encontrada")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de AsignacionCompra inválidos")
            }
        }

        //DELETE eliminar asignacionCompra por id
        delete("/{id}") {
            val id =
                call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(
                    HttpStatusCode.BadRequest,
                    "Id inválido"
                )
            val deleted = AsignacionCompraRepo().deleteAsignacionCompra(id)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "AsignacionCompra eliminada correctamente")
            } else {
                call.respond(HttpStatusCode.NotFound, "AsignacionCompra no encontrada")
            }
        }

    }
}