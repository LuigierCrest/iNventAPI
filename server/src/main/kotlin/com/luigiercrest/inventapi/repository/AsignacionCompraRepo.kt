package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.AsignacionCompra
import com.luigiercrest.inventapi.models.AsignacionCompras
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlinx.datetime.*
import kotlin.collections.map

class AsignacionCompraRepo {
    suspend fun getAllAsignacionCompra(): List<AsignacionCompra> = newSuspendedTransaction (Dispatchers.IO) {
        AsignacionCompras.selectAll().map {
            AsignacionCompra(
                it[AsignacionCompras.idAsignacionCompra],
                it[AsignacionCompras.idCentro],
                it[AsignacionCompras.idProveedor],
                it[AsignacionCompras.entrega]
            )
        }
    }

}