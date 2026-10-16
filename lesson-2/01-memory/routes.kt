package com.example

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.itemRoutes() {
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
        if (item == null) call.respond(HttpStatusCode.NotFound, ErrorBody("not found"))
        else call.respond(item)
    }
    post("/items") {
        val draft = call.receive<ItemDraft>()
        val created = Item(nextId++, draft.name, draft.price, draft.category)
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
        val updated = Item(id, draft.name, draft.price, draft.category)
        items[index] = updated
        call.respond(updated)
    }
    delete("/items/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, ErrorBody("id must be int"))
            return@delete
        }
        val removed = items.removeIf { it.id == id }
        if (!removed) call.respond(HttpStatusCode.NotFound, ErrorBody("not found"))
        else call.respond(HttpStatusCode.NoContent)
    }
}
