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
import java.lang.NullPointerException
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
        initCells(viewModel.xoTable)

        val childCount: Int = binding.gridLayout.getChildCount()

        for (i in 0 until childCount) {
            binding.gridLayout.getChildAt(i).setOnClickListener {
                playGame(i, binding.gridLayout.getChildAt(i) as ImageView)
            }
        }
        binding.resetBtn.setOnClickListener {
            resetCells()
        }
    }

    private fun playGame(cellID: Int, selectedIv: ImageView) {

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
        } catch (e: NullPointerException) { e.printStackTrace().toString() }
        doChecks()
    }

    private fun paint(iv: ImageView, id: Int, enabled: Boolean) {
        iv.setImageResource(id)
        iv.isEnabled = enabled
    }

    private fun initCells(xoTable: Array<Char>){

        for (cellID in 0..8) {
            if (xoTable[cellID]=='-') {
                binding.gridLayout.getChildAt(cellID).setBackgroundResource(R.drawable.cells_border)
                paint(binding.gridLayout.getChildAt(cellID) as ImageView, 0, true)
                binding.gridLayout.getChildAt(cellID).isEnabled = true
            }
        }
    }

    private fun resetCells() {
        viewModel.clearGame()
        binding.resetBtn.visibility = View.INVISIBLE
        listener()
    }

    private fun AutoPlay() {
        doChecks()
        if (viewModel.xoTable.contains('-')) {
            viewModel.setLeftMoves()
            val r = Random()
            Log.i("size", viewModel.emptyCells.size.toString())
            val randIndex = r.nextInt(viewModel.emptyCells.size)
            Log.i("arraySize: ", viewModel.emptyCells.size.toString()+ " "+ randIndex)
            val cellID = viewModel.emptyCells[randIndex]

            playGame(cellID, binding.gridLayout.getChildAt(cellID) as ImageView)
        } else return
    }

    fun doChecks() {
        val flag = viewModel.checkWinner()

        if (flag != '-') {
            when (flag) {
                'o' -> {
                    Toast.makeText(requireContext(), "O Wins! ", Toast.LENGTH_SHORT).show()
                    gameOver()
                }
                'x' -> {
                    Toast.makeText(requireContext(), "x Wins! ", Toast.LENGTH_SHORT).show()
                    gameOver()
                }
                '-' -> {
                    Log.i("draw: ", viewModel.xoTable.contains('-').toString())
                    Toast.makeText(requireContext(), "Draw! ", Toast.LENGTH_SHORT).show()
                    gameOver()
                }
                else -> {
                }
            }
        }


    }

    private fun gameOver() {
        binding.resetBtn.visibility = View.VISIBLE
        viewModel.emptyCells.clear()
        viewModel.turn = '-'
        return
    }
}