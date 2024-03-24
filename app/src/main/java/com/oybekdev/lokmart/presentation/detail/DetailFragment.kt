package com.oybekdev.lokmart.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oybekdev.lokmart.R
import com.oybekdev.lokmart.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment:Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel by viewModels<DetailViewModel>()
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getProduct(args.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() = with(binding){
        viewModel.loading.observe(viewLifecycleOwner){
            loading.root.isVisible  = it
            plus.isVisible = it.not()
            buttonDivider.isVisible = it.not()
        }
        viewModel.error.observe(viewLifecycleOwner){
            error.root.isVisible  = it
            plus.isVisible = it.not()
            buttonDivider.isVisible = it.not()
        }
        viewModel.detail.observe(viewLifecycleOwner){
            val color = if (it.favorite) R.color.orange else R.color.dark
            val resolved = ResourcesCompat.getColor(resources, color, null)
            favorite.setColorFilter(resolved)
        }
    }

    private fun initUi() = with(binding){
        back.setOnClickListener {
            findNavController().popBackStack()
        }
        error.retry.setOnClickListener {
            viewModel.getProduct(args.id)
        }
    }
}
//17:46