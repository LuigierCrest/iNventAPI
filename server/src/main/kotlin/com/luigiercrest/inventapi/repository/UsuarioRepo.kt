package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.UsuarioDTO
import com.luigiercrest.inventapi.models.entities.UsuarioEntity
import com.luigiercrest.inventapi.models.entities.Usuarios
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class UsuarioRepo {
    // Helper para reducir boilerplate de transacciones
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    //GET todos
    suspend fun getAllUsuarios(): List<UsuarioDTO> = dbQuery {
        UsuarioEntity.all().map { it.toDTO() }
    }
    //GET por dni
    suspend fun getUsuarioByDni(dni: String): UsuarioDTO? = dbQuery {
        UsuarioEntity.find { Usuarios.id eq dni }.firstOrNull()?.toDTO()
    }

    // GET por centro
    suspend fun getUsuariosByCentro(idCentro: Int): List<UsuarioDTO> = dbQuery {
        UsuarioEntity.find { Usuarios.idCentro eq idCentro }.map { it.toDTO() }
    }
    // GET por nombre
    suspend fun getUsuariosByNombre(nombre: String): List<UsuarioDTO> = dbQuery {
        UsuarioEntity.find { Usuarios.nombre eq nombre }.map { it.toDTO() }
    }
    // GET por apellidos
    suspend fun getUsuariosByApellidos(apellidos: String): List<UsuarioDTO> = dbQuery {
        UsuarioEntity.find { Usuarios.apellidos eq apellidos }.map { it.toDTO() }
    }
    // GET por departamento
    suspend fun getUsuariosByDepartamento(departamento: String): List<UsuarioDTO> = dbQuery {
        UsuarioEntity.find { Usuarios.departamento eq departamento }.map { it.toDTO() }
    }
    // GET por rol
    suspend fun getUsuariosByRol(rol: String): List<UsuarioDTO> = dbQuery {
        UsuarioEntity.find { Usuarios.rol eq rol }.map { it.toDTO() }
    }
    // POST crear usuario
    suspend fun addUsuario(usuario: UsuarioDTO) = dbQuery {
        UsuarioEntity.new(usuario.dni) {
            this.idCentro = usuario.idCentro
            this.nombre = usuario.nombre
            this.apellidos = usuario.apellidos
            this.email = usuario.email
            this.departamento = usuario.departamento
            this.rol = usuario.rol
        }.toDTO()
    }
    // PUT actualizar usuario por dni
    suspend fun updateUsuario(dni: String, usuario: UsuarioDTO): Boolean = dbQuery {
        val usuarioToUpdate = UsuarioEntity.find { Usuarios.id eq dni }.firstOrNull()
            ?: return@dbQuery false
        usuarioToUpdate.idCentro = usuario.idCentro
        usuarioToUpdate.nombre = usuario.nombre
        usuarioToUpdate.apellidos = usuario.apellidos
        usuarioToUpdate.departamento = usuario.departamento
        usuarioToUpdate.rol = usuario.rol
        true
    }
    // DELETE eliminar usuario por dni
    suspend fun deleteUsuario(dni: String): Boolean = dbQuery {
        val usuarioToDelete = UsuarioEntity.find { Usuarios.id eq dni }.firstOrNull()
            ?: return@dbQuery false
        usuarioToDelete.delete()
        true
    }

    // esta función no se usa para las corrutinas, es necesaria para la autorización JWT por roles

    fun getRolUsuarioById(idUsuario: Int): String? {
        return transaction {
            val usuario = UsuarioEntity.find { Usuarios.idUsuario eq idUsuario }.firstOrNull()
            val rol = usuario?.rol
            rol
        }
    }
}