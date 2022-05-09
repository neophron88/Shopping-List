package org.rasulov.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.rasulov.shoppinglist.data.ShopListRepositoryImpl
import org.rasulov.shoppinglist.domain.AddShopItemUseCase
import org.rasulov.shoppinglist.domain.EditShopItemUseCase
import org.rasulov.shoppinglist.domain.GetShopItemUseCase
import org.rasulov.shoppinglist.domain.ShopItem

class ShopItemViewModel : ViewModel() {


    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean> get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean> get() = _errorInputCount

    private val _closeActivity = MutableLiveData<Boolean>()
    val closeActivity: LiveData<Boolean> get() = _closeActivity


    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem> get() = _shopItem


    fun getShopItem(id: Int) {
        _shopItem.postValue(getShopItemUseCase.getShopItem(id))
    }

    fun addShopItem(name: String?, count: String?) {
        if (areFieldsValid(name, count)) {
            val item = ShopItem(name!!, count!!.toInt(), true)
            addShopItemUseCase.addShopItemUseCase(item)
            _closeActivity.postValue(true)
        }
    }

    fun editShopItem(name: String?, count: String?) {
        if (areFieldsValid(name, count)) {
            _shopItem.value?.let {
                val item = it.copy(name = name!!, quantity = count!!.toInt())
                editShopItemUseCase.editShopItem(item)
                _closeActivity.postValue(true)
            }
        }
    }

    private fun areFieldsValid(name: String?, count: String?): Boolean {
        val clearedName = name?.trim() ?: ""
        val parsedCount =
            count?.trim()?.toInt() ?: 0  //be careful it may throw a parse exception but unlikely
        return validate(clearedName, parsedCount)
    }

    private fun validate(name: String, count: Int): Boolean {
        if (name.isEmpty()) {
            _errorInputName.postValue(false)
            return false
        }
        if (count < 1) {
            _errorInputCount.postValue(false)
            return false
        }
        return true
    }

    public fun resetErrorInputName() {
        _errorInputName.value = true
    }

    public fun resetErrorInputCount() {
        _errorInputCount.value = true
    }
}