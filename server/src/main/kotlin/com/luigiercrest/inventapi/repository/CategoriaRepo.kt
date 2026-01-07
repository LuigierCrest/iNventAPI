package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.CategoriaDTO
import com.luigiercrest.inventapi.models.entities.CategoriaEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class CategoriaRepo {
    // Helper para reducir boilerplate de transacciones
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    // GET Categorias
    suspend fun getAllCategorias(): List<CategoriaDTO> = dbQuery {
        CategoriaEntity.all().map { it.toDTO() }
    }
    // GET por id
    suspend fun getCategoriaById(id: Int): CategoriaDTO? = dbQuery {
        CategoriaEntity.findById(id)?.toDTO()
    }
    // POST crear Categoria
    suspend fun addCategoria(Categoria: CategoriaDTO) = dbQuery {
        CategoriaEntity.new(Categoria.idCategoria) {
            this.nombre = Categoria.nombre
        }.toDTO()
    }
    // PUT actualizar Categoria por id
    suspend fun updateCategoria(id: Int, Categoria: CategoriaDTO): Boolean = dbQuery {
        val CategoriaToUpdate = CategoriaEntity.findById(id) ?: return@dbQuery false
        CategoriaToUpdate.nombre = Categoria.nombre
        true
    }
    // DELETE eliminar Categoria por id
    suspend fun deleteCategoria(id: Int): Boolean = dbQuery {
        val CategoriaToDelete = CategoriaEntity.findById(id) ?: return@dbQuery false
        CategoriaToDelete.delete()
        true
    }
}