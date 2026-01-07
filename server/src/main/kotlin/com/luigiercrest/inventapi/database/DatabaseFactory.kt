package com.luigiercrest.inventapi.database

import com.luigiercrest.inventapi.inventoryDB
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

object DatabaseFactory {
    fun init(): Database {
        val config = HikariConfig().apply {
            jdbcUrl = inventoryDB.URL
            driverClassName = inventoryDB.DRIVER
            username = inventoryDB.USER
            password = inventoryDB.PASSWORD
            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }
        val dataSource = HikariDataSource(config) // pool de Hikari
        return Database.Companion.connect(dataSource) // Conecta Exposed a PostgreSQL

    }
}