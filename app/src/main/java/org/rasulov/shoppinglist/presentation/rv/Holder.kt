package org.rasulov.shoppinglist.presentation.rv

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.rasulov.shoppinglist.R

class Holder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.name)
    val quantity: TextView = view.findViewById(R.id.quantity)
}