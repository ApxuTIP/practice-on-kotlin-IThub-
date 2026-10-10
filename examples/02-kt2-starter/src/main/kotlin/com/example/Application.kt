package com.example

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class Ok(val ok: Boolean = true)

@Serializable
data class ErrorBody(val error: String)

@Serializable
data class Item(
    val id: Int,
    val name: String,
    val price: Int,
    val category: String,
)

val items = mutableListOf<Item>()
var nextId = 1

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    embeddedServer(Netty, port = port, host = "127.0.0.1") {
        install(ContentNegotiation) { json() }
        routing {
            get("/") {
                call.respond(Ok())
            }
            get("/items") {
                val category = call.request.queryParameters["category"]
                val result = if (category.isNullOrBlank()) items
                else items.filter { it.category == category }
                call.respond(result)
            }
            get("/items/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, ErrorBody("id must be int"))
                    return@get
                }
                val item = items.find { it.id == id }
                if (item == null) {
                    call.respond(HttpStatusCode.NotFound, ErrorBody("not found"))
                } else {
                    call.respond(item)
                }
            }
            // POST /items, PUT /items/{id}, DELETE /items/{id} —
            // копируй из lesson-2/01-memory/routes.kt
        }
    }.start(wait = true)
}
