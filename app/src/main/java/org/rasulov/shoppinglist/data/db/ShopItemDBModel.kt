package org.rasulov.shoppinglist.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_items")
data class ShopItemDBModel(

    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name: String,
    val quantity: Int,
    val isEnabled: Boolean,

)
