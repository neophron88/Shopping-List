package org.rasulov.shoppinglist.presentation.main_activity

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.rasulov.shoppinglist.data.ShopListRepositoryImpl
import org.rasulov.shoppinglist.domain.EditShopItemUseCase
import org.rasulov.shoppinglist.domain.GetShopListUseCase
import org.rasulov.shoppinglist.domain.RemoveShopItemUseCase
import org.rasulov.shoppinglist.domain.ShopItem

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)


    val shopList = getShopListUseCase.getShopList()


    fun removeShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            removeShopItemUseCase.removeShopItem(shopItem)
        }
    }

    fun editShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            val editedItem = shopItem.copy(isEnabled = !shopItem.isEnabled)
            editShopItemUseCase.editShopItem(editedItem)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}