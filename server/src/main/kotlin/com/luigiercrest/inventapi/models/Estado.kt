package com.luigiercrest.inventapi.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Estado (
    val idEstado: Int,
    val nombre: String,
) {
}

object Estados : Table("estado") {
    val idEstado = integer("id_estado")
    val nombre = varchar("nombre", 100)
    override val primaryKey = PrimaryKey(idEstado, name = "PK_Estado_ID")
}