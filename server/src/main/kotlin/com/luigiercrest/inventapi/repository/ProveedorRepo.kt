package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.ProveedorDTO
import com.luigiercrest.inventapi.models.entities.ProveedorEntity
import com.luigiercrest.inventapi.models.entities.Proveedores
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.builtins.PairSerializer
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

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
            this.direccion = proveedor.direccion ?: ""
            this.telefono = proveedor.telefono ?: ""
            this.email = proveedor.email ?: ""
        }.toDTO()
    }
    //PUT actualizar por id
    suspend fun updateProveedor(id: Int, proveedor: ProveedorDTO): Boolean = dbQuery {
        ProveedorEntity.findById(id) ?: return@dbQuery false
        val row = Proveedores.update({ Proveedores.id eq id }) {
            it[this.nombre] = proveedor.nombre
            it[this.direccion] = proveedor.direccion
            it[this.telefono] = proveedor.telefono
            it[this.email] = proveedor.email
        }
        row > 0
    }
    //DELETE eliminar por id
    suspend fun deleteProveedor(id: Int): Boolean = dbQuery {
        val proveedorToDelete = ProveedorEntity.findById(id) ?: return@dbQuery false
        proveedorToDelete.delete()
        true
    }
}