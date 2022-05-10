package org.rasulov.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.rasulov.shoppinglist.R
import org.rasulov.shoppinglist.presentation.rv.ShopListAdapter
import org.rasulov.shoppinglist.presentation.rv.SwipeListener

class MainActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ShopListAdapter
    private lateinit var floatBtnAdd: FloatingActionButton
    private lateinit var viewModel: MainViewModel
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler = findViewById(R.id.rv_shop_list)
        adapter = ShopListAdapter()
        floatBtnAdd = findViewById(R.id.floating_add_item)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        val swipeListener = SwipeListener().apply {
            onSwipeListener = {
                viewModel.removeShopItem(adapter.currentList[it])
            }
        }
        itemTouchHelper = ItemTouchHelper(swipeListener)
        itemTouchHelper.attachToRecyclerView(recycler)
        recycler.adapter = adapter
        adapter.shopItemLongClickListener = {
            viewModel.editShopItem(it)
        }
        adapter.shopItemClickListener = {
            val newIntentAddMode = ShopItemActivity.newIntentEditMode(this, it)
            startActivity(newIntentAddMode)
        }

        viewModel.shopList.observe(this) {
            adapter.submitList(it)
        }

        floatBtnAdd.setOnClickListener {
            val newIntentAddMode = ShopItemActivity.newIntentEditMode(this)
            startActivity(newIntentAddMode)
        }

    }
}


