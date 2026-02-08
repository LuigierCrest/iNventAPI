package com.luigiercrest.inventapi.models.entities

import com.luigiercrest.inventapi.models.dto.ProveedorDTO
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable


class ProveedorEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : org.jetbrains.exposed.dao.IntEntityClass<ProveedorEntity>(Proveedores)

    var nombre by Proveedores.nombre
    var direccion by Proveedores.direccion
    var telefono by Proveedores.telefono
    var email by Proveedores.email

    // Función de mapeo para convertir a DTO
    fun toDTO() = ProveedorDTO(
        idProveedor = this.id.value,
        nombre = this.nombre,
        direccion = this.direccion,
        telefono = this.telefono,
        email = this.email

    )

}

object Proveedores : IntIdTable("proveedor","id_proveedor") {
    val nombre = varchar("nombre", 100)
    val direccion = varchar("direccion", 150).nullable()
    val telefono = varchar("telefono", 20).nullable()
    val email = varchar("email", 100).nullable()
}