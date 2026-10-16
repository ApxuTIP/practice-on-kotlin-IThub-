package com.example

import org.jetbrains.exposed.sql.Table

object ProductsTable : Table("items") {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 100)
    val price = integer("price")
    val city = city("category", 100)
    override val primaryKey = PrimaryKey(id)
}