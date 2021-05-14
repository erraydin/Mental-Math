package com.erraydin.mentalmath.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.erraydin.mentalmath.database.ScoreDatabaseDao

class GameViewModelFactory(private val difficulty: String, private val dataSource: ScoreDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(difficulty, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}