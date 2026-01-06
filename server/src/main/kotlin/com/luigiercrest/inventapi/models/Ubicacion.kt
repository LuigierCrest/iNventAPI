package com.luigiercrest.inventapi.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class ubicacion (
    val idUbicacion: Int,
    val nombre: String,) {
}

object Ubicaciones : Table("ubicacion") {
    val idUbicacion = integer("id_ubicacion")
    val nombre = varchar("nombre", 100)
    override val primaryKey = PrimaryKey(idUbicacion, name = "PK_Ubicacion_ID")
}