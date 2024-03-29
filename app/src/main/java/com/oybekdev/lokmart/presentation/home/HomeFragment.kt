package com.oybekdev.lokmart.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.oybekdev.lokmart.R
import com.oybekdev.lokmart.data.api.product.dto.Banner
import com.oybekdev.lokmart.data.api.product.dto.Category
import com.oybekdev.lokmart.data.api.product.dto.Product
import com.oybekdev.lokmart.data.api.product.dto.Section
import com.oybekdev.lokmart.databinding.FragmentHomeBinding
import com.oybekdev.lokmart.presentation.home.adapter.BannerAdapter
import com.oybekdev.lokmart.presentation.home.adapter.HomeCategoryAdapter
import com.oybekdev.lokmart.presentation.home.adapter.SectionAdapter
import com.oybekdev.lokmart.utils.HorizontalMarginItemDecoration
import com.oybekdev.lokmart.utils.setLightStatusBar
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.abs

@AndroidEntryPoint
class HomeFragment:Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel> ()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToLiveData()
        initUi()
    }

    private fun initUi() = with(binding) {
        setLightStatusBar()
        error.retry.setOnClickListener {
            viewModel.getHome()
        }
        indicator.apply {
            val normalColor = ContextCompat.getColor(requireContext(),R.color.indicator_unchecked)
            val checkedColor = ContextCompat.getColor(requireContext(),R.color.indicator_checked)
            setSliderColor(normalColor, checkedColor)
            setSliderWidth(resources.getDimension(R.dimen.dp_20))
            setSliderHeight(resources.getDimension(R.dimen.dp_4))
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            notifyDataChanged()
        }
        banners.offscreenPageLimit = 1

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * kotlin.math.abs(position))

        }
        banners.setPageTransformer(pageTransformer)

        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        banners.addItemDecoration(itemDecoration)

        showAll.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.toCategoriesFragment())
        }

        searchContainer.search.setOnFocusChangeListener { view, focused ->
            if (focused.not()) return@setOnFocusChangeListener
            findNavController().navigate(HomeFragmentDirections.toSearchFragment())
        }
    }

    private fun subscribeToLiveData() = with(binding){
        viewModel.loading.observe(viewLifecycleOwner){
            loading.root.isVisible = it
        }
        viewModel.error.observe(viewLifecycleOwner){
            error.root.isVisible = it
        }
        viewModel.home.observe(viewLifecycleOwner){
            home.isVisible = it != null
            it ?: return@observe

            val name = it.user.firstName?: it.user.username
            greeting.text = getString(R.string.fragment_home_greeting, name)
            Glide.with(root).load(it.user.avatar).into(avatar)

            banners.adapter = BannerAdapter(it.banners,this@HomeFragment::onBannerClick)
            indicator.setupWithViewPager(banners)
            indicator.apply {
                setPageSize(it.banners.size)
                notifyDataChanged()
            }

            categories.adapter = HomeCategoryAdapter(it.categories,this@HomeFragment::onCategoryClick )

            sections.adapter = SectionAdapter(
                it.sections,
                this@HomeFragment::showAll,
                this@HomeFragment::onClickProduct,
                this@HomeFragment::like
            )

            count.text = it.notificationCount.toString()

        }

    }
    private fun onBannerClick(banner: Banner){

    }
    private fun onCategoryClick(category: Category){
        findNavController().navigate(HomeFragmentDirections.toProductFragment(category))
    }
    private fun showAll(section:Section){

    }
    private fun onClickProduct(product: Product){

    }
    private fun like(product:Product){

    }
}