package org.rasulov.shoppinglist.domain

data class ShopItem(
    val id: Int,
    val name: String,
    val quantity: Int,
    val isEnabled: Boolean
)
