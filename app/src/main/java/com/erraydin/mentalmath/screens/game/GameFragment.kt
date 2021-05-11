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

        setButtonOnClickListeners()

        viewModel.remainingTime.observe(viewLifecycleOwner, Observer { newTime ->
            binding.textViewRemainingTime.text = newTime.toString()
        })

        viewModel.question.observe(viewLifecycleOwner, Observer { newQuestion ->
            binding.textViewQuestion.text = newQuestion
        })

        viewModel.userAnswer.observe(viewLifecycleOwner, Observer { newUserAnswer ->
            binding.editTextResult.setText(newUserAnswer)
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

    private fun setButtonOnClickListeners() {
        binding.button0.setOnClickListener {
            viewModel.addToAnswer("0")
        }
        binding.button1.setOnClickListener {
            viewModel.addToAnswer("1")
        }
        binding.button2.setOnClickListener {
            viewModel.addToAnswer("2")
        }
        binding.button3.setOnClickListener {
            viewModel.addToAnswer("3")
        }
        binding.button4.setOnClickListener {
            viewModel.addToAnswer("4")
        }
        binding.button5.setOnClickListener {
            viewModel.addToAnswer("5")
        }
        binding.button6.setOnClickListener {
            viewModel.addToAnswer("6")
        }
        binding.button7.setOnClickListener {
            viewModel.addToAnswer("7")
        }
        binding.button8.setOnClickListener {
            viewModel.addToAnswer("2")
        }
        binding.button9.setOnClickListener {
            viewModel.addToAnswer("9")
        }
        binding.buttonDivision.setOnClickListener {
            if (viewModel.userAnswer.value?.toIntOrNull() != null) {
                viewModel.addToAnswer("/")
            }
        }
        binding.buttonMinus.setOnClickListener {
            if (viewModel.userAnswer.value == "") {
                viewModel.addToAnswer("-")
            }
        }

        binding.buttonDot.setOnClickListener {
            if (viewModel.userAnswer.value?.toIntOrNull() != null) {
                viewModel.addToAnswer(".")
            }
        }

        binding.buttonBackspace.setOnClickListener {
            viewModel.backspace()
        }


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