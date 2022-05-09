package org.rasulov.shoppinglist.presentation.rv

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.rasulov.shoppinglist.R
import org.rasulov.shoppinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, Holder>(ShopItemDiffCallback()) {


    var shopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var shopItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layout = if (viewType == ENABLED) R.layout.item_enabled else R.layout.item_disabled
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val shopItem = getItem(position)
        holder.name.text = shopItem.name
        holder.quantity.text = shopItem.quantity.toString()
        holder.itemView.setOnLongClickListener {
            shopItemLongClickListener?.let {
                it.invoke(shopItem)
                return@setOnLongClickListener true
            }
            false
        }
        holder.itemView.setOnClickListener {
            shopItemClickListener?.let {

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