package com.luigiercrest.inventapi.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProveedorDTO (
    val idProveedor: Int,
    val nombre: String,
    val direccion: String,
    val telefono: String,
    val email: String,
) {
}