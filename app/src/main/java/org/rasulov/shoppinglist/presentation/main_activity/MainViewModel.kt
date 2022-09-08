package org.rasulov.shoppinglist.presentation.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.rasulov.shoppinglist.domain.EditShopItemUseCase
import org.rasulov.shoppinglist.domain.GetShopListUseCase
import org.rasulov.shoppinglist.domain.RemoveShopItemUseCase
import org.rasulov.shoppinglist.domain.ShopItem
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getShopListUseCase: GetShopListUseCase,
    private val removeShopItemUseCase: RemoveShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase
) : ViewModel() {


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