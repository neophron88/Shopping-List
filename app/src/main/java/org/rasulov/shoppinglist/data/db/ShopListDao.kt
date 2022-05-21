package org.rasulov.shoppinglist.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShopListDao {

    @Query("SELECT * FROM shop_items")
    fun getShopList(): LiveData<List<ShopItemDBModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(item: ShopItemDBModel)

    @Query("DELETE FROM shop_items where id=:id")
    suspend fun deleteShopItem(id: Int)

    @Query("SELECT *  FROM shop_items where id=:id limit 1")
    suspend fun getShopItem(id: Int): ShopItemDBModel


}