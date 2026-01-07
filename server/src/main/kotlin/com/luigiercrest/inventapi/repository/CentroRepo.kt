package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.CentroDTO
import com.luigiercrest.inventapi.models.entities.CentroEntity
import com.luigiercrest.inventapi.models.entities.Centros
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction


class CentroRepo () {

    // Helper para reducir boilerplate de transacciones
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    //GET todos los centros
    suspend fun getAllCentros(): List<CentroDTO> = dbQuery{
        CentroEntity.all().map { it.toDTO() }
    }
    //GET por id
    suspend fun getCentrosById(id:Int): CentroDTO? = dbQuery {
        CentroEntity.findById(id)?.toDTO()
    }
    //GET por municipio
    suspend fun getCentrosByMunicipio(municipio:String): List<CentroDTO> = dbQuery {
        CentroEntity.find { Centros.municipio eq municipio }.map { it.toDTO() }
    }
    //GET por tipo
    suspend fun getCentrosByTipo(tipo:String): List<CentroDTO> = dbQuery {
        CentroEntity.find { Centros.tipo eq tipo }.map { it.toDTO() }
    }
    //POST crear centro
    suspend fun addCentro(centro: CentroDTO) = dbQuery {
        CentroEntity.new(centro.idCentro) {
            this.tipo = centro.tipo
            this.nombre = centro.nombre
            this.direccion = centro.direccion
            this.municipio = centro.municipio
        }.toDTO()
    }

    //PUT actualizar centro por id
    suspend fun updateCentro(id: Int, centro: CentroDTO): Boolean = dbQuery {
        val centroToUpdate = CentroEntity.findById(id) ?: return@dbQuery false
        centroToUpdate.tipo = centro.tipo
        centroToUpdate.nombre = centro.nombre
        centroToUpdate.direccion = centro.direccion
        centroToUpdate.municipio = centro.municipio
        true
    }
    //DELETE eliminar centro por id
    suspend fun deleteCentro(id: Int): Boolean = dbQuery {
        val centroToDelete = CentroEntity.findById(id) ?: return@dbQuery false
        centroToDelete.delete()
        true
    }
}