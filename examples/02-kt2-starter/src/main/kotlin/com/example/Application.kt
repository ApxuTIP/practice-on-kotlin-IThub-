package com.example

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.install
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import com.example.Products
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

@Serializable
data class Ok(val ok: Boolean = true)

@Serializable
data class ErrorBody(val error: String)

@Serializable
data class Avito(
    val id: Int,
    val title: String,
    val price: Int,
    var city: String,
)

@Serializable
data class ItemDraft(val name: String, val price: Int, val category: String)


fun main() {

    Database.connect(
        url = "jdbc:sqlite:data.db",
        driver = "org.sqlite.JDBC"
    )

    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    embeddedServer(Netty, port = port, host = "127.0.0.1") {

        var items = mutableListOf<Avito>()
        var nextId = 1

        install(ContentNegotiation) { json() }

        install(Authentication) { jwtAuth() }

        routing {
            get("/") {
                call.respond(Ok())
            }

            authRoutes()

            authenticate("auth-jwt") {
                get("/items") {
                    val city = call.request.queryParameters["city"]
                    val result = if (city.isNullOrBlank()) items
                    else items.filter { it.city == city }
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

                post("/items") {
                    val draft = call.receive<ItemDraft>()
                    val created = Avito(nextId++, draft.name, draft.price, draft.category)
                    items += created
                    call.respond(HttpStatusCode.Created, created)
                }

                put("/items/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond(HttpStatusCode.BadRequest, ErrorBody("id must be int"))
                        return@put
                    }
                    val draft = call.receive<ItemDraft>()
                    val index = items.indexOfFirst { it.id == id }
                    if (index < 0) {
                        call.respond(HttpStatusCode.NotFound, ErrorBody("not found"))
                        return@put
                    }
                    items[index] = Avito(id, draft.name, draft.price, draft.category)
                    call.respond(items[index])
                }

                delete("/items/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond(HttpStatusCode.BadRequest, ErrorBody("id must be int"))
                        return@delete
                    }
                    if (!items.removeIf { it.id == id }) {
                        call.respond(HttpStatusCode.NotFound, ErrorBody("not found"))
                    } else {
                        call.respond(HttpStatusCode.NoContent)
                    }
                }

                post("/create-table") {
                    transaction {
                        SchemaUtils.create(Products)
                    }
                    call.respond(Ok())
                }
            }
        }
    }.start(wait = true)
}
