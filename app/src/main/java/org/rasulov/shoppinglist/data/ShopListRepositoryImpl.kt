package org.rasulov.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.rasulov.shoppinglist.domain.ShopItem
import org.rasulov.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {

    private val data: MutableList<ShopItem> = mutableListOf()
    private var idCounter = 0

    val liveData = MutableLiveData<List<ShopItem>>()

    override fun addShopItem(shopItem: ShopItem) {
        autoIncrementId(shopItem)
        data.add(shopItem)
        update()
    }

    private fun autoIncrementId(shopItem: ShopItem) {
        shopItem.id = ++idCounter
    }

    override fun editShopItem(shopItem: ShopItem) {
        val item = getShopItem(shopItem.id)
        item.name = shopItem.name
        item.quantity = shopItem.quantity
        item.isEnabled = shopItem.isEnabled
        update()
    }

    override fun getShopItem(id: Int): ShopItem {
        return data.find { it.id == id }
            ?: throw RuntimeException("Element with id $id is not found")

    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return liveData
    }

    override fun removeShopItem(shopItem: ShopItem) {
        data.remove(shopItem)
        update()
    }

    private fun update() {
        liveData.value = data.toList()
    }

}