package org.rasulov.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.rasulov.shoppinglist.R
import org.rasulov.shoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.Holder>() {


    var data = listOf<ShopItem>()
        set(value) {
            val diffUtil = ShopListDiffCallback(field, value)
            val calculateDiff = DiffUtil.calculateDiff(diffUtil)
            calculateDiff.dispatchUpdatesTo(this)
            field = value
        }

    var shopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var shopItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layout = if (viewType == ENABLED) R.layout.item_enabled else R.layout.item_disabled
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val shopItem = data[position]
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


    override fun getItemCount(): Int = data.size


    override fun getItemViewType(position: Int): Int {
        val isEnabled = data[position].isEnabled
        return if (isEnabled) ENABLED else DISABLED
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val quantity: TextView = view.findViewById(R.id.quantity)
    }


    companion object {
        const val ENABLED = 1
        const val DISABLED = 0
    }


}