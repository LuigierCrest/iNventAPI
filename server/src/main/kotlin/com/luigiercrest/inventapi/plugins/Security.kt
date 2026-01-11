package com.luigiercrest.inventapi.plugins

import io.ktor.server.application.Application
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.luigiercrest.inventapi.jwtconfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.respond

fun Application.configureSecurity() {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = jwtconfig.REALM
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtconfig.SECRET))
                    .withAudience(jwtconfig.AUDIENCE)
                    .withIssuer(jwtconfig.ISSUER)
                    .build()
            )
            validate { credential ->
                val dni = credential.payload.getClaim("dni").asString()
                val rol = credential.payload.getClaim("rol").asString()
                if (dni.isNotEmpty() && rol.isNotEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.respond(UnauthorizedResponse())
            }
        }

    }
}