package org.rasulov.shoppinglist.domain

interface ShopListRepository {

    fun addShopItemUseCase(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(id: Int): ShopItem

    fun getShopList(): List<ShopItem>

    fun removeShopItem(shopItem: ShopItem)

}
