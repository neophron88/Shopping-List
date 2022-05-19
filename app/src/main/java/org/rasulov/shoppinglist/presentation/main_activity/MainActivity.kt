package org.rasulov.shoppinglist.presentation.main_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import org.rasulov.shoppinglist.R
import org.rasulov.shoppinglist.databinding.ActivityMainBinding
import org.rasulov.shoppinglist.presentation.shop_item.ShopItemActivity
import org.rasulov.shoppinglist.presentation.shop_item.ShopItemFragment
import org.rasulov.shoppinglist.presentation.main_activity.rv.ShopListAdapter
import org.rasulov.shoppinglist.presentation.main_activity.rv.SwipeListener

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ShopListAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var itemTouchHelper: ItemTouchHelper

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ShopListAdapter()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val swipeListener = SwipeListener().apply {
            onSwipeListener = {
                viewModel.removeShopItem(adapter.currentList[it])
            }
        }
        itemTouchHelper = ItemTouchHelper(swipeListener)
        itemTouchHelper.attachToRecyclerView(binding.rvShopList)
        binding.rvShopList.adapter = adapter
        adapter.shopItemLongClickListener = {
            viewModel.editShopItem(it)
        }
        adapter.shopItemClickListener = {

            if (binding.shopItemContainer != null) {
                applyFragment(ShopItemFragment.newEditMode(it))
            } else {
                val newIntentEditMode = ShopItemActivity.newIntentEditMode(this, it)
                startActivity(newIntentEditMode)
            }

        }

        viewModel.shopList.observe(this) {
            adapter.submitList(it)
        }

        binding.floatingAddItem.setOnClickListener {
            if (binding.shopItemContainer != null) {
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


