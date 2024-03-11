package com.oybekdev.lokmart.presentation.sign_up

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.oybekdev.lokmart.R
import com.oybekdev.lokmart.databinding.FragmentSignUpBinding
import com.oybekdev.lokmart.utils.clearLightStatusBar
import com.oybekdev.lokmart.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment:Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun subscribeToLiveData() = with(binding){
        viewModel.loading.observe(viewLifecycleOwner){isLoading ->
            progress.isVisible = isLoading
            register.text = if (isLoading) null else getString(R.string.fragment_sign_up_register)
        }
        viewModel.events.observe(viewLifecycleOwner){event ->
            when(event){
                SignUpViewModel.Event.ConnectionError -> toast(R.string.connection_error)
                SignUpViewModel.Event.Error -> toast(R.string.error)
                SignUpViewModel.Event.AlreadyRegistered -> toast(R.string.already_registered)
                SignUpViewModel.Event.NavigateHome -> toast(R.string.app_name)
            }
        }
    }



    private fun initUi() = with(binding){
        clearLightStatusBar()
        register.setOnClickListener {
            viewModel.signUp(
                username.text.toString(),
                email.text.toString(),
                password.text.toString()
            )
        }
        signIn.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.toSignInFragment())
        }

        val termsOfUse = getString(R.string.terms_of_use)
        val pravicyPolicy = getString(R.string.privacy_policy)
        val string = getString(R.string.fragment_sing_up_terms_and_privacy,termsOfUse,pravicyPolicy)
        val spannable = SpannableString(string)
        val termsOfUseSpan = object :ClickableSpan(){
            override fun onClick(view: View) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
                startActivity(browserIntent)
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = ContextCompat.getColor(requireContext(), R.color.orange)
                val semiBold = ResourcesCompat.getFont(requireContext(), R.font.semi_bold)
                ds.typeface = semiBold
            }

        }
        val privacyPolicySpan = object :ClickableSpan(){
            override fun onClick(view: View) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mohirdev.com"))
                startActivity(browserIntent)
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = ContextCompat.getColor(requireContext(), R.color.orange)
                val semiBold = ResourcesCompat.getFont(requireContext(), R.font.semi_bold)
                ds.typeface = semiBold
            }
        }
        spannable.setSpan(
            termsOfUseSpan,
            string.indexOf(termsOfUse),
            string.indexOf(termsOfUse) + termsOfUse.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            privacyPolicySpan,
            string.indexOf(pravicyPolicy),
            string.indexOf(pravicyPolicy) + pravicyPolicy.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        terms.text = spannable
        terms.movementMethod = LinkMovementMethod.getInstance()
    }
}