package com.erraydin.mentalmath.screens.game

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val _remainingTime = MutableLiveData<Long>()
    val remainingTime : LiveData<Long>
        get() = _remainingTime


    private val timer: CountDownTimer
    companion object {
        const val DONE = 0L
        const val ONE_SECOND = 1000L
        const val TOTAL_TIME = 12000L
    }

    init {
        Log.i("GameViewMode", "GameViewModel created!")
        _remainingTime.value = TOTAL_TIME / ONE_SECOND

        timer = object : CountDownTimer(TOTAL_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _remainingTime.value = millisUntilFinished / ONE_SECOND
            }

            override fun onFinish() {
                // TODO implement what should happen when the timer finishes
            }
        }

        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
        timer.cancel()
    }
}