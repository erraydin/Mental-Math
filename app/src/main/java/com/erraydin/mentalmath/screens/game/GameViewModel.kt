package com.erraydin.mentalmath.screens.game

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.*

class GameViewModel : ViewModel() {
    private val _remainingTime = MutableLiveData<Long>()
    val remainingTime : LiveData<Long>
        get() = _remainingTime


    private var timer: CountDownTimer? = null

    companion object {
        const val DONE = 0L
        const val ONE_SECOND = 1000L
        const val TOTAL_TIME = 12000L
    }

    init {
        Log.i("GameViewMode", "GameViewModel created!")
        _remainingTime.value = TOTAL_TIME / ONE_SECOND
        startTimer(TOTAL_TIME)
//        timer = object : CountDownTimer(TOTAL_TIME, ONE_SECOND) {
//
//            override fun onTick(millisUntilFinished: Long) {
//                _remainingTime.value = millisUntilFinished / ONE_SECOND
//            }
//
//            override fun onFinish() {
//                // TODO implement what should happen when the timer finishes
//            }
//        }

        timer?.start()
    }

    fun pauseTimer() {
        timer?.cancel()
        timer = null
        Log.i("GameFragment", "timer is null: ${timer == null}")
    }

    fun resumeTimer() {
        _remainingTime.value?.let { startTimer(it * ONE_SECOND) }
        Log.i("GameFragment", "timer is not null: ${timer != null}" )
    }

    private fun startTimer(remainingTimeMilli: Long) {
        if (timer == null) {
            timer = object : CountDownTimer(remainingTimeMilli, ONE_SECOND) {

                override fun onTick(millisUntilFinished: Long) {
                    _remainingTime.value = millisUntilFinished / ONE_SECOND
                }

                override fun onFinish() {
                    // TODO implement what should happen when the timer finishes
                }
            }

            timer?.start()
        }

    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
        timer?.cancel()
        timer = null
    }
}