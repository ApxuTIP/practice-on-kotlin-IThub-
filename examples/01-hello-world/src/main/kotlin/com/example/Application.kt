package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    embeddedServer(Netty, port = port, host = "127.0.0.1") {
        routing {
            get("/") {
                call.respondText("Hello World!")
            }
            get("/hello/{name}") {
                call.respondText ("{\"foo\" : \"bar\"}")
            }
            get("/products") {
                call.respondText("Список из 4 товаров: id, name, price")
            }
            get("/products/{id}") {
                call.respondText("Один товар. Нет такого id — 404")
            }
            get("/products?maxPrice=100") {
                call.respondText("Только товары не дороже 100")
            }
        }
    }.start(wait = true)
}