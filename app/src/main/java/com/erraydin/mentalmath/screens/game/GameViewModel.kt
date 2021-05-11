package com.erraydin.mentalmath.screens.game

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.*
import java.lang.IllegalArgumentException
import kotlin.random.Random

class GameViewModel : ViewModel() {
    private val _remainingTime = MutableLiveData<Long>()
    val remainingTime: LiveData<Long>
        get() = _remainingTime

    private val _gameFinished = MutableLiveData<Boolean>()
    val gameFinished: LiveData<Boolean>
        get() = _gameFinished

    private val _question = MutableLiveData<String>()
    val question: LiveData<String>
        get() = _question

    private val _userAnswer = MutableLiveData<String>()
    val userAnswer: LiveData<String>
        get() = _userAnswer

    private var result: Int = 0

    private var timer: CountDownTimer? = null

    companion object {
        const val DONE = 0L
        const val ONE_SECOND = 1000L
        const val TOTAL_TIME = 120000L
    }

    init {
        nextQuestion("Easy")
        Log.i("GameViewMode", "GameViewModel created!")
        _userAnswer.value = ""
        _remainingTime.value = TOTAL_TIME / ONE_SECOND
        startTimer(TOTAL_TIME)
        timer?.start()
    }

    fun nextQuestion(difficulty: String) {

        val operand1 = Random.nextInt(10, 99)
        val operand2 = Random.nextInt(10, 99)
        val operation = listOf("+", "-", "×").random()
        result = when (operation) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "×" -> operand1 * operand2
            else -> throw IllegalArgumentException("Operation is invalid!")
        }

        _question.value = "$operand1 $operation $operand2 = "
        _userAnswer.value = ""

    }

    fun pauseTimer() {
        timer?.cancel()
        timer = null
    }

    fun resumeTimer() {
        _remainingTime.value?.let { startTimer(it * ONE_SECOND) }
    }

    private fun startTimer(remainingTimeMilli: Long) {
        if (timer == null) {
            timer = object : CountDownTimer(remainingTimeMilli, ONE_SECOND) {

                override fun onTick(millisUntilFinished: Long) {
                    _remainingTime.value = millisUntilFinished / ONE_SECOND
                }

                override fun onFinish() {
                    _gameFinished.value = true
                }
            }

            timer?.start()
        }

    }

    fun onGameFinishEnd() {
        _gameFinished.value = false
    }

    fun addToAnswer(char: String) {
        _userAnswer.value = _userAnswer.value + char

    }

    fun backspace() {
        if (_userAnswer.value != "") {
            _userAnswer.value = _userAnswer.value?.dropLast(1)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
        timer?.cancel()
        timer = null
    }


}