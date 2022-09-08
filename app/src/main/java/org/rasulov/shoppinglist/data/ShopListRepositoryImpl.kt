package org.rasulov.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import org.rasulov.shoppinglist.data.db.ShopListDao
import org.rasulov.shoppinglist.data.db.ShopListMapper
import org.rasulov.shoppinglist.domain.ShopItem
import org.rasulov.shoppinglist.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper
) : ShopListRepository {

    override suspend fun addShopItem(shopItem: ShopItem) {
        val item = mapper.mapEntityToDBModel(shopItem)
        shopListDao.addShopItem(item)
    }


    override suspend fun editShopItem(shopItem: ShopItem) {
        val item = mapper.mapEntityToDBModel(shopItem)
        shopListDao.addShopItem(item)

    }

    override suspend fun getShopItem(id: Int): ShopItem {
        val item = shopListDao.getShopItem(id)
        return mapper.mapDBModelToEntity(item)


    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return Transformations.map(shopListDao.getShopList()) {
            mapper.mapDBModelListToEntityList(it)
        }
    }

    override suspend fun removeShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

}