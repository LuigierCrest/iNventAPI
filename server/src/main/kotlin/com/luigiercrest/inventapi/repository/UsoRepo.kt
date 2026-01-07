package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.UsoDTO
import com.luigiercrest.inventapi.models.entities.UsoEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UsoRepo {
    // Helper para reducir boilerplate de transacciones
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    // GET todos los usos
    suspend fun getAllUsos(): List<UsoDTO> = dbQuery {
        UsoEntity.all().map { it.toDTO() }
    }
    // GET por id
    suspend fun getUsoById(id: Int): UsoDTO? = dbQuery {
        UsoEntity.findById(id)?.toDTO()
    }
    // POST crear uso
    suspend fun addUso(uso: UsoDTO) = dbQuery {
        UsoEntity.new(uso.idUso) {
            this.nombre = uso.nombre
        }.toDTO()
    }
    // PUT actualizar uso por id
    suspend fun updateUso(id: Int, uso: UsoDTO): Boolean = dbQuery {
        val usoToUpdate = UsoEntity.findById(id) ?: return@dbQuery false
        usoToUpdate.nombre = uso.nombre
        true
    }
    // DELETE eliminar uso por id
    suspend fun deleteUso(id: Int): Boolean = dbQuery {
        val usoToDelete = UsoEntity.findById(id) ?: return@dbQuery false
        usoToDelete.delete()
        true
    }
}