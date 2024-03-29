package com.oybekdev.lokmart.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.oybekdev.lokmart.R
import com.oybekdev.lokmart.R.dimen.dp_10
import com.oybekdev.lokmart.databinding.FragmentOnboardingBinding
import com.oybekdev.lokmart.utils.clearLightStatusBar
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment:Fragment() {
    private lateinit var binding: FragmentOnboardingBinding
    private val adapter = OnBoardingAdapter()
    private val viewModel by viewModels<OnboardingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() = with(binding) {
        clearLightStatusBar()
        pager.adapter = adapter

        indicatorView.apply {
            val normalColor = ContextCompat.getColor(requireContext(),R.color.indicator_unchecked)
            val checkedColor = ContextCompat.getColor(requireContext(),R.color.indicator_checked)
            setSliderColor(normalColor, checkedColor)
            setSliderWidth(resources.getDimension(dp_10))
            setSliderHeight(resources.getDimension(R.dimen.dp_8))
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            setPageSize(adapter.itemCount)
            notifyDataChanged()
        }

        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                indicatorView.onPageScrolled(position,positionOffset,positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicatorView.onPageSelected(position)
                next.text = if (position == adapter.itemCount-1){
                    getString(R.string.fragment_onboarding_get_string)
                }else{
                    getString(R.string.fragment_onboarding_next)
                }
            }
        })

        next.setOnClickListener {
            if (pager.currentItem == adapter.itemCount-1){
                viewModel.onboarded()
                findNavController().navigate(OnBoardingFragmentDirections.toSignInFragment())
            }else{
                pager.setCurrentItem(pager.currentItem+1, true)
            }
        }
    }
}
//47:07