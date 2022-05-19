package org.rasulov.shoppinglist.presentation.shop_item

import android.util.Log
import android.widget.EditText
import androidx.core.widget.doBeforeTextChanged
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import org.rasulov.shoppinglist.domain.ShopItem

@BindingAdapter("isError")
fun setError(view: TextInputLayout, isError: Boolean?) {
    if (isError == true) {
        view.error = " "
    } else if (isError == false) {
        view.error = null
    }
    Log.d("iit0088", "setError: ")
}

@BindingAdapter("setShopItemName")
fun setShopItemName(view: EditText, item: ShopItem?) {
    item?.let {
        view.setText(it.name)
    }
    Log.d("iit0088", "setShopItemName: $item")

}

@BindingAdapter("setShopItemCount")
fun setShopItemCount(view: EditText, item: ShopItem?) {
    item?.let {
        view.setText(it.quantity.toString())
    }
    Log.d("iit0088", "setShopItemCount: $item")
}


@BindingAdapter("doBeforeChange")
fun doBeforeChange(view: EditText, doBefore: DoBefore) {
    view.doBeforeTextChanged { _, _, _, _ ->
        doBefore.doBefore()
    }
    Log.d("iit0088", "doBeforeChange: ")
}


fun interface DoBefore {
    fun doBefore()
}