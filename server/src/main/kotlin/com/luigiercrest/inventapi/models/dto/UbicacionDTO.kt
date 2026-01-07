package com.luigiercrest.inventapi.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class UbicacionDTO (
    val idUbicacion: Int,
    val nombre: String,) {
}