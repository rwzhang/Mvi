package com.example.mvi.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.mvi.adapter.BannerAdapter
import com.example.mvi.data.intent.HomeIntent
import com.example.mvi.data.state.HomeState
import com.example.mvi.data.viewmodel.HomeViewModel
import com.example.mvi.databinding.FragmentHomeBinding
import com.example.mvi.ui.MainActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: BannerAdapter
    lateinit var  mainActivity:MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        initView()
        initViewModel()

    }

    private fun initView() {
        mainActivity= activity as MainActivity
        adapter = BannerAdapter()
        binding.viewPagerBanner.adapter = adapter
        binding.viewPagerBanner.apply {
            offscreenPageLimit = 1
        }
//        binding.viewPagerBanner.setOnTouchListener(object :View.OnTouchListener{
//            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
//                p1?.let {
//                    when (p1.action) {
//                        MotionEvent.ACTION_DOWN -> {
////                            binding.drawerLayoutLeft.closeDrawers()
//                        }
//                    }
//                }
//                return false
//            }
//
//        })
        binding.viewPagerBanner.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
//                if (position == 2) {
//                    mainActivity.onPageSelected(true)
//                } else {
//                    mainActivity.onPageSelected(false)
//                }
            }
        })
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { it ->
                when (it) {
                    is HomeState.Idle -> {}
                    is HomeState.Error -> {}
                    is HomeState.Loading -> {}
                    is HomeState.getBanner -> {
                        it.loginResponse.let { response ->
                            response.data?.let {
                                if (this@HomeFragment::adapter.isInitialized) {
                                    if (it.size > 0) {
                                        adapter.setData(it)
                                        adapter.notifyDataSetChanged()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.homeIntentChannel.send(HomeIntent.Banner)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("zrw", "onResume: 显示了homeFragment")
    }

    interface OnDrawerPagerListener {

        fun onPageSelected(isLast: Boolean)
    }

}