package com.luigiercrest.inventapi.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val dni: String,
    val passwd: String
)

@Serializable
data class LoginResponse(
    val token: String,
    val expiresIn: Int,
    val rol: String,
    val dni: String
)