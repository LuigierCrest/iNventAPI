package com.luigiercrest.inventapi.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Usuario (
    val dni: String,
    val idCentro: Int,
    val nombre: String,
    val apellidos:String,
    val email:String,
    val departamento:String,
    val rol:String) {
}

object Usuarios : Table("usuario") {
    val dni = varchar("dni", length = 9)
    val idCentro = integer("id_centro")
    val nombre = varchar("nombre", length = 50)
    val apellidos = varchar("apellidos", length = 100)
    val email = varchar("email", length = 100)
    val departamento = varchar("departamento", length = 100)
    val rol = varchar("rol", length = 20)
    override val primaryKey = PrimaryKey(dni, name = "PK_Usuario_DNI")
}