package com.erraydin.mentalmath.screens.choose_difficulty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.erraydin.mentalmath.R
import com.erraydin.mentalmath.databinding.FragmentChooseDifficultyBinding


class ChooseDifficultyFragment : Fragment() {
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

        binding.buttonStartGame.setOnClickListener {
            findNavController().navigate(ChooseDifficultyFragmentDirections.actionChooseDifficultyFragmentToGameFragment())
        }
        return binding.root
    }

}