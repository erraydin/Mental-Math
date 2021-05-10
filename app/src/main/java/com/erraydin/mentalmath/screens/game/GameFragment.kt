package com.erraydin.mentalmath.screens.game

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.erraydin.mentalmath.R
import com.erraydin.mentalmath.databinding.FragmentGameBinding


class GameFragment : Fragment() {
    private lateinit var viewModel: GameViewModel
    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        Log.i("GameFragment", "Called ViewModelProvider")
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        viewModel.remainingTime.observe(viewLifecycleOwner, Observer { newTime ->
            binding.textViewRemainingTime.text = newTime.toString()
        })

        viewModel.gameFinished.observe(viewLifecycleOwner, Observer { gameFinished ->
            if (gameFinished) {
                viewModel.onGameFinishEnd()
                viewModel.pauseTimer()
                findNavController().navigate(GameFragmentDirections.actionGameFragmentToScoreFragment())
            }
        })

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseTimer()
    }

    override fun onResume() {
        super.onResume()
        viewModel.resumeTimer()
    }
}