package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.UsuarioDTO
import com.luigiercrest.inventapi.models.entities.UsuarioEntity
import com.luigiercrest.inventapi.models.entities.Usuarios
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UsuarioRepo {
    suspend fun getAllUsuarios(): List<UsuarioDTO> = newSuspendedTransaction(Dispatchers.IO) {
        UsuarioEntity.all().map { it.toDTO() }
    }
}