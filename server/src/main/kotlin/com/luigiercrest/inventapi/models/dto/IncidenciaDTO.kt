package com.luigiercrest.inventapi.models.dto

import com.luigiercrest.inventapi.utils.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class IncidenciaDTO
    (
    val idIncidencia: Int? = null,
    val idCentro: Int,
    val idDispositivo: Int,
    val idServicioTecnico: Int,
    val dniResponsable: String,
    val descripcion: String,
    @Serializable(with = LocalDateSerializer::class)
    val fechaReporte: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val fechaCierre: LocalDate?,
    val estado: String
    ) { }