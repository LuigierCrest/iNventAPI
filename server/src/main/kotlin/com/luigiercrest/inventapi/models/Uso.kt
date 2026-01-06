package com.luigiercrest.inventapi.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Uso (
    val idUso: Int,
    val nombre: String,
) {
}

object Usos : Table("uso") {
    val idUso = integer("id_uso")
    val nombre = varchar("nombre", 100)
    override val primaryKey = PrimaryKey(idUso, name = "PK_Uso_ID")
}