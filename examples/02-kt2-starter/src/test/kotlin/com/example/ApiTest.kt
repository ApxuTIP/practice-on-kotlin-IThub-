package com.example

import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApiTest {

    private suspend fun ApplicationTestBuilder.getAuthToken(): String {
        val client = createClient {
            install(ContentNegotiation) { json() }
        }

        client.post("/register") {
            contentType(ContentType.Application.Json)
            setBody(AuthDraft("test@test.com", "1234", "admin"))
        }

        val loginResponse = client.post("/login") {
            contentType(ContentType.Application.Json)
            setBody(AuthDraft("test@test.com", "1234", "admin"))
        }

        val tokenResponse: TokenResponse = loginResponse.body()
        return tokenResponse.token
    }

    @Test
    fun listIsEmpty() = testApplication {
        application { module() }
        val token = getAuthToken()

        val response = client.get("/items") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("[]", response.bodyAsText())
    }

    @Test
    fun postCreatesItem() = testApplication {
        application { module() }
        val token = getAuthToken()

        val http = createClient {
            install(ContentNegotiation) { json() }
        }

        val response = http.post("/items") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(ItemDraft("Чай", 90, "drinks"))
        }

        assertEquals(HttpStatusCode.Created, response.status)
        assertTrue(response.bodyAsText().contains("Чай"))
    }

    @Test
    fun missingIdIs404Json() = testApplication {
        application { module() }
        val token = getAuthToken()

        val response = client.get("/items/999") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }

        assertEquals(HttpStatusCode.NotFound, response.status)
        assertTrue(response.bodyAsText().contains("error"))
    }
}
