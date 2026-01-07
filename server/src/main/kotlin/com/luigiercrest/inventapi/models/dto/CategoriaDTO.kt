package com.luigiercrest.inventapi.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoriaDTO (
    val idCategoria: Int,
    val nombre: String,
) {
}