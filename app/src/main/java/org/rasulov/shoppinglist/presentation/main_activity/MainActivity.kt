package org.rasulov.shoppinglist.presentation.main_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import org.rasulov.shoppinglist.R
import org.rasulov.shoppinglist.databinding.ActivityMainBinding
import org.rasulov.shoppinglist.domain.ShopItem
import org.rasulov.shoppinglist.presentation.ShopApplication
import org.rasulov.shoppinglist.presentation.ViewModelFactory
import org.rasulov.shoppinglist.presentation.shop_item.ShopItemActivity
import org.rasulov.shoppinglist.presentation.shop_item.ShopItemFragment
import org.rasulov.shoppinglist.presentation.main_activity.rv.ShopListAdapter
import org.rasulov.shoppinglist.presentation.main_activity.rv.SwipeListener
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ShopListAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var itemTouchHelper: ItemTouchHelper

    private lateinit var binding: ActivityMainBinding


    private val component by lazy {
        (application as ShopApplication).component
    }

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ShopListAdapter()
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

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


        val observer: Observer<Collection<ShopItem>> =
            Observer<Collection<ShopItem>> {
                adapter.submitList(it as List<ShopItem>)
            }

        viewModel.shopList.observe(this, observer)

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


