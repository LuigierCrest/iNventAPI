package com.luigiercrest.inventapi.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class ServicioTecnico (
    val idServicioTecnico: Int,
    val nombre: String,
    val direccion: String,
    val telefono: String,
    val email: String
) {
}

object ServiciosTecnicos : Table("servicio_tecnico") {
    val idServicioTecnico = integer("id_servicio_tecnico")
    val nombre = varchar("nombre", 100)
    val direccion = varchar("direccion", 150)
    val telefono = varchar("telefono", 20)
    val email = varchar("email", 100)
    override val primaryKey = PrimaryKey(idServicioTecnico, name = "PK_ServicioTecnico_ID")
}