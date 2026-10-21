package com.example

import org.jetbrains.exposed.sql.Table

object Products : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 100)
    val price = integer("price")
    var city = varchar("city", 100)
    override val primaryKey = PrimaryKey(id)
}
