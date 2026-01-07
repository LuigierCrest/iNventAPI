package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.DispositivoDTO
import com.luigiercrest.inventapi.models.entities.DispositivoEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DispositivoRepo {

    // Helper para reducir boilerplate de transacciones
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    //GET todos los dispostivos
    suspend fun getAllDispositivos(): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.all().map { it.toDTO() }
    }
}