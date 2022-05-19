package org.rasulov.shoppinglist.presentation.shop_item

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.rasulov.shoppinglist.databinding.FragmentShopItemBinding
import org.rasulov.shoppinglist.databinding.FragmentShopItemBindingImpl
import java.lang.RuntimeException

class ShopItemFragment : Fragment() {

    private lateinit var viewModel: ShopItemViewModel


    private var _binding: FragmentShopItemBinding? = null

    private val binding: FragmentShopItemBinding
        get() = _binding ?: throw RuntimeException("Binding is null")

    private var onActionSave: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopItemBindingImpl.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        validateArguments()
        viewModel.closeActivity.observe(viewLifecycleOwner) {
            if (it == true) activity?.onBackPressed()
        }

        binding.btnSave.setOnClickListener {
            onActionSave?.invoke()
        }
    }

    private fun validateArguments() {
        val mode = arguments?.getString(MODE, "")

        if (mode == MODE_ADD) {
            onActionSave = {
                viewModel.addShopItem(
                    binding.edtName.text?.toString(),
                    binding.edtCount.text?.toString()
                )
            }

        } else if (mode == MODE_EDIT) {
            val id = arguments?.getInt(EXTRA_SHOP_ITEM_ID, -1)
                ?: throw RuntimeException("Wrong ShopItemId")
            viewModel.getShopItem(id)
            onActionSave = {
                viewModel.editShopItem(
                    binding.edtName.text?.toString(),
                    binding.edtCount.text?.toString()
                )
            }
        } else throw RuntimeException("Something went Wrong")

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
