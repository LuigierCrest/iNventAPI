package com.luigiercrest.inventapi.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Centro (
    val idCentro: Int,
    val tipo: String,
    val nombre: String,
    val direccion: String,
    val municipio: String
) {
}
object Centros : Table("centro") {
    val idCentro = integer("id_centro")
    val tipo = varchar("tipo", 50)
    val nombre = varchar("nombre", 100)
    val direccion = varchar("direccion", 150)
    val municipio = varchar("municipio", 100)
    override val primaryKey = PrimaryKey(idCentro, name = "PK_Centro_ID")
}