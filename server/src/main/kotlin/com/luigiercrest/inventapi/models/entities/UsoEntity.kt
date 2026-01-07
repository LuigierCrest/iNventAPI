package com.luigiercrest.inventapi.models.entities

import com.luigiercrest.inventapi.models.dto.UsoDTO
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

class UsoEntity(id: EntityID<Int>): IntEntity(id)  {
    companion object : IntEntityClass<UsoEntity>(Usos)

    var nombre by Usos.nombre

    // Función de mapeo para convertir a DTO
    fun toDTO() = UsoDTO(
        idUso = this.id.value,
        nombre = this.nombre
    )
}

object Usos : IntIdTable("uso", "id_uso") {
    val nombre = varchar("nombre", 100)
}