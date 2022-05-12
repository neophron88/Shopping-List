package org.rasulov.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.rasulov.shoppinglist.R
import org.rasulov.shoppinglist.presentation.rv.ShopListAdapter
import org.rasulov.shoppinglist.presentation.rv.SwipeListener

class MainActivity : AppCompatActivity() {

    private var count = 0
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ShopListAdapter
    private lateinit var floatBtnAdd: FloatingActionButton
    private lateinit var viewModel: MainViewModel
    private lateinit var itemTouchHelper: ItemTouchHelper

    private var container: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("it0088", "onCreate: MainActivity")

        recycler = findViewById(R.id.rv_shop_list)
        adapter = ShopListAdapter()
        floatBtnAdd = findViewById(R.id.floating_add_item)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        container = findViewById(R.id.shop_item_container)

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

            if (container != null) {
                applyFragment(ShopItemFragment.newEditMode(it))
            } else {
                val newIntentEditMode = ShopItemActivity.newIntentEditMode(this, it)
                startActivity(newIntentEditMode)
            }

        }

        viewModel.shopList.observe(this) {
            adapter.submitList(it)
        }

        floatBtnAdd.setOnClickListener {
            if (container != null) {
                applyFragment(ShopItemFragment.newAddMode())
            } else {
                val newIntentAddMode = ShopItemActivity.newIntentAddMode(this)
                startActivity(newIntentAddMode)
            }
        }
    }


    private fun applyFragment(fragment: ShopItemFragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()

    }

}


