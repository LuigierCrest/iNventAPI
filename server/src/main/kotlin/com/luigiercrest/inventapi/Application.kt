package com.luigiercrest.inventapi

import com.luigiercrest.inventapi.plugins.configureRouting
import com.luigiercrest.inventapi.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = SERVER_HOST, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
//    routing {
//        get("/") {
//            call.respondText("Ktor: ${Greeting().greet()}")
//        }
//    }

    configureSerialization()
    configureRouting()
}