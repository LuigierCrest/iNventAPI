package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.CentroDTO
import com.luigiercrest.inventapi.models.entities.CentroEntity
import com.luigiercrest.inventapi.models.entities.Centros
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update


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
        CentroEntity.findById(id) ?: return@dbQuery false
        val rows = Centros.update({ Centros.id eq id }) {
            it[this.tipo] = centro.tipo
            it[this.nombre] = centro.nombre
            it[this.direccion] = centro.direccion
            it[this.municipio] = centro.municipio
        }
        rows > 0
    }
    //DELETE eliminar centro por id
    suspend fun deleteCentro(id: Int): Boolean = dbQuery {
        val centroToDelete = CentroEntity.findById(id) ?: return@dbQuery false
        centroToDelete.delete()
        true
    }
}