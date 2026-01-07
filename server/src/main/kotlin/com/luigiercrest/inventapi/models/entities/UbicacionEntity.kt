package com.luigiercrest.inventapi.models.entities

import com.luigiercrest.inventapi.models.dto.UbicacionDTO
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

class UbicacionEntity(id: EntityID<Int>): IntEntity(id){
    companion object : IntEntityClass<UbicacionEntity>(Ubicaciones)

    var nombre by Ubicaciones.nombre

    // Función de mapeo para convertir a DTO
    fun toDTO() = UbicacionDTO(
        idUbicacion = this.id.value,
        nombre = this.nombre
    )
}

object Ubicaciones : IntIdTable("ubicacion", "id_ubicacion") {
    val nombre = varchar("nombre", 100)
}