package com.luigiercrest.inventapi.models.entities

import com.luigiercrest.inventapi.models.dto.UsuarioDTO
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

class UsuarioEntity (id: EntityID<String>): Entity<String>(id)  {
    companion object : EntityClass<String, UsuarioEntity>(Usuarios)
    val dni get() = id.value
    var idCentro by Usuarios.idCentro
    var nombre by Usuarios.nombre
    var apellidos by Usuarios.apellidos
    var email by Usuarios.email
    var departamento by Usuarios.departamento
    var rol by Usuarios.rol

    // Función de mapeo para convertir a DTO
    fun toDTO() = UsuarioDTO(
        dni = this.dni,
        idCentro = this.idCentro,
        nombre = this.nombre,
        apellidos = this.apellidos,
        email = this.email,
        departamento = this.departamento,
        rol = this.rol
    )
}

object Usuarios : IdTable<String>("usuario") {
    override val id: Column<EntityID<String>> = varchar("dni", 9).entityId()
    override val primaryKey = PrimaryKey(id)
    val idCentro = integer("id_centro")
    val nombre = varchar("nombre", length = 50)
    val apellidos = varchar("apellidos", length = 100)
    val email = varchar("email", length = 100)
    val departamento = varchar("departamento", length = 100)
    val rol = varchar("rol", length = 20)
}