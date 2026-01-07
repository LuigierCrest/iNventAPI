package com.luigiercrest.inventapi.models.dto

import kotlinx.serialization.Serializable


@Serializable
data class EstadoDTO (
    val idEstado: Int,
    val nombre: String,) {
}