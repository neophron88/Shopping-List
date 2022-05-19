package org.rasulov.shoppinglist.presentation.main_activity.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import org.rasulov.shoppinglist.R
import org.rasulov.shoppinglist.databinding.ItemDisabledBinding
import org.rasulov.shoppinglist.databinding.ItemEnabledBinding
import org.rasulov.shoppinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, Holder>(ShopItemDiffCallback()) {


    var shopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var shopItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layout = if (viewType == ENABLED) R.layout.item_enabled else R.layout.item_disabled
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val shopItem = getItem(position)
        val bind = holder.binding

        bind.root.setOnLongClickListener {
            shopItemLongClickListener?.let {
                it.invoke(shopItem)
                return@setOnLongClickListener true
            }
            false
        }
        bind.root.setOnClickListener {
            shopItemClickListener?.invoke(shopItem.id)
        }

        when (bind) {
            is ItemEnabledBinding -> {
                bind.name.text = shopItem.name
                bind.quantity.text = shopItem.quantity.toString()
            }
            is ItemDisabledBinding -> {
                bind.name.text = shopItem.name
                bind.quantity.text = shopItem.quantity.toString()
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        val isEnabled = getItem(position).isEnabled
        return if (isEnabled) ENABLED else DISABLED
    }


    companion object {
        const val ENABLED = 1
        const val DISABLED = 0
    }


}