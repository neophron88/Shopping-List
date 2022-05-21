package org.rasulov.shoppinglist.domain

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun addShopItemUseCase(shopItem: ShopItem) {
        shopListRepository.addShopItem(shopItem)
    }
}