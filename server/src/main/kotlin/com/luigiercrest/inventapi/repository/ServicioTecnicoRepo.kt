package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.ServicioTecnicoDTO
import com.luigiercrest.inventapi.models.entities.ServicioTecnicoEntity
import com.luigiercrest.inventapi.models.entities.ServiciosTecnicos
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

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
            this.direccion = ServicioTecnico.direccion
            this.telefono = ServicioTecnico.telefono
            this.email = ServicioTecnico.email
        }.toDTO()
    }
    //PUT actualizar por id
    suspend fun updateServicioTecnico(id: Int, ServicioTecnico: ServicioTecnicoDTO): Boolean = dbQuery {
        ServicioTecnicoEntity.findById(id) ?: return@dbQuery false
        val rows = ServiciosTecnicos.update ({ ServiciosTecnicos.id eq id }) {
            it[this.nombre] = ServicioTecnico.nombre
            it[this.direccion] = ServicioTecnico.direccion
            it[this.telefono] = ServicioTecnico.telefono
            it[this.email] = ServicioTecnico.email
        }
        rows > 0
    }
    //DELETE eliminar por id
    suspend fun deleteServicioTecnico(id: Int): Boolean = dbQuery {
        val ServicioTecnicoToDelete = ServicioTecnicoEntity.findById(id) ?: return@dbQuery false
        ServicioTecnicoToDelete.delete()
        true
    }

    companion object
}