package com.example

import org.jetbrains.exposed.sql.Table

object ItemsTable : Table("items") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val price = integer("price")
    val category = varchar("category", 100)
    override val primaryKey = PrimaryKey(id)
}
