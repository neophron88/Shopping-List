package org.rasulov.shoppinglist.presentation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
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


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("it0088", "onAttach: ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("it0088", "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("it0088", "onCreateView: ")
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("it0088", "onViewCreated: ")
        textInLayoutName = view.findViewById(R.id.text_input_layout_name)
        textInLayoutCount = view.findViewById(R.id.text_input_layout_count)
        edtName = view.findViewById(R.id.edt_name)
        edtCount = view.findViewById(R.id.edt_count)
        btnSave = view.findViewById(R.id.btn_save)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        validateArguments()

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

    private fun validateArguments() {
        val mode = arguments?.getString(MODE, "")

        if (mode == MODE_ADD) {
            onActionSave = {
                viewModel.addShopItem(
                    edtName.text?.toString(),
                    edtCount.text?.toString()
                )
            }

        } else if (mode == MODE_EDIT) {
            val id = arguments?.getInt(EXTRA_SHOP_ITEM_ID, -1)
                ?: throw RuntimeException("Wrong ShopItemId")
            viewModel.getShopItem(id)
            onActionSave = {
                viewModel.editShopItem(
                    edtName.text?.toString(),
                    edtCount.text?.toString()
                )
            }
        } else throw RuntimeException("Something went Wrong")

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("it0088", "onActivityCreated: ")
    }

    override fun onStart() {
        super.onStart()
        Log.d("it0088", "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d("it0088", "onResume: ")
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

        fun newEditMode(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(MODE, MODE_EDIT)
                    putInt(EXTRA_SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }
}
