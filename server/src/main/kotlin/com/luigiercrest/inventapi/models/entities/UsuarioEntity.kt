package com.luigiercrest.inventapi.models.entities

import com.luigiercrest.inventapi.models.dto.UsuarioDTO
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

class UsuarioEntity (id: EntityID<Int>): IntEntity(id)  {
    companion object : IntEntityClass<UsuarioEntity>(Usuarios)
    var dni by Usuarios.dni
    var idCentro by Usuarios.idCentro
    var nombre by Usuarios.nombre
    var apellidos by Usuarios.apellidos
    var email by Usuarios.email
    var departamento by Usuarios.departamento
    var rol by Usuarios.rol

    var passwdHash by Usuarios.passwdHash

    // Función de mapeo para convertir a DTO
    fun toDTO(incluyePasswd: Boolean = false): UsuarioDTO = UsuarioDTO(
        idUsuario = this.id.value,
        dni = this.dni,
        idCentro = this.idCentro,
        nombre = this.nombre,
        apellidos = this.apellidos,
        email = this.email,
        departamento = this.departamento,
        rol = this.rol,
        passwdHash = if (incluyePasswd) this.passwdHash else null
    )
}

object Usuarios : IntIdTable("usuario", "id_usuario") {
    val dni = varchar("dni", 9).uniqueIndex()
    val idCentro = integer("id_centro")
    val nombre = varchar("nombre", length = 50)
    val apellidos = varchar("apellidos", length = 100)
    val email = varchar("email", length = 100)
    val departamento = varchar("departamento", length = 100)
    val rol = varchar("rol", length = 20)
    val passwdHash = varchar("passwd_hash", length = 256)
}