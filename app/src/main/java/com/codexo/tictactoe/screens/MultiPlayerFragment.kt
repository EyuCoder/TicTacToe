package com.codexo.tictactoe.screens

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.codexo.tictactoe.R
import com.codexo.tictactoe.data.DataHandler
import com.codexo.tictactoe.databinding.FragmentMultiPlayerBinding
import com.codexo.tictactoe.databinding.FragmentSinglePlayerBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MultiPlayerFragment : Fragment() {
    private lateinit var binding: FragmentMultiPlayerBinding
    private lateinit var viewModel: SharedViewModel
    private lateinit var userData: DataHandler
    private val xIcon = R.drawable.x_android
    private val oIcon = R.drawable.o_apple
    lateinit var xMedia: MediaPlayer
    lateinit var oMedia: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_multi_player, container, false)

        Log.i("MultiPlayerFragment", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        xMedia = MediaPlayer.create(requireContext(), R.raw.x)
        oMedia = MediaPlayer.create(requireContext(), R.raw.o)

        userData = DataHandler(requireContext())

        listener()

        return binding.root
    }

    private fun listener() {
        viewModel.initGame()
        initScreen()
        getSoundStatus()

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
        var sound = getSoundStatus()

        try {
            if (viewModel.turn == 'x') {
                paint(selectedIv, xIcon, false)
                if (sound) xMedia.start()
                viewModel.xoTable[cellID] = viewModel.turn
                viewModel.turn = 'o'
                viewModel.freeSpots--
                doChecks()

            } else if (viewModel.turn == 'o') {

                paint(selectedIv, oIcon, false)
                if (sound) oMedia.start()
                viewModel.xoTable[cellID] = viewModel.turn
                viewModel.turn = 'x'
                viewModel.freeSpots--
                doChecks()
            }
        } catch (e: NullPointerException) {
            e.printStackTrace().toString()
        }
        Log.v("xscore: ", viewModel.xScore.toString())
    }

    private fun paint(iv: ImageView, id: Int, enabled: Boolean) {
        iv.setImageResource(id)
        iv.isEnabled = enabled
    }

    private fun initScreen() {
        binding.scoreTv.text = ""
        binding.xWinsTv.text = viewModel.xScore.toString()
        binding.oWinsTv.text = viewModel.oScore.toString()
        getGamesPlayed()

        for (cellID in 0..8) {
            if (viewModel.xoTable[cellID] == 'o') {
                binding.gridLayout.getChildAt(cellID)
                    .setBackgroundResource(R.drawable.custom_cells_bg)
                paint(binding.gridLayout.getChildAt(cellID) as ImageView, oIcon, true)
                binding.gridLayout.getChildAt(cellID).isEnabled = true
            } else if (viewModel.xoTable[cellID] == 'x') {
                binding.gridLayout.getChildAt(cellID)
                    .setBackgroundResource(R.drawable.custom_cells_bg)
                paint(binding.gridLayout.getChildAt(cellID) as ImageView, xIcon, true)
                binding.gridLayout.getChildAt(cellID).isEnabled = true
            } else {
                binding.gridLayout.getChildAt(cellID)
                    .setBackgroundResource(R.drawable.custom_cells_bg)
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

    fun doChecks() {
        val flag = viewModel.checkWinner()
        if (flag == 'x') viewModel.turn = '-'

        when (flag) {
            'o' -> {
                Toast.makeText(requireContext(), "O Wins! ", Toast.LENGTH_SHORT).show()
                binding.scoreTv.text = "Apple Wins!"
                viewModel.oScore = viewModel.oScore + 1
                binding.oWinsTv.text = viewModel.oScore.toString()
                gameOver()
            }
            'x' -> {
                viewModel.turn = '-'
                Log.v("Hello: ", "sup")
                Toast.makeText(requireContext(), "x Wins! ", Toast.LENGTH_SHORT).show()
                binding.scoreTv.text = "Android Wins!"
                viewModel.xScore = viewModel.xScore + 1
                binding.xWinsTv.text = viewModel.xScore.toString()
                gameOver()
            }
            '-' -> {
                Toast.makeText(requireContext(), "Draw! ", Toast.LENGTH_SHORT).show()
                binding.scoreTv.text = "Draw!"
                Log.i("draw: ", viewModel.xoTable.contains('-').toString())
                gameOver()
            }
            else -> {
            }
        }

    }

    private fun getSoundStatus(): Boolean {
        var sound = false
        userData.gameSoundFlow.asLiveData().observe(requireActivity(), {
            sound = it
        })
        Log.v("sound: ", sound.toString())
        return sound
    }

    private fun getGamesPlayed(): Int {
        var total = 0
        lifecycleScope.launch {
            userData.gameTotalFlow.asLiveData().observe(requireActivity(), {
                total = it
                binding.gamesPlayedTv.text = it.toString()
            })
        }
        Log.v("Total: ", total.toString())
        return total
    }

    private fun setGamesPlayed() {
        var count = getGamesPlayed()
        GlobalScope.launch {
            userData.setGamesPlayed(++count)
        }

    }

    private fun gameOver() {
        binding.resetBtn.visibility = View.VISIBLE
        viewModel.emptyCells.clear()
        viewModel.turn = '-'
        setGamesPlayed()
        return
    }

    override fun onDetach() {
        super.onDetach()
    }
}