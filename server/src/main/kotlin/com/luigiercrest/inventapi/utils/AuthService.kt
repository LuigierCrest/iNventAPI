package com.luigiercrest.inventapi.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.luigiercrest.inventapi.jwtconfig
import com.luigiercrest.inventapi.models.dto.UsuarioDTO
import com.luigiercrest.inventapi.repository.UsuarioRepo
import org.mindrot.jbcrypt.BCrypt
import java.util.*

interface AuthService {
    suspend fun authenticate(dni: String, passwd: String): UsuarioDTO?
    fun generateToken(usuario: UsuarioDTO): String
}

class AuthServiceImpl(private val usuarioRepo: UsuarioRepo) : AuthService {

    override suspend fun authenticate(dni: String, passwd: String): UsuarioDTO? {
        val usuario = usuarioRepo.getUsuarioByDni(dni) ?: null
        // Verifica la contraseña
        return if (BCrypt.checkpw(passwd, usuario?.passwdHash)) {
            usuario
        } else {
            null
        }

    }

    override fun generateToken(usuario: UsuarioDTO): String {
        return JWT.create()
            .withAudience(jwtconfig.AUDIENCE)
            .withIssuer(jwtconfig.ISSUER)
            .withClaim("dni", usuario.dni)
            .withClaim("rol", usuario.rol)
            .withExpiresAt(Date(System.currentTimeMillis() + jwtconfig.EXPIRATION_TIME))
            .sign(Algorithm.HMAC256(jwtconfig.SECRET))
    }

}