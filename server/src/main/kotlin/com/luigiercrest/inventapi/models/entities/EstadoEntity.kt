package com.luigiercrest.inventapi.models.entities

import com.luigiercrest.inventapi.models.dto.EstadoDTO
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

class EstadoEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<EstadoEntity>(Estados)

    var nombre by Estados.nombre

    // Función de mapeo para convertir a DTO
    fun toDTO() = EstadoDTO(
        idEstado = this.id.value,
        nombre = this.nombre
    )
}

object Estados : IntIdTable("estado","id_estado") {
    val nombre = varchar("nombre", 100)
}