package com.luigiercrest.inventapi.plugins

import com.luigiercrest.inventapi.jwtconfig

import com.luigiercrest.inventapi.models.LoginRequest
import com.luigiercrest.inventapi.models.LoginResponse
import com.luigiercrest.inventapi.repository.UsuarioRepo
import com.luigiercrest.inventapi.utils.AuthServiceImpl
import com.luigiercrest.inventapi.routes.*
//import com.luigiercrest.inventapi.utils.Roles
//import com.luigiercrest.inventapi.utils.withRole
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
        route("/api") {
            get("/test") {
                call.respondText("¡Bienvenido a la API de Inventario!")
            }
            post("/login") {
                val loginRequest = call.receive<LoginRequest>()
                val usuario = authService.authenticate(
                    loginRequest.dni,
                    loginRequest.passwd
                )
                if (usuario != null) {
                    val token = authService.generateToken(usuario)
                    call.respond(
                        LoginResponse(
                            // Después de la autenticación se manda información al cliente
                            token = token,
                            expiresIn = jwtconfig.EXPIRATION_TIME,
                            rol = usuario.rol,
                            idCentro = usuario.idCentro,
                            idUsuario = usuario.idUsuario
                        )
                    )
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "Credenciales inválidas")
                }
            }
            authenticate("auth-jwt") {
                get("/authtest") {
                    call.respondText("¡Conexión con autenticación exitosa!")
                }
                // Rutas protegidas por autenticación JWT
                // Demasidas rutas, es mejor organizarlas por casos de uso o módulos, para la próxima
                route("/admin") {
                    centroRouting()
                    proveedorRouting()
                    asignacionCompraRouting()
                    usuarioRouting()
                    dispositivoRouting()
                    servicioTecnicoRouting()
                    incidenciaRouting()
                }
                route("/dire") {
                    direRouting()
                }

                route("/resp") {
                    respRouting()
                }
            }
        }
    }
}