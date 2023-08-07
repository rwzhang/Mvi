package com.example.mvi.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvi.databinding.ActivitySplashBinding
import java.util.Timer
import java.util.TimerTask

class SplashActivity : AppCompatActivity() {
    private lateinit var task: SplashTimerTask
    private var timer: Timer? = null

    private lateinit var binding: ActivitySplashBinding
    var timeCount: Int = 6
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initViewModel()
    }

    private fun initView() {
        timer = Timer()
        if (!this::task.isInitialized) {
            task = SplashTimerTask(
                timer, timeCount, this@SplashActivity,
                binding
            )
            timer?.schedule(task, 0L, 1000L)
        } else {
            task.cancel()
            timer?.purge()
            timer?.cancel()
        }
    }

    private fun initViewModel() {

    }

   data class SplashTimerTask(
        var timer: Timer?,
        var timerCount: Int,
        var activity: Activity,
        var binding: ActivitySplashBinding
    ) : TimerTask() {
        override fun run() {

            when (timerCount) {
                0 -> {
                    binding.textViewTitle.text = timerCount.toString()
                    activity.startActivity(Intent(activity, MainActivity::class.java))
                    activity.finish()
                }
                1, 2, 3, 4, 5,6 -> {
                    binding.textViewTitle.text = timerCount.toString()
                }
                else -> {
                    cancel()
                    timer?.purge()
                    timer?.cancel()
                }
            }
            timerCount--
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::task.isInitialized) {
            task.cancel()
            timer?.purge()
            timer?.cancel()
        }
    }

}