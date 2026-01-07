package com.luigiercrest.inventapi.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class UsoDTO (
    val idUso: Int,
    val nombre: String,
) {
}