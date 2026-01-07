package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.ProveedorDTO
import com.luigiercrest.inventapi.models.entities.ProveedorEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ProveedorRepo {
    // Helper
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
    //GET
    suspend fun getAllProveedores(): List<ProveedorDTO> = dbQuery {
        ProveedorEntity.all().map { it.toDTO() }
    }
    //GET por id
    suspend fun getProveedorById(id: Int): ProveedorDTO? = dbQuery {
        ProveedorEntity.findById(id)?.toDTO()
    }
    //POST crear
    suspend fun addProveedor(proveedor: ProveedorDTO) = dbQuery {
        ProveedorEntity.new(proveedor.idProveedor) {
            this.nombre = proveedor.nombre
        }.toDTO()
    }
    //PUT actualizar por id
    suspend fun updateProveedor(id: Int, proveedor: ProveedorDTO): Boolean = dbQuery {
        val proveedorToUpdate = ProveedorEntity.findById(id) ?: return@dbQuery false
        proveedorToUpdate.nombre = proveedor.nombre
        true
    }
    //DELETE eliminar por id
    suspend fun deleteProveedor(id: Int): Boolean = dbQuery {
        val proveedorToDelete = ProveedorEntity.findById(id) ?: return@dbQuery false
        proveedorToDelete.delete()
        true
    }
}