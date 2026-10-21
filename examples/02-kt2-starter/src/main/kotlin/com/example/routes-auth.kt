package com.example

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes() {
    post("/register") {
        val draft = call.receive<AuthDraft>()
        if (users.any { it.email == draft.email }) {
            call.respond(HttpStatusCode.Conflict, ErrorBody("email taken"))
            return@post
        }
        users += User(draft.email, hashPassword(draft.password))
        call.respond(HttpStatusCode.Created, Ok())
    }
    post("/login") {
        val draft = call.receive<AuthDraft>()
        val user = users.find { it.email == draft.email }
        if (user == null || !checkPassword(draft.password, user.passwordHash)) {
            call.respond(HttpStatusCode.Unauthorized, ErrorBody("bad credentials"))
            return@post
        }
        call.respond(TokenResponse(makeToken(user.email)))
    }
}
