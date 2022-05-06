package org.rasulov.shoppinglist.domain

data class ShopItem(
    var name: String,
    var quantity: Int,
    var isEnabled: Boolean,
    var id: Int = UNDEFINED
) {
    companion object {
        const val UNDEFINED = -1
    }
}


