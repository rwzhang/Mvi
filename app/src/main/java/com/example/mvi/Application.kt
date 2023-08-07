package com.example.mvi

import android.app.Activity
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import com.example.mvi.ui.MainActivity
import com.gyf.immersionbar.ImmersionBar

class Application :MultiDexApplication(){
    var mainActivity: MainActivity? = null
    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                if ("${activity.packageName}.${activity.localClassName}" == MainActivity::class.java.name) {
                    mainActivity = activity as MainActivity
                }
            }
            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }

        })
    }

}