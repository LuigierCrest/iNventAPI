package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.EstadoDTO
import com.luigiercrest.inventapi.models.entities.EstadoEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class EstadoRepo {
    // Helper para reducir boilerplate de transacciones
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    // GET todos los Estados
    suspend fun getAllEstados(): List<EstadoDTO> = dbQuery {
        EstadoEntity.all().map { it.toDTO() }
    }
    // GET por id
    suspend fun getEstadoById(id: Int): EstadoDTO? = dbQuery {
        EstadoEntity.findById(id)?.toDTO()
    }
    // POST crear Estado
    suspend fun addEstado(estado: EstadoDTO) = dbQuery {
        EstadoEntity.new(estado.idEstado) {
            this.nombre = estado.nombre
        }.toDTO()
    }
    // PUT actualizar Estado por id
    suspend fun updateEstado(id: Int, estado: EstadoDTO): Boolean = dbQuery {
        val estadoToUpdate = EstadoEntity.findById(id) ?: return@dbQuery false
        estadoToUpdate.nombre = estado.nombre
        true
    }
    // DELETE eliminar Estado por id
    suspend fun deleteEstado(id: Int): Boolean = dbQuery {
        val estadoToDelete = EstadoEntity.findById(id) ?: return@dbQuery false
        estadoToDelete.delete()
        true
    }
}