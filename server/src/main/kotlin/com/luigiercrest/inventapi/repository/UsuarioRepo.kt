package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.exceptions.ClaveForaneaException
import com.luigiercrest.inventapi.models.dto.UsuarioDTO
import com.luigiercrest.inventapi.models.entities.UsuarioEntity
import com.luigiercrest.inventapi.models.entities.Usuarios
import com.luigiercrest.inventapi.exceptions.DniException
import com.luigiercrest.inventapi.models.entities.CentroEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import org.postgresql.util.PSQLException

class UsuarioRepo {
    // Helper para reducir boilerplate de transacciones
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    //GET todos
    suspend fun getAllUsuarios(): List<UsuarioDTO> = dbQuery {
        UsuarioEntity.all().map { it.toDTO(incluyePasswd = false) }
    }

    //GET por dni
    suspend fun getUsuarioByDni(dni: String): UsuarioDTO? = dbQuery {
        UsuarioEntity.find { Usuarios.dni eq dni }.firstOrNull()?.toDTO(incluyePasswd = false)
    }
    //GET por dni para Auth, devuelve el hash de la contraseña
    suspend fun getUsuarioByDniAuth(dni: String): UsuarioDTO? = dbQuery {
        UsuarioEntity.find { Usuarios.dni eq dni }.firstOrNull()?.toDTO(incluyePasswd = true)
    }

    // GET por centro
    suspend fun getUsuariosByCentro(idCentro: Int): List<UsuarioDTO> = dbQuery {
        UsuarioEntity.find { Usuarios.idCentro eq idCentro }.map { it.toDTO(incluyePasswd = false) }
    }

    // GET por nombre
    suspend fun getUsuariosByNombre(nombre: String): List<UsuarioDTO> = dbQuery {
        UsuarioEntity.find { Usuarios.nombre eq nombre }.map { it.toDTO(incluyePasswd = false) }
    }

    // GET por apellidos
    suspend fun getUsuariosByApellidos(apellidos: String): List<UsuarioDTO> = dbQuery {
        UsuarioEntity.find { Usuarios.apellidos eq apellidos }.map { it.toDTO(incluyePasswd = false) }
    }

    // GET por departamento
    suspend fun getUsuariosByDepartamento(departamento: String): List<UsuarioDTO> = dbQuery {
        UsuarioEntity.find { Usuarios.departamento eq departamento }.map { it.toDTO(incluyePasswd = false) }
    }

    // GET por rol
    suspend fun getUsuariosByRol(rol: String): List<UsuarioDTO> = dbQuery {
        UsuarioEntity.find { Usuarios.rol eq rol }.map { it.toDTO(incluyePasswd = false) }
    }

    // POST crear usuario
    suspend fun addUsuario(nuevoUsuario: UsuarioDTO): UsuarioDTO = dbQuery {
        val existing = UsuarioEntity.find { Usuarios.dni eq nuevoUsuario.dni }.firstOrNull()
        if (existing != null) {
            throw DniException()
        }
        val centroExists = CentroEntity.findById(nuevoUsuario.idCentro) != null
        if (!centroExists) {
            throw ClaveForaneaException()
        }
        try {
            val created = UsuarioEntity.new() {
                this.dni= nuevoUsuario.dni
                this.idCentro = nuevoUsuario.idCentro
                this.nombre = nuevoUsuario.nombre
                this.apellidos = nuevoUsuario.apellidos
                this.email = nuevoUsuario.email.toString()
                this.departamento = nuevoUsuario.departamento.toString()
                this.rol = nuevoUsuario.rol
                this.passwdHash = nuevoUsuario.passwdHash.toString()
            }
            created.toDTO()
        } catch (e: ExposedSQLException) {
            // Exposed puede envolver la PSQLException
            val psql = e.cause as? PSQLException ?: e.cause?.cause as? PSQLException
            if (psql?.sqlState == "23505") {
                println(DniException())
            }
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    // PUT actualizar usuario por dni
    suspend fun updateUsuario(dni: String, usuario: UsuarioDTO): Boolean = dbQuery {
        UsuarioEntity.find { Usuarios.dni eq dni }.firstOrNull() ?: return@dbQuery false
        val rows = Usuarios.update({ Usuarios.dni eq dni }) {
            it[Usuarios.idCentro] = usuario.idCentro
            it[Usuarios.nombre] = usuario.nombre
            it[Usuarios.apellidos] = usuario.apellidos
            it[Usuarios.email] = usuario.email.toString()
            it[Usuarios.departamento] = usuario.departamento.toString()
            it[Usuarios.rol] = usuario.rol
            // la contraseña solo la puede actualizar el propio usuario
            //it[Usuarios.passwdHash] = usuario.passwdHash
        }
        rows > 0
    }
    // PUT actualizar contraseña por id
    suspend fun updateUsuarioPassword(idUsuario: Int, newPasswdHash: String): Boolean = dbQuery {
        val usuarioToUpdate = UsuarioEntity.findById(idUsuario)
            ?: return@dbQuery false
        usuarioToUpdate.passwdHash = newPasswdHash
        true
    }


    // DELETE eliminar usuario por dni
    suspend fun deleteUsuario(dni: String): Boolean = dbQuery {
        val usuarioToDelete = UsuarioEntity.find { Usuarios.dni eq dni }.firstOrNull()
            ?: return@dbQuery false
        usuarioToDelete.delete()
        true
    }

    // esta función no se usa para las corrutinas, usada para un test en la autorización JWT por roles
//
//    fun getRolUsuarioById(idUsuario: Int): String? {
//        return transaction {
//            val usuario = UsuarioEntity.find { Usuarios.idUsuario eq idUsuario }.firstOrNull()
//            val rol = usuario?.rol
//            rol
//        }
//    }
}