package com.example

import kotlinx.serialization.Serializable

@Serializable
data class Ok(val ok: Boolean = true)

@Serializable
data class ErrorBody(val error: String)

@Serializable
data class Item(
    val id: Int,
    val name: String,
    val price: Int,
    val category: String,
)

@Serializable
data class ItemDraft(
    val name: String,
    val price: Int,
    val category: String,
)
