package com.luigiercrest.inventapi.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Proveedor (
    val idProveedor: Int,
    val nombre: String,
    val direccion: String,
    val telefono: String,
    val email: String,
) {

}

object Proveedores : Table("proveedor") {
    val idProveedor = integer("id_proveedor")
    val nombre = varchar("nombre", 100)
    val direccion = varchar("direccion", 150)
    val telefono = varchar("telefono", 20)
    val email = varchar("email", 100)
    override val primaryKey = PrimaryKey(idProveedor, name = "PK_Proveedor_ID")
}