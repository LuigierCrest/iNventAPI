package com.luigiercrest.inventapi.models.entities


import com.luigiercrest.inventapi.models.dto.AsignacionCompraDTO
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

class AsignacionCompraEntity (id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AsignacionCompraEntity>(AsignacionCompras)

    var idCentro by AsignacionCompras.idCentro
    var idProveedor by AsignacionCompras.idProveedor
    var entrega by AsignacionCompras.entrega

    // Función de mapeo para convertir a DTO
    fun toDTO() = AsignacionCompraDTO(
        idAsignacionCompra = this.id.value,
        idCentro = this.idCentro,
        idProveedor = this.idProveedor,
        entrega = this.entrega
    )
}
object AsignacionCompras : IntIdTable("asignacion_compra", "id_asignacion") {
    val idCentro = integer("id_centro")
    val idProveedor = integer("id_proveedor")
    val entrega = date("entrega")
}