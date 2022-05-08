package org.rasulov.shoppinglist.domain

data class ShopItem(
    val name: String,
    val quantity: Int,
    val isEnabled: Boolean,
    var id: Int = UNDEFINED
) {
    companion object {
        const val UNDEFINED = -1
    }
}


