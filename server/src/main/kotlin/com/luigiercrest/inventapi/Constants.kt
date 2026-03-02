package com.luigiercrest.inventapi

const val SERVER_PORT = 8080
const val SERVER_HOST = "127.0.0.1"

object inventoryDB {
    val DB_URL = System.getenv("DB_URL") ?: "jdbc:postgresql://localhost:5432/inventory_db"
    val DB_DRIVER = System.getenv("DB_DRIVER")?: "org.postgresql.Driver"
    val DB_USER = System.getenv("DB_USER") ?: "admin"
    val DB_PASSWORD = System.getenv("DB_PASSWORD") ?: "1234admin6789"
}

object jwtconfig {
    const val SECRET = "elSecretoQueNadieSabe"
    const val ISSUER = "luigiercrest-inventapi"
    const val AUDIENCE = "inventapi-users"
    const val REALM = "InventAPI Access"
    const val EXPIRATION_TIME = 36_000_00 * 8 //8 horas
}