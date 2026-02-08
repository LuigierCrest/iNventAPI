package com.luigiercrest.inventapi

const val SERVER_PORT = 8080
const val SERVER_HOST = "0.0.0.0"

object inventoryDB {
    const val URL = "jdbc:postgresql://localhost:5432/inventory_db"
    const val DRIVER = "org.postgresql.Driver"
    const val USER = "admin"
    const val PASSWORD = "1234admin6789"
}

object jwtconfig {
    const val SECRET = "elSecretoQueNadieSabe"
    const val ISSUER = "luigiercrest-inventapi"
    const val AUDIENCE = "inventapi-users"
    const val REALM = "InventAPI Access"
    const val EXPIRATION_TIME = 36_000_00 * 8 //8 horas
}