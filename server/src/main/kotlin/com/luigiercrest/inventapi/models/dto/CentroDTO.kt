package com.luigiercrest.inventapi.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class CentroDTO(
    val idCentro: Int,
    val tipo: String,
    val nombre: String,
    val direccion: String?,
    val municipio: String?
)
