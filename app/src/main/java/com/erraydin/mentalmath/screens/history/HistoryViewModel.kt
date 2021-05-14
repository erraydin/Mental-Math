package com.erraydin.mentalmath.screens.history

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.*
import com.erraydin.mentalmath.database.Score
import com.erraydin.mentalmath.database.ScoreDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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

    //This is for cancelling coroutines when the viewmodel is destroyed
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

    private val scores: LiveData<List<Score>> = database.getALLOrderedByScore(MEDIUM)
    val scoresString = Transformations.map(scores) { scores ->
        formatScores(scores)
    }

    private fun formatScores(scores: List<Score>): String {
        val stringBuilder = StringBuilder()
        for (score in scores) {
            stringBuilder.append(score.score.toString() + " ")
        }
        return stringBuilder.toString()
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}