package com.example

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.util.Collections

fun Application.installSockets() {
    install(WebSockets)
}

fun Route.chatRoutes() {
    val connections = Collections.synchronizedSet(mutableSetOf<DefaultWebSocketSession>())
    webSocket("/chat") {
        connections += this
        try {
            send("connected")
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val text = frame.readText()
                    connections.forEach { it.send(text) }
                }
            }
        } finally {
            connections -= this
        }
    }
}
