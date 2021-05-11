package com.erraydin.mentalmath.screens.choose_difficulty

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.erraydin.mentalmath.R
import com.erraydin.mentalmath.databinding.FragmentChooseDifficultyBinding
import com.google.android.material.button.MaterialButton
import java.lang.IllegalArgumentException
import kotlin.properties.Delegates


class ChooseDifficultyFragment : Fragment() {
    private var darkColor by Delegates.notNull<Int>()
    private var selectedColor: Int = 0
    private var difficulty: String = "Medium"
    private lateinit var binding: FragmentChooseDifficultyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_choose_difficulty, container, false)

//        binding.buttonScoreHistory.setOnClickListener {
//            findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToHistoryFragment())
//        }

        darkColor = context?.let { it1 -> ContextCompat.getColor(it1, R.color.background_lighter) }!!
        selectedColor = context?.let { it1 -> ContextCompat.getColor(it1, R.color.selected_color) }!!

        binding.buttonStartGame.setOnClickListener {
            findNavController().navigate(ChooseDifficultyFragmentDirections.actionChooseDifficultyFragmentToGameFragment())
        }

        binding.buttonEasy.setOnClickListener {
            setDifficulty("Easy")
        }
        binding.buttonMedium.setOnClickListener {
            setDifficulty("Medium")
        }
        binding.buttonHard.setOnClickListener {
            setDifficulty("Hard")
        }
        binding.buttonExpert.setOnClickListener {
            setDifficulty("Expert")
        }

        return binding.root
    }

    private fun setDifficulty(newDifficulty: String) {
        if (difficulty != newDifficulty) {
            var button = getButton()
            button.setBackgroundColor(darkColor)

            difficulty = newDifficulty
            button = getButton()
            button.setBackgroundColor(selectedColor)

            binding.textViewDifficultyTitle.text = "$newDifficulty Difficulty"
            binding.textViewOperations.text = getInfoText()
        }
    }


    private fun getButton (): MaterialButton {
        return when (difficulty) {
            "Easy" -> binding.buttonEasy
            "Medium" -> binding.buttonMedium
            "Hard" -> binding.buttonHard
            "Expert" -> binding.buttonExpert
            else -> throw IllegalArgumentException("There is a problem about difficulty!")
        }
    }

    private fun getInfoText (): String {
        return when (difficulty) {
            "Easy" -> "• Addition \n• Subtraction \n• Multiplication"
            "Medium" -> "• Addition \n• Subtraction \n• Multiplication \n• Division"
            "Hard" -> "• Addition \n• Subtraction \n• Multiplication \n• Division \n• Decimals"
            "Expert" -> "• Addition \n• Subtraction \n• Multiplication \n• Division \n• Decimals \n• Fractions"
            else -> throw IllegalArgumentException("There is a problem about difficulty!")
        }
    }

}