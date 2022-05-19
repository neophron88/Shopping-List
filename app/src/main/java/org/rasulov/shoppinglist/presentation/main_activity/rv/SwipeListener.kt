package org.rasulov.shoppinglist.presentation.main_activity.rv

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeListener : ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
) {

    var onSwipeListener: ((Int) -> Unit)? = null

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onSwipeListener?.invoke(viewHolder.adapterPosition)
    }
}