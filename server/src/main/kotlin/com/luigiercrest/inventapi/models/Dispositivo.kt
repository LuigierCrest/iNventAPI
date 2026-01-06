package com.luigiercrest.inventapi.models

import kotlinx.serialization.Serializable


@Serializable
data class Dispositivo (
    val idDispositivo: Int,
    val idCentro:Int,
    val nombreDispositivo:String,
    val numSerie:String,
    val marcaModelo:String,
    val ultiActualizacion: String,
    val observaciones: String,
    val idEstado:Int,
    val idCategoria:Int,
    val idUbicacion:Int,
    val idUso:Int,
    val idAsignacion:Int) {
}

