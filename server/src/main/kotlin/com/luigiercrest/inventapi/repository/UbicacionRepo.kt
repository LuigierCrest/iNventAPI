package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.UbicacionDTO
import com.luigiercrest.inventapi.models.entities.UbicacionEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UbicacionRepo {
    // Helper para reducir boilerplate de transacciones
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
    //GET todas las ubicaciones
    suspend fun getAllUbicaciones(): List<UbicacionDTO> = dbQuery {
        UbicacionEntity.all().map { it.toDTO() }
    }
    //GET por id
    suspend fun getUbicacionById(id:Int): UbicacionDTO? = dbQuery {
        UbicacionEntity.findById(id)?.toDTO()
    }
    //POST crear ubicacion
    suspend fun addUbicacion(ubicacion: UbicacionDTO) = dbQuery {
        UbicacionEntity.new(ubicacion.idUbicacion) {
            this.nombre = ubicacion.nombre
        }.toDTO()
    }
    //PUT actualizar ubicacion por id
    suspend fun updateUbicacion(id: Int, ubicacion: UbicacionDTO): Boolean = dbQuery {
        val ubicacionToUpdate = UbicacionEntity.findById(id) ?: return@dbQuery false
        ubicacionToUpdate.nombre = ubicacion.nombre
        true
    }
    //DELETE eliminar ubicacion por id
    suspend fun deleteUbicacion(id: Int): Boolean = dbQuery {
        val ubicacionToDelete = UbicacionEntity.findById(id) ?: return@dbQuery false
        ubicacionToDelete.delete()
        true
    }
}