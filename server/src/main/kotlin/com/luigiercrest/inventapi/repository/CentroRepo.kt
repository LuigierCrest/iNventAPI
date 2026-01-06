package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.Centro
import com.luigiercrest.inventapi.models.Centros
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class CentroRepo () {
    //private val db: Database = init()
    suspend fun getAllCentros(): List<Centro> = newSuspendedTransaction(Dispatchers.IO){
        Centros.selectAll().map {
            Centro(it[Centros.idCentro],
                it[Centros.tipo],
                it[Centros.nombre],
                it[Centros.direccion],
                it[Centros.municipio])
        }
    }
}