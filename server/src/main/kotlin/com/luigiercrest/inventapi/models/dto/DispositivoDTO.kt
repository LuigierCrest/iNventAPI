package com.luigiercrest.inventapi.models.dto

import com.luigiercrest.inventapi.utils.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class DispositivoDTO (
    val idDispositivo: Int?=null,
    val idCentro:Int,
    val nombre:String,
    val numSerie:String?,
    val marcaModelo:String?,
    @Serializable(with = LocalDateSerializer::class)
    val ultimaActualizacion: LocalDate?, //se transforma en localdate o string
    val observaciones: String?,
    val estado:String?,
    val categoria:String?,
    val ubicacion:String?,
    val uso:String?,
    val idAsignacion:Int
){
}