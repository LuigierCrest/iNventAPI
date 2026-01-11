package com.luigiercrest.inventapi.plugins

import com.luigiercrest.inventapi.jwtconfig
import com.luigiercrest.inventapi.models.LoginRequest
import com.luigiercrest.inventapi.models.LoginResponse
import com.luigiercrest.inventapi.repository.UsuarioRepo
import com.luigiercrest.inventapi.utils.AuthServiceImpl
import com.luigiercrest.inventapi.routes.*
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.auth.*
import io.ktor.server.request.receive

fun Application.configureRouting() {

    val usuarioRepo = UsuarioRepo()
    val authService = AuthServiceImpl(usuarioRepo)

    routing {
        route ("/api"){
            post ("/login"){
                val loginRequest = call.receive<LoginRequest>()
                val usuario = authService.authenticate(
                    loginRequest.dni,
                    loginRequest.passwd
                )
                if (usuario != null) {
                    val token = authService.generateToken(usuario)
                    call.respond(
                        LoginResponse(
                            token = token,
                            expiresIn = jwtconfig.EXPIRATION_TIME,
                            rol = usuario.rol,
                            dni = usuario.dni)
                    )
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "Credenciales inválidas")
                }
            }
            authenticate("auth-jwt") {
                get("/") {
                    call.respondText("Conectado con éxito")
                }
                usuarioRouting()
                centroRouting()
                asignacionCompraRouting()
                dispositivoRouting()
                usoRouting()
                asignacionCompraRouting()
                ubicacionRouting()
                dispositivoRouting()
                categoriaRouting()
                estadoRouting()
                incidenciaRouting()
                proveedorRouting()
                servicioTecnicoRouting()
            }

        }

    }
}