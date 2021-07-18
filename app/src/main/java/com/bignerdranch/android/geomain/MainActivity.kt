package com.bignerdranch.android.geomain

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex


        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.previous_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {
            checkAnswer(true)

            quizViewModel.listOfIndex.add(quizViewModel.currentIndex)
            trueButton.isEnabled = false
            falseButton.isEnabled = false
            correctAnswerPercentage(quizViewModel.listOfIndex.size, quizViewModel.countOfCorrectAnswers)
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
            quizViewModel.listOfIndex.add(quizViewModel.currentIndex)
            trueButton.isEnabled = false
            falseButton.isEnabled = false
            correctAnswerPercentage(quizViewModel.listOfIndex.size, quizViewModel.countOfCorrectAnswers)

        }
        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            checkQuestionIndex(quizViewModel.currentIndex)
            updateQuestion()

        }
        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        previousButton.setOnClickListener {
            quizViewModel.moveToPrevious()
            checkQuestionIndex(quizViewModel.currentIndex)
            updateQuestion()
        }
        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun correctAnswerPercentage(size: Int, countOfAnswers: Int) {
        if (quizViewModel.questionBankSize == size) {
            val a = countOfAnswers.toFloat() / (size.toFloat() / 100)
            Toast.makeText(this, "percentage of correct answers: $a", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkQuestionIndex(index: Int) {
        if (quizViewModel.listOfIndex.contains(index)) {
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        } else {
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }

    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            quizViewModel.countOfCorrectAnswers++
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
    }

}