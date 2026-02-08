package com.luigiercrest.inventapi.plugins

import com.luigiercrest.inventapi.utils.LocalDateSerializer
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            encodeDefaults = true
            ignoreUnknownKeys = true
            coerceInputValues = true
            serializersModule = SerializersModule {
                contextual(LocalDateSerializer)
            }
        })
    }
}