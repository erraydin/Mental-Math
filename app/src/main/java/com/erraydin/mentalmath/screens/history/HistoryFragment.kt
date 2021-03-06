package com.erraydin.mentalmath.screens.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erraydin.mentalmath.R
import com.erraydin.mentalmath.database.ScoreDatabase
import com.erraydin.mentalmath.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {
    private lateinit var viewModel: HistoryViewModel
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
        viewModel = ViewModelProvider(this, viewModelFactory).get(HistoryViewModel::class.java)

        binding.historyViewModel = viewModel


        val adapter = ScoreAdapter()
        binding.recyclerViewScores.adapter = adapter

        viewModel.scores.observe(viewLifecycleOwner, Observer { newScores ->
            newScores?.let {
                adapter.data = newScores
            }
        })

        binding.lifecycleOwner = this

        setupSpinners()

        return binding.root
    }


    private fun setupSpinners() {
        val difficulties = arrayOf("Easy", "Medium", "Hard", "Expert")
        val difficultyAdapter =
            activity?.let { ArrayAdapter(it, R.layout.spinner_item, difficulties) }
        binding.spinnerDifficulty.adapter = difficultyAdapter
        binding.spinnerDifficulty.setSelection(1)
        binding.spinnerDifficulty.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.setDifficulty(difficulties[position])
                    viewModel.makeQuery()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }


        val orderBy = arrayOf("Score", "Date")
        val orderAdapter = activity?.let { ArrayAdapter(it, R.layout.spinner_item, orderBy) }
        binding.spinnerOrderBy.adapter = orderAdapter
        binding.spinnerOrderBy.setSelection(0)
        binding.spinnerOrderBy.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.setOrderBy(orderBy[position])
                    viewModel.makeQuery()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

}