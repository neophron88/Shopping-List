package org.rasulov.shoppinglist.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDBModel::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun shopListDao(): ShopListDao

    companion object {
        private var instance: AppDataBase? = null
        private val lock = Any()

        fun getInstance(application: Application): AppDataBase {
            instance?.let { return it }
            synchronized(lock) {
                instance?.let { return it }
                val db =
                    Room.databaseBuilder(application, AppDataBase::class.java, "shop_items.db")
                        .build()
                instance = db
                return db
            }
        }
    }
}