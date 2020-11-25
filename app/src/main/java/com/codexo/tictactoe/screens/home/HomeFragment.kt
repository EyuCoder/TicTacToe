package com.codexo.tictactoe.screens.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.codexo.tictactoe.R
import com.codexo.tictactoe.databinding.FragmentHomeBinding
import com.codexo.tictactoe.screens.single.SinglePlayerViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        Log.i("HomeFragment", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.singlePlayerBtn.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_singlePlayerFragment)
        }
        binding.multiPlayerBtn.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_multiPlayerFragment)
        }


        return binding.root
    }
}