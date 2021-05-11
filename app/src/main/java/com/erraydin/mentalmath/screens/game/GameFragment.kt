package com.erraydin.mentalmath.screens.game

import android.os.Bundle
import android.os.CountDownTimer
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
    private lateinit var viewModelFactory: GameViewModelFactory
    private lateinit var viewModel: GameViewModel
    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)


        val args = arguments?.let { GameFragmentArgs.fromBundle(it) }
        viewModelFactory = GameViewModelFactory(args?.difficulty ?: "Medium")
        viewModel = ViewModelProvider(this, viewModelFactory).get(GameViewModel::class.java)

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this

        //Make edittext automatically focused and disable keyboard
        editTextConfig()


        /* ######################################################################
        * ####################  OBSERVERS  #####################################
        * ########################################################################*/

        viewModel.userAnswer.observe(viewLifecycleOwner, Observer { newUserAnswer ->
            binding.editTextResult.setText(newUserAnswer)
            binding.editTextResult.setSelection(binding.editTextResult.text.length)
            if (newUserAnswer == viewModel.getResult()) {
                viewModel.incrementScore()
                val timer = object : CountDownTimer(100, GameViewModel.ONE_SECOND) {
                    override fun onFinish() {
                        viewModel.nextQuestion()
                    }
                    override fun onTick(millisUntilFinished: Long) {
                    }
                }
                timer.start()
            }
        })

        viewModel.gameFinished.observe(viewLifecycleOwner, Observer { gameFinished ->
            if (gameFinished) {
                viewModel.pauseTimer()
                val timer = object : CountDownTimer(1000, GameViewModel.ONE_SECOND) {
                    override fun onFinish() {
                        viewModel.onGameFinishEnd()
                        findNavController().navigate(GameFragmentDirections.actionGameFragmentToScoreFragment(viewModel.score.value ?: 0))
                    }
                    override fun onTick(millisUntilFinished: Long) {
                    }
                }
                timer.start()
            }
        })

        return binding.root
    }


    private fun editTextConfig () {
        binding.editTextResult.requestFocus()
        binding.editTextResult.showSoftInputOnFocus = false
        binding.editTextResult.isLongClickable = false
        binding.editTextResult.setTextIsSelectable(false)
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