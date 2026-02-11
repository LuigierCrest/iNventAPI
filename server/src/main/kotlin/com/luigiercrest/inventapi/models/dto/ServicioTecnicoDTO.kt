package com.luigiercrest.inventapi.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class ServicioTecnicoDTO (
    val idServicioTecnico: Int?=null,
    val nombre: String,
    val direccion: String?,
    val telefono: String?,
    val email: String?
) {
}