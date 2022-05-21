package org.rasulov.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import org.rasulov.shoppinglist.data.db.AppDataBase
import org.rasulov.shoppinglist.data.db.ShopListMapper
import org.rasulov.shoppinglist.domain.ShopItem
import org.rasulov.shoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl(application: Application) : ShopListRepository {


    private val db = AppDataBase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override suspend fun addShopItem(shopItem: ShopItem) {
        val item = mapper.mapEntityToDBModel(shopItem)
        db.addShopItem(item)
    }


    override suspend fun editShopItem(shopItem: ShopItem) {
        val item = mapper.mapEntityToDBModel(shopItem)
        db.addShopItem(item)

    }

    override suspend fun getShopItem(id: Int): ShopItem {
        val item = db.getShopItem(id)
        return mapper.mapDBModelToEntity(item)


    }

    override  fun getShopList(): LiveData<List<ShopItem>> {
        return Transformations.map(db.getShopList()) {
            mapper.mapDBModelListToEntityList(it)
        }
    }

    override suspend fun removeShopItem(shopItem: ShopItem) {
        db.deleteShopItem(shopItem.id)
    }

}