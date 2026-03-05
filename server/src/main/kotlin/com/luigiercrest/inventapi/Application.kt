/*
*
*   iNventAPI
*   Es un servicio para proporcionar endpoints para la aplicación multiplataforma iNventMulti
*   Se trata de un proyecto desarrollado en Kotlin Multi Platform.
*
*   Desarrollado por Luis Manuel Ortega Rodríguez, 3ºDAM, IES El Rincón, Las Palmas de Gran Canaria. Curso 2025-2026.
*
*   Licencia: Creative Commons Attribution-NonCommercial 4.0 International (CC BY-NC 4.0)
*
* */


package com.luigiercrest.inventapi

import com.luigiercrest.inventapi.database.DatabaseFactory
import com.luigiercrest.inventapi.plugins.configureRouting
import com.luigiercrest.inventapi.plugins.configureSecurity
import com.luigiercrest.inventapi.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = SERVER_HOST, module = Application::module)
        .start(wait = true)

}

fun Application.module() {
    DatabaseFactory.init()
    configureSerialization()
    configureSecurity()
    configureRouting()

}