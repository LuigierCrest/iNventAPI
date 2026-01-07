package com.luigiercrest.inventapi.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class ServicioTecnicoDTO (
    val idServicioTecnico: Int,
    val nombre: String,
    val direccion: String,
    val telefono: String,
    val email: String
) {
}