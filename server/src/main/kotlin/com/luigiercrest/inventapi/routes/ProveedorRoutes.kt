package com.luigiercrest.inventapi.routes

import com.luigiercrest.inventapi.models.dto.ProveedorDTO
import com.luigiercrest.inventapi.repository.ProveedorRepo
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.proveedorRouting(){
    route("/proveedores"){
        // GET todos los proveedores
        get{
            val proveedores  = ProveedorRepo().getAllProveedores()
            if (proveedores.isNotEmpty()) {
                call.respond(proveedores)
            } else {
                call.respond(HttpStatusCode.OK, emptyList<ProveedorDTO>())
            }
        }
        // GET por id
        get ("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            val proveedor = ProveedorRepo().getProveedorById(id)
            if (proveedor != null) {
                call.respond(proveedor)
            } else {
                call.respond(HttpStatusCode.NotFound, "Proveedor no encontrado")
            }
        }
        // POST crear proveedor
        post {
            try {
                val proveedor = call.receive<ProveedorDTO>()
                val nuevoProveedor = ProveedorRepo().addProveedor(proveedor)
                call.respond(HttpStatusCode.Created, nuevoProveedor)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error al crear el proveedor: ${e.message}")
            }
        }
        // PUT actualizar proveedor por id
        put ("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            try {
                val proveedor = call.receive<ProveedorDTO>()
                val actualizado = ProveedorRepo().updateProveedor(id, proveedor)
                if (actualizado) {
                    call.respond(HttpStatusCode.OK, "Proveedor actualizado correctamente")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Proveedor no encontrado")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error al actualizar el proveedor: ${e.message}")
            }
        }

        // DELETE eliminar proveedor por id
        delete ("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            try {
                val eliminado = ProveedorRepo().deleteProveedor(id)
                if (eliminado) {
                    call.respond(HttpStatusCode.OK, "Proveedor eliminado correctamente")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Proveedor no encontrado")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error al eliminar el proveedor: ${e.message}")
            }
        }
    }
}