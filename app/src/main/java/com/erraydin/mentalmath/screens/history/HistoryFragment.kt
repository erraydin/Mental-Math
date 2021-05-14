package com.erraydin.mentalmath.screens.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.erraydin.mentalmath.R
import com.erraydin.mentalmath.database.ScoreDatabase
import com.erraydin.mentalmath.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = ScoreDatabase.getInstance(application).scoreDatabaseDao
        val viewModelFactory = HistoryViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(HistoryViewModel::class.java)

        binding.historyViewModel = viewModel
        binding.lifecycleOwner = this


        return binding.root
    }

}