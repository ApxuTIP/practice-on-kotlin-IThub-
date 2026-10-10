package com.example

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun dbAll(category: String?): List<Item> = transaction {
    val query = if (category.isNullOrBlank()) ItemsTable.selectAll()
    else ItemsTable.selectAll().where { ItemsTable.category eq category }
    query.map { it.toItem() }
}

fun dbGet(id: Int): Item? = transaction {
    ItemsTable.selectAll().where { ItemsTable.id eq id }.singleOrNull()?.toItem()
}

fun dbInsert(draft: ItemDraft): Item = transaction {
    val id = ItemsTable.insert {
        it[name] = draft.name
        it[price] = draft.price
        it[category] = draft.category
    } get ItemsTable.id
    Item(id, draft.name, draft.price, draft.category)
}

fun dbUpdate(id: Int, draft: ItemDraft): Item? = transaction {
    val n = ItemsTable.update({ ItemsTable.id eq id }) {
        it[name] = draft.name
        it[price] = draft.price
        it[category] = draft.category
    }
    if (n == 0) null else Item(id, draft.name, draft.price, draft.category)
}

fun dbDelete(id: Int): Boolean = transaction {
    ItemsTable.deleteWhere { ItemsTable.id eq id } > 0
}

private fun ResultRow.toItem() = Item(
    id = this[ItemsTable.id],
    name = this[ItemsTable.name],
    price = this[ItemsTable.price],
    category = this[ItemsTable.category],
)
