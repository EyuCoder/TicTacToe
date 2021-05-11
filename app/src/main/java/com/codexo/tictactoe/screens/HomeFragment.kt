package com.codexo.tictactoe.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.codexo.tictactoe.data.DataHandler
import com.codexo.tictactoe.R
import com.codexo.tictactoe.databinding.FragmentHomeBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment(){
    private lateinit var _binding: FragmentHomeBinding
    //private lateinit var viewModel: HomeViewModel
    private lateinit var userData: DataHandler

    companion object {
        val GOOGLE_PLAY_APP_LINK =
            "https://play.google.com/store/apps/details?id=com.codexo.tictactoe"
        val GOOGLE_PLAY_PROFILE = "https://play.google.com/store/apps/developer?id=eyuel+Daniel"
        val LINKEDIN_PROFILE = "https://www.linkedin.com/in/eyuel-daniel"
        val GITHUB_PROFILE = "https://github.com/elysium09/"
        val INSTAGRAM = "https://www.instagram.com/stories/eyu_x"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
//        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        userData = DataHandler(requireContext())

        listener()
        observeData()

        return _binding.root
    }

    private fun listener() {
        _binding.singlePlayerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_singlePlayerFragment)
        }
        _binding.multiPlayerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_multiPlayerFragment)
        }

        _binding.rateBtn.setOnClickListener { openLink(GOOGLE_PLAY_APP_LINK) }
        _binding.shareBtn.setOnClickListener { share(GOOGLE_PLAY_APP_LINK) }
        _binding.soundBtn.setOnClickListener { setSoundStatus(!_binding.soundBtn.isActivated) }
        _binding.infoBtn.setOnClickListener {}
    }

    private fun observeData() {
        //Updates soundbtn
        userData.gameSoundFlow.asLiveData().observe(requireActivity(), {
            _binding.soundBtn.isActivated = it
        })

        //Updates total
        userData.gameTotalFlow.asLiveData().observe(requireActivity(), {
            _binding.totalGameTv.text = "Games Played: $it"
        })
    }

    fun setSoundStatus(switch: Boolean) {
        GlobalScope.launch {
            userData.setSound(switch)
        }
    }

    private fun openLink(link: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(link)
            )
        )
    }

    private fun share(shareString: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareString)
        startActivity(shareIntent)
    }
}