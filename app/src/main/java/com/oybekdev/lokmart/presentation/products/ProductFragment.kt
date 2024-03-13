package com.oybekdev.lokmart.presentation.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oybekdev.lokmart.data.api.product.dto.Product
import com.oybekdev.lokmart.databinding.FragmentProductsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment:Fragment() {

    private lateinit var binding:FragmentProductsBinding
    private val viewModel by viewModels<ProductsViewModels>()
    private val args by navArgs<ProductFragmentArgs>()
    private val adapter by lazy {
        ProductsAdapter(this::onClick, this::like)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setCategory(args.category)

        adapter.addLoadStateListener {
            viewModel.setLoadStates(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()

    }
    private fun initUi() = with(binding){
        back.setOnClickListener{
            findNavController().popBackStack()
        }
        error.retry.setOnClickListener {
            viewModel.getProducts()
        }
        products.adapter = adapter
    }
    private fun subsribeToLiveData() = with(binding){
        viewModel.loading.observe(viewLifecycleOwner){
            loading.root.isVisible = it
        }
        viewModel.error.observe(viewLifecycleOwner){
            error.root.isVisible = it
        }
        viewModel.products.observe(viewLifecycleOwner){
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
        viewModel.category.observe(viewLifecycleOwner){
            title.text = it.title
        }
    }

    private fun onClick(product:Product){

    }
    private fun like(product:Product){

    }
}