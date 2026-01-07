package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.AsignacionCompraDTO
import com.luigiercrest.inventapi.models.entities.AsignacionCompraEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.collections.map

class AsignacionCompraRepo {
    suspend fun getAllAsignacionCompra(): List<AsignacionCompraDTO> = newSuspendedTransaction (Dispatchers.IO) {
        AsignacionCompraEntity.all().map { it.toDTO() }
    }

}