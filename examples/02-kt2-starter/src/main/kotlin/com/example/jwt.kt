package com.example

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun makeToken(email: String, role: String): String =
    JWT.create()
        .withIssuer(JWT_ISSUER)
        .withClaim("email", email)
        .withClaim("role", role)
        .sign(Algorithm.HMAC256(JWT_SECRET))

fun AuthenticationConfig.jwtAuth() {
    jwt("auth-jwt") {
        verifier(
            JWT.require(Algorithm.HMAC256(JWT_SECRET))
                .withIssuer(JWT_ISSUER)
                .build()
        )
        validate { cred ->
            val email = cred.payload.getClaim("email").asString()
            if (email.isNullOrBlank()) null else JWTPrincipal(cred.payload)
        }
    }
}
