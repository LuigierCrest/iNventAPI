package com.luigiercrest.inventapi.models.dto

import com.luigiercrest.inventapi.utils.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class AsignacionCompraDTO (
    val idAsignacionCompra: Int? = null,
    val idCentro: Int,
    val idProveedor: Int,
    @Serializable(with = LocalDateSerializer::class)
    val entrega: LocalDate
) {
}