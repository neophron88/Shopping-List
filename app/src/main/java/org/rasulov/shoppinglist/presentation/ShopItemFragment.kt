package org.rasulov.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doBeforeTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import org.rasulov.shoppinglist.R
import java.lang.RuntimeException

class ShopItemFragment : Fragment() {


    private lateinit var textInLayoutName: TextInputLayout
    private lateinit var textInLayoutCount: TextInputLayout
    private lateinit var edtName: EditText
    private lateinit var edtCount: EditText
    private lateinit var btnSave: Button
    private lateinit var viewModel: ShopItemViewModel
    private var onActionSave: (() -> Unit)? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textInLayoutName = view.findViewById(R.id.text_input_layout_name)
        textInLayoutCount = view.findViewById(R.id.text_input_layout_count)
        edtName = view.findViewById(R.id.edt_name)
        edtCount = view.findViewById(R.id.edt_count)
        btnSave = view.findViewById(R.id.btn_save)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        validateIntent()

        viewModel.errorInputName.observe(viewLifecycleOwner) {
            checkTextInputLayout(textInLayoutName, it)
        }

        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            checkTextInputLayout(textInLayoutCount, it)
        }

        viewModel.closeActivity.observe(viewLifecycleOwner) {
            if (it == true) activity?.onBackPressed()
        }

        viewModel.shopItem.observe(viewLifecycleOwner) {
            it?.let {
                edtName.setText(it.name)
                edtCount.setText(it.quantity.toString())
            }
        }

        edtName.doBeforeTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputName()
        }

        edtCount.doBeforeTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputCount()
        }

        btnSave.setOnClickListener {
            onActionSave?.invoke()
        }
    }

    private fun validateIntent() {
        val mode = ""

        onActionSave = when (mode) {
            MODE_ADD -> {
                viewModel.addShopItem(
                    edtName.text?.toString(),
                    edtCount.text?.toString()
                )
            }

            MODE_EDIT ->{
                viewModel.editShopItem(
                    edtName.text?.toString(),
                    edtCount.text?.toString()
                )
            }

            else  throw RuntimeException("Something went Wrong")
        }
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

        fun newAddMode(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(MODE, MODE_ADD)
                }
            }
        }

        fun newEditMode(shopItemId: Int): Intent {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(MODE, MODE_EDIT)
                    putString(EXTRA_SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }
}