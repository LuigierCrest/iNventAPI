package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.Usuario
import com.luigiercrest.inventapi.models.Usuarios
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UsuarioRepo {
    suspend fun getAllUsuarios(): List<Usuario> = newSuspendedTransaction(Dispatchers.IO) {
        Usuarios.selectAll().map {
            Usuario(
                it[Usuarios.dni],
                it[Usuarios.idCentro],
                it[Usuarios.nombre],
                it[Usuarios.apellidos],
                it[Usuarios.email],
                it[Usuarios.departamento],
                it[Usuarios.rol]
            )
        }
    }
}