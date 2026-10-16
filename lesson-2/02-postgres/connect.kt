package com.example

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun connectDb() {
    Database.connect(
        url = "jdbc:postgresql://127.0.0.1:5432/shop",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "pass",
    )
    transaction {
        SchemaUtils.create(ItemsTable)
    }
}
