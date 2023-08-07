package com.example.mvi.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.mvi.R
import com.example.mvi.data.state.MainState
import com.example.mvi.data.viewmodel.MainViewModel
import com.example.mvi.data.viewmodel.ViewModelFactory
import com.example.mvi.databinding.ActivityMainBinding
import com.example.mvi.intent.MainIntent
import com.example.mvi.navigator.FragmentNavigator
import com.example.mvi.navigator.NavigationUI
import com.example.mvi.ui.fragment.HomeFragment
import com.suilvapp.app.network.RetrofitClient
import kotlinx.coroutines.launch

/**
 * 问题
 * navigation 跳转A-B-C A的布局闪烁
 */

class MainActivity : AppCompatActivity(), HomeFragment.OnDrawerPagerListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private val fragmentManager: FragmentManager by lazy {
        supportFragmentManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(RetrofitClient.apiService)
        )[MainViewModel::class.java]
        initViewModel()
        initView()
    }

    private fun initView() {
        val navHostFragment =
            fragmentManager.findFragmentById(R.id.my_nav_host_fragment) as com.example.mvi.navigator.NavHostFragment
        navController = navHostFragment.navController
        val fragmentNavigator = FragmentNavigator(this, navHostFragment.childFragmentManager, navHostFragment.id)
//        //3.注册到Navigator里面，这样才找得到
        navController.navigatorProvider.addNavigator(fragmentNavigator)
//        //4.设置Graph，需要将activity_main.xml文件中的app:navGraph="@navigation/mobile_navigation"移除
        navController.setGraph(R.navigation.nav_android)
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
        }
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.title.toString()) {
                "首页" -> {

                }

                "广场" -> {
                }

                "公众号" -> {
                }

                "体系" -> {
                }

                "项目" -> {
                }
            }

//            item.onNavDestinationSelected(navController)
            NavigationUI.onNavDestinationSelected(item, navHostFragment.navController)
            false//如果返回false 则底部导航栏选中按钮系统动画失效，但会保留第一次选中的按钮效果
        }
        binding.drawerLayoutLeft.addDrawerListener(object : DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
//                binding.drawerLayoutLeft.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
            }

            override fun onDrawerClosed(drawerView: View) {
//                binding.drawerLayoutLeft.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                if (drawerView != null) {

                }
            }

            override fun onDrawerStateChanged(newState: Int) {}
        })
//        binding.drawerLayoutLeft.setOnTouchListener(object : View.OnTouchListener {
//            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
//                p1?.let {
//                    when (p1.action) {
//                        MotionEvent.ACTION_DOWN -> {
////                            binding.drawerLayoutLeft.closeDrawers()
//                        }
//
//                        else -> {
//
//                        }
//                    }
//                }
//              return false
//            }
//        })


        binding.drawerLayoutLeft.run {
            val toggle = ActionBarDrawerToggle(
                this@MainActivity,
                this,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            addDrawerListener(toggle)
            toggle.syncState()
        }
    }


    private fun initViewModel() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {}
                    is MainState.Error -> {}
                    is MainState.Loading -> {}
                    is MainState.Register -> {
                        Log.d("zrw", "initViewModel: " + it.loginResponse.toString())
                    }

                    else -> {}
                }
            }
        }
        lifecycleScope.launch {
            mainViewModel.mainIntentChannel.send(MainIntent.Register)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    override fun onPageSelected(isLast: Boolean) {
        if (this::binding.isInitialized) {
            if (isLast) {
                binding.drawerLayoutLeft.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else if (binding.drawerLayoutLeft.getDrawerLockMode(GravityCompat.START) == DrawerLayout.LOCK_MODE_UNLOCKED) {
                binding.drawerLayoutLeft.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
            }
        }

    }
}