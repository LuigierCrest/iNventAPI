package com.luigiercrest.inventapi.models


import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate
import com.luigiercrest.inventapi.utils.LocalDateSerializer


@Serializable
data class AsignacionCompra (
    val idAsignacionCompra: Int,
    val idCentro: Int,
    val idProveedor: Int,
    @Serializable(with = LocalDateSerializer::class)
    val entrega: LocalDate
) {
}

object AsignacionCompras : Table("asignacion_compra") {
    val idAsignacionCompra = integer("id_asignacion")
    val idCentro = integer("id_centro")
    val idProveedor = integer("id_proveedor")
    val entrega = date("entrega")
    override val primaryKey = PrimaryKey(idAsignacionCompra, name = "PK_AsignacionCompra_ID")
}