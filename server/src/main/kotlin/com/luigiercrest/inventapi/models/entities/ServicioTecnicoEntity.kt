package com.luigiercrest.inventapi.models.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

class ServicioTecnicoEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<ServicioTecnicoEntity>(ServiciosTecnicos)

    var nombre by ServiciosTecnicos.nombre
    var direccion by ServiciosTecnicos.direccion
    var telefono by ServiciosTecnicos.telefono
    var email by ServiciosTecnicos.email

    // Función de mapeo para convertir a DTO
    fun toDTO() = com.luigiercrest.inventapi.models.dto.ServicioTecnicoDTO(
        idServicioTecnico = this.id.value,
        nombre = this.nombre,
        direccion = this.direccion,
        telefono = this.telefono,
        email = this.email
    )
}

object ServiciosTecnicos : IntIdTable("servicio_tecnico", "id_servicio_tecnico") {
    val nombre = varchar("nombre", 100)
    val direccion = varchar("direccion", 150).nullable()
    val telefono = varchar("telefono", 20).nullable()
    val email = varchar("email", 100).nullable()
}