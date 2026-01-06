package com.luigiercrest.inventapi.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Categoria (
    val idCategoria: Int,
    val nombre: String,
) {
}

object Categorias : Table("categoria") {
    val idCategoria = integer("id_categoria")
    val nombre = varchar("nombre", 100)
    override val primaryKey = PrimaryKey(idCategoria, name = "PK_Categoria_ID")
}