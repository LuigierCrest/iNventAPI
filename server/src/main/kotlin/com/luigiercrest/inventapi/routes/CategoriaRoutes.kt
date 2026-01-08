package com.luigiercrest.inventapi.routes

import com.luigiercrest.inventapi.models.dto.CategoriaDTO
import com.luigiercrest.inventapi.repository.CategoriaRepo
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.categoriaRouting() {
    route("/categorias") {
        //GET todas las categorias
        get {
            val categorias = CategoriaRepo().getAllCategorias()
            if (categorias.isNotEmpty()) {
                call.respond(categorias)
            } else {
                call.respond(HttpStatusCode.OK, emptyList<CategoriaDTO>())
            }
        }
        // GET por id
        get ("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            val categoria = CategoriaRepo().getCategoriaById(id)
            if (categoria != null) {
                call.respond(categoria)
            } else {
                call.respond(HttpStatusCode.NotFound, "Categoria no encontrada")
            }
        }
        // POST crear categoria
        post {
            try{
                val dto = call.receive<CategoriaDTO>()
                CategoriaRepo().addCategoria(dto)
                call.respond(HttpStatusCode.Created, "Categoria agregada correctamente")
            } catch (e: Exception){
                call.respond(HttpStatusCode.BadRequest, "Datos de categoria inválidos")
                print(e)
            }
        }
        // PUT actualizar categoria por id
        put {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            try {
                val dto = call.receive<CategoriaDTO>()
                val updated = CategoriaRepo().updateCategoria(id, dto)
                if (updated) {
                    call.respond(HttpStatusCode.OK, "Categoria actualizada correctamente")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Categoria no encontrada")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de categoria inválidos")
            }
        }
        // DELETE categoria por id
        delete {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(
                HttpStatusCode.BadRequest,
                "Id inválido"
            )
            val deleted = CategoriaRepo().deleteCategoria(id)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Categoria eliminada correctamente")
            } else {
                call.respond(HttpStatusCode.NotFound, "Categoria no encontrada")
            }
        }
    }

}