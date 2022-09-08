package org.rasulov.shoppinglist.domain

import javax.inject.Inject

class AddShopItemUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository
) {
    suspend fun addShopItemUseCase(shopItem: ShopItem) {
        shopListRepository.addShopItem(shopItem)
    }
}