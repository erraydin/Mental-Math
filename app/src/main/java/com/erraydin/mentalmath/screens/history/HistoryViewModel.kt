package com.erraydin.mentalmath.screens.history

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.*
import com.erraydin.mentalmath.database.Score
import com.erraydin.mentalmath.database.ScoreDatabaseDao
import kotlinx.coroutines.*
import java.lang.IllegalArgumentException
import java.lang.StringBuilder

class HistoryViewModel(val database: ScoreDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    companion object {
        const val EASY = "Easy"
        const val MEDIUM = "Medium"
        const val HARD = "Hard"
        const val EXPERT = "Expert"
        const val SCORE = "Score"
        const val DATE = "Date"
    }

    /* ###############################################################
    * ################ BoilerPlate Code For Coroutines ###############
    * ###############################################################*/

    //This is for cancelling coroutines when the viewModel is destroyed
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /*---------------------------------------><-------------------------------------*/


    private val _difficulty = MutableLiveData<String>()
//    val difficulty: LiveData<String>
//        get() = _difficulty

    private val _orderBy = MutableLiveData<String>()
//    val orderBy: LiveData<String>
//        get() = _orderBy


    private val scores = MutableLiveData<List<Score>>()
    val scoresString = Transformations.map(scores) { scores ->
        formatScores(scores)
    }

    init {
        setDifficulty(MEDIUM)
        setOrderBy(SCORE)
        makeQuery()
    }


    private fun formatScores(scores: List<Score>): String {
        val stringBuilder = StringBuilder()
        for (score in scores) {
            stringBuilder.append(score.score.toString() + " ")
        }
        return stringBuilder.toString()
    }

    /* ###############################################################
    * ################ Async database queries   #######################
    * ###############################################################*/

    fun setDifficulty(difficulty: String) {
        _difficulty.value = difficulty
    }

    fun setOrderBy(orderBy: String) {
        _orderBy.value = orderBy
    }

    fun makeQuery() {
        uiScope.launch {
            scores.value = getScoresFromDatabase()
        }
    }

    private suspend fun getScoresFromDatabase(): List<Score>? {
        return withContext(Dispatchers.IO) {
            when(_orderBy.value) {
                "Score" -> {database.getALLOrderedByScore(_difficulty.value!!)}
                "Date" -> {database.getALLOrderedByTime(_difficulty.value!!)}
                else -> throw IllegalArgumentException("orderBy value is illegal!")
            }
        }
    }

   /*---------------------------------------><-------------------------------------*/

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}