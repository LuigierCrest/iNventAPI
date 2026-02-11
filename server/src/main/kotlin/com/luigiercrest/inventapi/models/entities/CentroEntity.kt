package com.luigiercrest.inventapi.models.entities

import org.jetbrains.exposed.dao.id.IntIdTable
import com.luigiercrest.inventapi.models.dto.CentroDTO
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class CentroEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CentroEntity>(Centros)

    var tipo by Centros.tipo
    var nombre by Centros.nombre
    var direccion by Centros.direccion
    var municipio by Centros.municipio

    // Función de mapeo para convertir a DTO
    fun toDTO() = CentroDTO(
        idCentro = this.id.value,
        tipo = this.tipo,
        nombre = this.nombre,
        direccion = this.direccion,
        municipio = this.municipio
    )
}

object Centros : IntIdTable("centro", "id_centro") {
    val tipo = varchar("tipo", 50)
    val nombre = varchar("nombre", 100)
    val direccion = varchar("direccion", 150).nullable()
    val municipio = varchar("municipio", 100).nullable()
}