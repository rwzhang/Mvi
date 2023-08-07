package com.example.mvi.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mvi.R
import com.example.mvi.data.viewmodel.SystemViewModel

class SystemFragment : Fragment() {

    companion object {
        fun newInstance() = SystemFragment()
    }

    private lateinit var viewModel: SystemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_system, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SystemViewModel::class.java)
        // TODO: Use the ViewModel
    }

}