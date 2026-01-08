package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.ServicioTecnicoDTO
import com.luigiercrest.inventapi.models.entities.ServicioTecnicoEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ServicioTecnicoRepo {
    // Helper para reducir boilerplate de transacciones
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
    //GET
    suspend fun getAllServicioTecnicos(): List<ServicioTecnicoDTO> = dbQuery {
        ServicioTecnicoEntity.all().map { it.toDTO() }
    }
    //GET por id
    suspend fun getServicioTecnicoById(id: Int): ServicioTecnicoDTO? = dbQuery {
        ServicioTecnicoEntity.findById(id)?.toDTO()
    }
    //POST crear
    suspend fun addServicioTecnico(ServicioTecnico: ServicioTecnicoDTO) = dbQuery {
        ServicioTecnicoEntity.new(ServicioTecnico.idServicioTecnico) {
            this.nombre = ServicioTecnico.nombre
        }.toDTO()
    }
    //PUT actualizar por id
    suspend fun updateServicioTecnico(id: Int, ServicioTecnico: ServicioTecnicoDTO): Boolean = dbQuery {
        val ServicioTecnicoToUpdate = ServicioTecnicoEntity.findById(id) ?: return@dbQuery false
        ServicioTecnicoToUpdate.nombre = ServicioTecnico.nombre
        true
    }
    //DELETE eliminar por id
    suspend fun deleteServicioTecnico(id: Int): Boolean = dbQuery {
        val ServicioTecnicoToDelete = ServicioTecnicoEntity.findById(id) ?: return@dbQuery false
        ServicioTecnicoToDelete.delete()
        true
    }

    companion object
}