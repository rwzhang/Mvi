package com.example.mvi.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mvi.R
import com.example.mvi.data.viewmodel.OfficialAccountViewModel

class OfficialAccountFragment : Fragment() {

    companion object {
        fun newInstance() = OfficialAccountFragment()
    }

    private lateinit var viewModel: OfficialAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_official_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OfficialAccountViewModel::class.java)
    }


}