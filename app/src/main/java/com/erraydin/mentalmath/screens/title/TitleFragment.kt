package com.erraydin.mentalmath.screens.title

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.erraydin.mentalmath.R
import com.erraydin.mentalmath.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {
    private lateinit var binding: FragmentTitleBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)

        binding.buttonPlay.setOnClickListener {
            findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToChooseDifficultyFragment())
        }

        binding.buttonHistory.setOnClickListener {
            findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToHistoryFragment())
        }

        binding.buttonInfo.setOnClickListener {
            findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToInfoFragment())
        }
        val a = System.currentTimeMillis()
        val b = DateUtils.formatDateTime(context, a,
            DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_DATE or
                    DateUtils.FORMAT_SHOW_YEAR
        )


        Log.i("Title Fragment", "the date is $b")
        return binding.root
    }




}