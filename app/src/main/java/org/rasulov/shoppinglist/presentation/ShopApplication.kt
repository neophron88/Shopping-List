package org.rasulov.shoppinglist.presentation

import android.app.Application
import org.rasulov.shoppinglist.di.DaggerApplicationComponent

class ShopApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}