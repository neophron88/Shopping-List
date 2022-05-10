package org.rasulov.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doBeforeTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import org.rasulov.shoppinglist.R
import java.lang.RuntimeException

class ShopItemActivity : AppCompatActivity() {

    private lateinit var textInLayoutName: TextInputLayout
    private lateinit var textInLayoutCount: TextInputLayout
    private lateinit var edtName: EditText
    private lateinit var edtCount: EditText
    private lateinit var btnSave: Button
    private lateinit var viewModel: ShopItemViewModel

    private var onActionSave: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        textInLayoutName = findViewById(R.id.text_input_layout_name)
        textInLayoutCount = findViewById(R.id.text_input_layout_count)
        edtName = findViewById(R.id.edt_name)
        edtCount = findViewById(R.id.edt_count)
        btnSave = findViewById(R.id.btn_save)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        validateIntent()
        viewModel.errorInputName.observe(this) {
            checkTextInputLayout(textInLayoutName, it)
        }

        viewModel.errorInputCount.observe(this) {
            checkTextInputLayout(textInLayoutCount, it)
        }

        viewModel.closeActivity.observe(this) {
            if (it == true) finish()
        }
        viewModel.shopItem.observe(this) {
            it?.let {
                edtName.setText(it.name)
                edtCount.setText(it.quantity.toString())
            }
        }

        edtName.doBeforeTextChanged { _, _, _, _ -> viewModel.resetErrorInputName() }
        edtCount.doBeforeTextChanged { _, _, _, _ -> viewModel.resetErrorInputCount() }
        btnSave.setOnClickListener {
            onActionSave?.invoke()
        }
    }

    private fun validateIntent() {
        val mode = intent.getStringExtra(MODE)

        var message = ""
        if (mode == MODE_ADD) {
            onActionSave = {
                viewModel.addShopItem(
                    edtName.text.toString(),
                    edtCount.text.toString()
                )
            }
            return
        } else if (mode == MODE_EDIT) {
            val shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, -1)
            viewModel.getShopItem(shopItemId)
            if (shopItemId != -1) {
                onActionSave = {
                    viewModel.editShopItem(
                        edtName.text.toString(),
                        edtCount.text.toString()
                    )
                }
                return
            } else message = "it's required a correct item's id"

        } else message = "it's required one of the available modes"

        throw RuntimeException(message)
    }

    private fun checkTextInputLayout(textInputLayout: TextInputLayout, it: Boolean?) {
        if (it == true) {
            textInputLayout.error = " "
        } else if (it == false) {
            textInputLayout.error = null
        }
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