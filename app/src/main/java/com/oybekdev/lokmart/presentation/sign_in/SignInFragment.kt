package com.oybekdev.lokmart.presentation.sign_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.oybekdev.lokmart.R
import com.oybekdev.lokmart.databinding.FragmentSignInBinding
import com.oybekdev.lokmart.utils.clearLightStatusBar
import com.oybekdev.lokmart.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.sign

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
            progress.isVisible = isLoading
            signIn.text = if (isLoading) null else getString(R.string.fragment_sign_in_button)
        }
        viewModel.events.observe(viewLifecycleOwner){event ->
            when(event){
                SignInViewModel.Event.ConnectionError -> toast(R.string.connection_error)
                SignInViewModel.Event.Error -> toast(R.string.error)
                SignInViewModel.Event.InvalidCredentials -> toast(R.string.invalid_credentials)
                SignInViewModel.Event.NavigateToHome -> toast(R.string.app_name)
            }
        }
    }

    fun initUi() = with(binding){
        clearLightStatusBar()
        binding.signIn.setOnClickListener {
            viewModel.signIn(binding.username.text.toString(), binding.password.text.toString())
        }

        signUp.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.toSignUpFragment())
        }
    }


}