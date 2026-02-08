package com.luigiercrest.inventapi.routes

import com.luigiercrest.inventapi.models.dto.UsuarioDTO
import com.luigiercrest.inventapi.repository.UsuarioRepo
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.usuarioRouting(){

    route ("/usuarios"){
        //GET todos los ususarios
        get {
            val usuarios = UsuarioRepo().getAllUsuarios()
            if (usuarios.isNotEmpty()) {
                call.respond(usuarios)
            } else {
                call.respond(HttpStatusCode.OK, emptyList<UsuarioDTO>())
            }
        }
        //GET por dni
        get ("/{dni}"){
            val dni = call.parameters["dni"] ?: ""
            val usuario = UsuarioRepo().getUsuarioByDni(dni)
            if (usuario != null) {
                call.respond(usuario)
            } else{
                call.respond(HttpStatusCode.NotFound, "Usuario no encontrado")
            }
        }
        // GET por idUsuario
        //GET por centro
        get ("/centro/{idCentro}"){
            val idCentro = call.parameters["idCentro"]?.toIntOrNull() ?: -1
            val usuarios = UsuarioRepo().getUsuariosByCentro(idCentro)
            call.respond(usuarios)
        }
        //GET por nombre
        get ("/nombre/{nombre}"){
            val nombre = call.parameters["nombre"] ?: ""
            val usuarios = UsuarioRepo().getUsuariosByNombre(nombre)
            call.respond(usuarios)
        }
        //GET por apellidos
        get ("/apellidos/{apellidos}"){
            val apellidos = call.parameters["apellidos"] ?: ""
            val usuarios = UsuarioRepo().getUsuariosByApellidos(apellidos)
            call.respond(usuarios)
        }
        //GET por rol
        get ("/rol/{rol}"){
            val rol = call.parameters["rol"] ?: ""
            val usuarios = UsuarioRepo().getUsuariosByRol(rol)
            call.respond(usuarios)
        }
        // GET por departamento
        get("/departamento/{departamento}"){
            val departamento = call.parameters["departamento"] ?: ""
            val usuarios = UsuarioRepo().getUsuariosByDepartamento(departamento)
            call.respond(usuarios)
        }

        //POST crear usuario
        post {
            try {
                val dto = call.receive<UsuarioDTO>()
                UsuarioRepo().addUsuario(dto)
                call.respond(HttpStatusCode.Created, "Usuario agregado correctamente")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de usuario inválidos")
            }
        }

        //PUT actualizar usuario por dni
        put ("/{dni}"){
            val dni = call.parameters["dni"] ?: ""
            try {
                val dto = call.receive<UsuarioDTO>()
                val updated = UsuarioRepo().updateUsuario(dni, dto)
                if (updated) {
                    call.respondText("Usuario actualizado correctamente", status = HttpStatusCode.OK)
                } else {
                    call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datos de usuario inválidos")
            }
        }
        //DELETE eliminar usuario por dni
        delete ("/{dni}"){
            val dni = call.parameters["dni"] ?: ""
            val deleted = UsuarioRepo().deleteUsuario(dni)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Usuario eliminado correctamente")
            } else {
                call.respond(HttpStatusCode.NotFound, "Usuario no encontrado")
            }
        }


    }
}