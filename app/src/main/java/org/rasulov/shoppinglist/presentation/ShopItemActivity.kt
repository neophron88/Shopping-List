package org.rasulov.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.rasulov.shoppinglist.R
import java.lang.RuntimeException

class ShopItemActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        validateIntent()

    }

    private fun validateIntent() {
        val mode = intent.getStringExtra(MODE)

        if (mode == MODE_ADD) {

            applyFragment(ShopItemFragment.newAddMode())

            return

        } else if (mode == MODE_EDIT) {

            val shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, -1)

            if (shopItemId != -1) {

                applyFragment(ShopItemFragment.newAddMode())

                return

            } else throwError("it's required a correct item's id")

        } else throwError("it's required one of the available modes")

    }

    private fun throwError(message: String) {
        throw RuntimeException(message)
    }

    private fun applyFragment(fragment: ShopItemFragment) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.shop_item_container, fragment)
            .commit()
    }


    companion object {

        private const val MODE = "mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"

        fun newIntentEditMode(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditMode(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }
}