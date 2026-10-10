package com.example

import kotlinx.serialization.Serializable

@Serializable
data class AuthDraft(val email: String, val password: String)

@Serializable
data class TokenResponse(val token: String)

data class User(val email: String, val passwordHash: String)

val users = mutableListOf<User>()

const val JWT_SECRET = "ithub-secret"
const val JWT_ISSUER = "ithub"
