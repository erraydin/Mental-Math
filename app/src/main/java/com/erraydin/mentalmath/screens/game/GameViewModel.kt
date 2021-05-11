package com.erraydin.mentalmath.screens.game

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.*
import java.lang.IllegalArgumentException
import kotlin.random.Random

class GameViewModel(difficulty: String) : ViewModel() {
    private val _remainingTime = MutableLiveData<Long>()
    private val remainingTime: LiveData<Long>
        get() = _remainingTime

    val remainingTimeString = Transformations.map(remainingTime) { time ->
        time.toString()
    }
    val difficulty = difficulty
    private val _gameFinished = MutableLiveData<Boolean>()
    val gameFinished: LiveData<Boolean>
        get() = _gameFinished

    private val _question = MutableLiveData<String>()
    val question: LiveData<String>
        get() = _question

    private val _userAnswer = MutableLiveData<String>()
    val userAnswer: LiveData<String>
        get() = _userAnswer

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    val scoreString = Transformations.map(score) { score ->
        score.toString()
    }

    private var result: Int = 0

    private var timer: CountDownTimer? = null

    companion object {
        //        const val EASY = "Easy"
//        const val MEDIUM = "Medium"
//        const val HARD = "Hard"
//        const val EXPERT = "Expert"
        const val BUTTON_0 = "0"
        const val BUTTON_1 = "1"
        const val BUTTON_2 = "2"
        const val BUTTON_3 = "3"
        const val BUTTON_4 = "4"
        const val BUTTON_5 = "5"
        const val BUTTON_6 = "6"
        const val BUTTON_7 = "7"
        const val BUTTON_8 = "8"
        const val BUTTON_9 = "9"
        const val BUTTON_DOT = "."
        const val BUTTON_DIVISION = "/"
        const val BUTTON_MINUS = "-"
        const val ONE_SECOND = 1000L
        const val TOTAL_TIME = 12000L
    }

    init {
        nextQuestion()
        Log.i("GameViewMode", "GameViewModel created!")
        _score.value = 0
        _userAnswer.value = ""
        _remainingTime.value = TOTAL_TIME / ONE_SECOND
        startTimer(TOTAL_TIME)
        timer?.start()
    }


    /* ############################################################################
    * #####################    GAME LOGIC      ####################################
    * ############################################################################*/


    fun nextQuestion() {
        val operand1 = Random.nextInt(0, 99)
        val operand2 = Random.nextInt(0, 99)
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

    fun skipQuestion() {
        if (_score.value != null && _score.value!! > 0) {
            _score.value = _score.value?.minus(1)
        }

        _userAnswer.value = ""
        nextQuestion()
    }

    //Handles in game keyboard character-button presses
    fun addToAnswer(char: String) {
        if (_remainingTime.value!! >= 1) {
            when (char) {
                "-" -> if (_userAnswer.value == "") {
                    _userAnswer.value += char
                }
                "/" -> if (_userAnswer.value?.toIntOrNull() != null) {
                    _userAnswer.value += char
                }
                "." -> if (_userAnswer.value == "" || _userAnswer.value == "-") {
                    _userAnswer.value += "0${char}"
                } else if (_userAnswer.value?.toIntOrNull() != null) {
                    _userAnswer.value += char
                }
                else -> _userAnswer.value += char
            }
        }
    }

    fun incrementScore() {
        _score.value = _score.value?.plus(1)
    }

    fun backspace() {
        if (_userAnswer.value != "") {
            _userAnswer.value = _userAnswer.value?.dropLast(1)
        }
    }

    fun onGameFinishEnd() {
        _gameFinished.value = false
    }

    fun getResult(): Int {
        return result
    }


    /* ############################################################################
    * #####################    TIMER METHODS      #################################
    * ############################################################################*/


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

    //The following actually completely destroys and recreates timer, since there is no pause-resume mechanism
    fun pauseTimer() {
        timer?.cancel()
        timer = null
    }

    fun resumeTimer() {
        _remainingTime.value?.let { startTimer(it * ONE_SECOND) }
    }

    // Prevent memory leak
    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
        timer?.cancel()
        timer = null
    }


}