package com.luigiercrest.inventapi.models.entities

import com.luigiercrest.inventapi.models.dto.UsuarioDTO
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

class UsuarioEntity (id: EntityID<String>): Entity<String>(id)  {
    companion object : EntityClass<String, UsuarioEntity>(Usuarios)
    val dni get() = id.value
    var idUsuario by Usuarios.idUsuario
    var idCentro by Usuarios.idCentro
    var nombre by Usuarios.nombre
    var apellidos by Usuarios.apellidos
    var email by Usuarios.email
    var departamento by Usuarios.departamento
    var rol by Usuarios.rol

    var passwdHash by Usuarios.passwdHash

    // Función de mapeo para convertir a DTO
    fun toDTO() = UsuarioDTO(
        dni = this.dni,
        idUsuario = this.idUsuario,
        idCentro = this.idCentro,
        nombre = this.nombre,
        apellidos = this.apellidos,
        email = this.email,
        departamento = this.departamento,
        rol = this.rol,
        passwdHash =this.passwdHash
    )
}

object Usuarios : IdTable<String>("usuario") {
    override val id: Column<EntityID<String>> = varchar("dni", 9).entityId()
    override val primaryKey = PrimaryKey(id)
    val idUsuario = integer("id_usuario").autoIncrement()
    val idCentro = integer("id_centro")
    val nombre = varchar("nombre", length = 50)
    val apellidos = varchar("apellidos", length = 100)
    val email = varchar("email", length = 100)
    val departamento = varchar("departamento", length = 100)
    val rol = varchar("rol", length = 20)
    val passwdHash = varchar("passwd_hash", length = 256)
}