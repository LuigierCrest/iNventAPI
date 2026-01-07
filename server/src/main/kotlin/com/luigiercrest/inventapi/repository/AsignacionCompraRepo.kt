package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.AsignacionCompraDTO
import com.luigiercrest.inventapi.models.entities.AsignacionCompraEntity
import com.luigiercrest.inventapi.models.entities.AsignacionCompras
import kotlinx.coroutines.Dispatchers
import java.time.LocalDate
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.collections.map

class AsignacionCompraRepo {
    // Helper para reducir boilerplate de transacciones
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    //GET all
    suspend fun getAllAsignacionCompra(): List<AsignacionCompraDTO> = dbQuery {
        AsignacionCompraEntity.all().map { it.toDTO() }
    }
    //GET por id
    suspend fun getAsignacionCompraById(id: Int): AsignacionCompraDTO? = dbQuery {
        AsignacionCompraEntity.findById(id)?.toDTO()
    }
    //GET por centro
    suspend fun getAsignacionCompraByCentro(idCentro: Int): List<AsignacionCompraDTO> = dbQuery {
        AsignacionCompraEntity.find { AsignacionCompras.idCentro eq idCentro }
            .map { it.toDTO() }
    }
    //GET por proveedor
    suspend fun getAsignacionCompraByProveedor(idProveedor: Int): List<AsignacionCompraDTO> = dbQuery {
        AsignacionCompraEntity.find { AsignacionCompras.idProveedor eq idProveedor }
            .map { it.toDTO() }
    }
    //GET por fecha entrega
    suspend fun getAsignacionCompraByFechaEntrega(fechaEntrega: String): List<AsignacionCompraDTO> = dbQuery {
        try {
            val fecha = LocalDate.parse(fechaEntrega)
            AsignacionCompraEntity.find{AsignacionCompras.entrega eq fecha}
                .map { it.toDTO() }
        } catch (e: Exception) {
            emptyList<AsignacionCompraDTO>()
        }
    }
    //POST crear asignacion compra
    suspend fun addAsignacionCompra(asignacionCompra: AsignacionCompraDTO) = dbQuery {
        AsignacionCompraEntity.new(asignacionCompra.idAsignacionCompra) {
            this.idCentro = asignacionCompra.idCentro
            this.idProveedor = asignacionCompra.idProveedor
            this.entrega = asignacionCompra.entrega
            //repasar formato fecha
        }.toDTO()
    }
    //PUT actualizar asignacion compra por id
    suspend fun updateAsignacionCompra(id: Int, asignacionCompra: AsignacionCompraDTO): Boolean = dbQuery {
        val asignacionCompraToUpdate = AsignacionCompraEntity.findById(id) ?: return@dbQuery false
        asignacionCompraToUpdate.idCentro = asignacionCompra.idCentro
        asignacionCompraToUpdate.idProveedor = asignacionCompra.idProveedor
        asignacionCompraToUpdate.entrega = asignacionCompra.entrega
        true
    }
    //DELETE eliminar asignacion compra por id
    suspend fun deleteAsignacionCompra(id: Int): Boolean = dbQuery {
        val asignacionCompraToDelete = AsignacionCompraEntity.findById(id) ?: return@dbQuery false
        asignacionCompraToDelete.delete()
        true
    }


}