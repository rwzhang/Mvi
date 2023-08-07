package com.example.mvi.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mvi.R
import com.example.mvi.data.viewmodel.SquareViewModel
import com.example.mvi.databinding.FragmentHomeBinding
import com.example.mvi.databinding.FragmentSquareBinding

class SquareFragment : Fragment() {

    companion object {
        fun newInstance() = SquareFragment()
    }

    private lateinit var viewModel: SquareViewModel
    private lateinit var binding: FragmentSquareBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSquareBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[SquareViewModel::class.java]
        binding.textViewTitle.setOnClickListener {
            findNavController().navigate(R.id.test_fragment)
        }
    }

}