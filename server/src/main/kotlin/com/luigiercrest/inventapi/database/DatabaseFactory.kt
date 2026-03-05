package com.luigiercrest.inventapi.database

import com.luigiercrest.inventapi.inventoryDB
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

object DatabaseFactory {
    fun init(): Database {
        val config = HikariConfig().apply {
            jdbcUrl = inventoryDB.DB_URL
            driverClassName = inventoryDB.DB_DRIVER
            username = inventoryDB.DB_USER
            password = inventoryDB.DB_PASSWORD
            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }
        val dataSource = HikariDataSource(config) // pool de Hikari con 10 conexiones
        return Database.connect(dataSource) // Conecta Exposed a PostgreSQL

    }
}