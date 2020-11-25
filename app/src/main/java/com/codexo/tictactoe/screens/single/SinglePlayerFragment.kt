package com.codexo.tictactoe.screens.single

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.codexo.tictactoe.R
import com.codexo.tictactoe.databinding.FragmentSinglePlayerBinding
import java.util.*

class SinglePlayerFragment : Fragment() {
    private lateinit var binding: FragmentSinglePlayerBinding
    private lateinit var viewModel: SinglePlayerViewModel
    private val xIcon = R.drawable.x
    private val oIcon = R.drawable.o

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_single_player, container, false)

        Log.i("SinglePlayerFragment", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(SinglePlayerViewModel::class.java)

        listener()

        return binding.root
    }

    private fun listener() {
        viewModel.initGame()
        binding.tv1.setOnClickListener { playGame(0, binding.tv1) }
        binding.tv2.setOnClickListener { playGame(1, binding.tv2) }
        binding.tv3.setOnClickListener { playGame(2, binding.tv3) }
        binding.tv4.setOnClickListener { playGame(3, binding.tv4) }
        binding.tv5.setOnClickListener { playGame(4, binding.tv5) }
        binding.tv6.setOnClickListener { playGame(5, binding.tv6) }
        binding.tv7.setOnClickListener { playGame(6, binding.tv7) }
        binding.tv8.setOnClickListener { playGame(7, binding.tv8) }
        binding.tv9.setOnClickListener { playGame(8, binding.tv9) }
        binding.resetBtn.setOnClickListener { resetCells()
        }
    }

    private fun playGame(cellID: Int, selectedIv: ImageView) {

        for (element in viewModel.xoTable) {
            println("here is the 2d array: ${element}")
        }
        try {
            if (viewModel.turn == 'x') {
                paint(selectedIv, xIcon, false)
                viewModel.xoTable[cellID] = viewModel.turn
                viewModel.turn = 'o'
                Handler(Looper.getMainLooper()).postDelayed({
                    AutoPlay()
                }, 400)

            } else {
                paint(selectedIv, oIcon, false)
                viewModel.xoTable[cellID] = viewModel.turn
                viewModel.turn = 'x'
            }
            for (element in viewModel.xoTable) {
                println("here is the 2d array: ${element}")
            }
            doChecks()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        for (element in viewModel.xoTable) {
            print(">>>>>>>>>>>" + element)
        }

    }

    private fun paint(iv: ImageView, id: Int, enabled: Boolean) {
        iv.setImageResource(id)
        iv.isEnabled = enabled
    }

    private fun resetCells() {
        binding.resetBtn.visibility = View.INVISIBLE
        binding.tv1.setImageDrawable(null)
        binding.tv2.setImageDrawable(null)
        binding.tv3.setImageDrawable(null)
        binding.tv4.setImageDrawable(null)
        binding.tv5.setImageDrawable(null)
        binding.tv6.setImageDrawable(null)
        binding.tv7.setImageDrawable(null)
        binding.tv8.setImageDrawable(null)
        binding.tv9.setImageDrawable(null)

        binding.tv1.visibility = View.VISIBLE
        binding.tv2.visibility = View.VISIBLE
        binding.tv3.visibility = View.VISIBLE
        binding.tv4.visibility = View.VISIBLE
        binding.tv5.visibility = View.VISIBLE
        binding.tv6.visibility = View.VISIBLE
        binding.tv7.visibility = View.VISIBLE
        binding.tv8.visibility = View.VISIBLE
        binding.tv9.visibility = View.VISIBLE
    }

    private fun AutoPlay() {
        if (viewModel.xoTable.contains('-')) {
            viewModel.getMoves()
            val r = Random()
            Log.i("size", viewModel.emptyCells.size.toString())
            val randIndex = r.nextInt(viewModel.emptyCells.size + 1)
            val cellID = viewModel.emptyCells[randIndex]

            val selectedIv: ImageView
            when (cellID) {
                0 -> selectedIv = binding.tv1
                1 -> selectedIv = binding.tv2
                2 -> selectedIv = binding.tv3
                3 -> selectedIv = binding.tv4
                4 -> selectedIv = binding.tv5
                5 -> selectedIv = binding.tv6
                6 -> selectedIv = binding.tv7
                7 -> selectedIv = binding.tv8
                8 -> selectedIv = binding.tv9
                else -> selectedIv = binding.tv1
            }

            playGame(cellID, selectedIv)
            viewModel.emptyCells.clear()
        }
    }

    fun doChecks() {
        val flag = viewModel.checkWinner()

        if (flag != '-'){
            when (flag) {
                'o' -> {
                    Toast.makeText(requireContext(), "O Wins! ", Toast.LENGTH_SHORT).show()
                    viewModel.clearGame()
                    binding.resetBtn.visibility = View.VISIBLE
                }
                'x' -> {
                    Toast.makeText(requireContext(), "x Wins! ", Toast.LENGTH_SHORT).show()
                    viewModel.clearGame()
                    binding.resetBtn.visibility = View.VISIBLE
                }
                '-' -> {
                    Toast.makeText(requireContext(), "Draw! ", Toast.LENGTH_SHORT).show()
                    viewModel.clearGame()
                    binding.resetBtn.visibility = View.VISIBLE
                }
                else -> {
                }
            }
        }


    }
}