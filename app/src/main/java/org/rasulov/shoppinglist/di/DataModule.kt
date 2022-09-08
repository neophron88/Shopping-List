package org.rasulov.shoppinglist.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import org.rasulov.shoppinglist.data.ShopListRepositoryImpl
import org.rasulov.shoppinglist.data.db.AppDataBase
import org.rasulov.shoppinglist.data.db.ShopListDao
import org.rasulov.shoppinglist.domain.ShopListRepository

@Module
interface DataModule {


    @ApplicationScope
    @Binds
    fun bindShopListRepository(impl: ShopListRepositoryImpl): ShopListRepository

    companion object {
        @ApplicationScope
        @Provides
        fun provideShopListDao(application: Application): ShopListDao {
            return AppDataBase.getInstance(application).shopListDao()
        }
    }
}