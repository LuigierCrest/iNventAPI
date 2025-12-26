package com.luigiercrest.inventapi.entities

import kotlinx.serialization.Serializable

@Serializable
data class Usuario (val id:Int, val rol:String, val email:String) {

}