package com.erraydin.mentalmath.screens.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.erraydin.mentalmath.R
import com.erraydin.mentalmath.databinding.FragmentScoreBinding


class ScoreFragment : Fragment() {
    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory
    private lateinit var binding: FragmentScoreBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score, container, false)
        val args = arguments?.let { ScoreFragmentArgs.fromBundle(it) }

        viewModelFactory = ScoreViewModelFactory(args?.score.toString())
        viewModel = ViewModelProvider(this, viewModelFactory).get(ScoreViewModel::class.java)
        binding.textViewScore.text = viewModel.score

        return binding.root
    }


}