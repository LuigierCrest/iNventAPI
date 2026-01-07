package com.luigiercrest.inventapi.models.entities

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

class CategoriaEntity(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<CategoriaEntity> (Categorias)
    var nombre by Categorias.nombre

    // Función de mapeo para convertir a DTO
    fun toDTO() = com.luigiercrest.inventapi.models.dto.CategoriaDTO(
        idCategoria = this.id.value,
        nombre = this.nombre
    )
}

object Categorias : IntIdTable("categoria", "id_categoria") {
    val nombre = varchar("nombre", 100)
}