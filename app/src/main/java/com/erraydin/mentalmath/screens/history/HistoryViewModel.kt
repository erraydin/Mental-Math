package com.erraydin.mentalmath.screens.history

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.*
import com.erraydin.mentalmath.database.Score
import com.erraydin.mentalmath.database.ScoreDatabaseDao
import kotlinx.coroutines.*
import java.lang.StringBuilder

class HistoryViewModel(val database: ScoreDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    companion object {
        const val EASY = "Easy"
        const val MEDIUM = "Medium"
        const val HARD = "Hard"
        const val EXPERT = "Expert"
        const val BY_SCORE = "By Score"
        const val BY_DATE = "By Date"
    }

    /* ###############################################################
    * ################ BoilerPlate Code For Coroutines ###############
    * ###############################################################*/

    //This is for cancelling coroutines when the viewModel is destroyed
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /* ###############################################################
    * ############ End of BoilerPlate Code For Coroutines #############
    * ###############################################################*/


    private val _difficulty = MutableLiveData<String>()
    val difficulty: LiveData<String>
        get() = _difficulty

    private val _orderBy = MutableLiveData<String>()
    val orderBy: LiveData<String>
        get() = _orderBy


    private val scores = MutableLiveData<List<Score>>()
    val scoresString = Transformations.map(scores) { scores ->
        formatScores(scores)
    }

    init {
        setDifficulty(MEDIUM)
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
        uiScope.launch {
            scores.value = getScoresFromDatabase(difficulty)
        }
    }

    private suspend fun getScoresFromDatabase(difficulty: String): List<Score>? {
        return withContext(Dispatchers.IO) {
            database.getALLOrderedByScore(_difficulty.value!!)
        }
    }

    /* ###############################################################
    * ################ End of Async database queries  ###############
    * ###############################################################*/

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}