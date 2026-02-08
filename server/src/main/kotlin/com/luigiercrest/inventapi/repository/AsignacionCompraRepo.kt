package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.exceptions.AsignacionException
import com.luigiercrest.inventapi.exceptions.ClaveForaneaException
import com.luigiercrest.inventapi.exceptions.DniException
import com.luigiercrest.inventapi.exceptions.FormatoFechaException
import com.luigiercrest.inventapi.models.dto.AsignacionCompraDTO
import com.luigiercrest.inventapi.models.entities.AsignacionCompraEntity
import com.luigiercrest.inventapi.models.entities.AsignacionCompras
import com.luigiercrest.inventapi.models.entities.CentroEntity
import com.luigiercrest.inventapi.models.entities.ProveedorEntity
import com.luigiercrest.inventapi.models.entities.UsuarioEntity
import com.luigiercrest.inventapi.models.entities.Usuarios
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.time.LocalDate
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import org.postgresql.util.PSQLException
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

        val centroExists = CentroEntity.findById(asignacionCompra.idCentro) != null
        if (!centroExists) {
            throw ClaveForaneaException()
        }
        val proveedorExists = ProveedorEntity.findById(asignacionCompra.idProveedor) != null
        if (!proveedorExists) {
            throw ClaveForaneaException()
        }
        try {
            AsignacionCompraEntity.new(asignacionCompra.idAsignacionCompra) {
                this.idCentro = asignacionCompra.idCentro
                this.idProveedor = asignacionCompra.idProveedor
                this.entrega = asignacionCompra.entrega
                //repasar formato fecha
            }.toDTO()
        } catch (e: ExposedSQLException) {
            val psql = e.cause as? PSQLException ?: e.cause?.cause as? PSQLException
            when (psql?.sqlState) {
                "22007", "22P02" -> throw FormatoFechaException("Formato de dato inválido: ${psql.message}")
                else -> throw e
            }
        } catch (e: Exception) {
            println(e)
        }
    }
    //PUT actualizar asignacion compra por id
    suspend fun updateAsignacionCompra(id: Int, asignacionCompra: AsignacionCompraDTO): Boolean = dbQuery {
        AsignacionCompraEntity.findById(id) ?: return@dbQuery false
        val rows = AsignacionCompras.update({ AsignacionCompras.id eq id }) {
            it[this.idCentro] = asignacionCompra.idCentro
            it[this.idProveedor] = asignacionCompra.idProveedor
            it[this.entrega] = asignacionCompra.entrega
        }
        rows > 0
    }
    //DELETE eliminar asignacion compra por id
    suspend fun deleteAsignacionCompra(id: Int): Boolean = dbQuery {
        val asignacionCompraToDelete = AsignacionCompraEntity.findById(id) ?: return@dbQuery false
        asignacionCompraToDelete.delete()
        true
    }


}