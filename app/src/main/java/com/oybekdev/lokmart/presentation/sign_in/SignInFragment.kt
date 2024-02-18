package com.oybekdev.lokmart.presentation.sign_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.oybekdev.lokmart.R
import com.oybekdev.lokmart.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment:Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() = with(binding) {
        viewModel.loading.observe(viewLifecycleOwner){ isLoading ->
            progressBar.isVisible = isLoading
        }
        viewModel.events.observe(viewLifecycleOwner){event ->
            when(event){
                SignInViewModel.Event.ConnectionError -> toast(R.string.connection_error)
                SignInViewModel.Event.Error -> toast(R.string.error)
                SignInViewModel.Event.InvalidCredentials -> toast(R.string.invalid_credentials)
            }
        }
    }

    fun initUi() = with(binding){
        binding.signIn.setOnClickListener {
            viewModel.signIn(binding.username.text.toString(), binding.password.text.toString())
        }
    }

    private fun toast(message:Int){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}