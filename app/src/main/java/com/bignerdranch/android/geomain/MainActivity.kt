package com.bignerdranch.android.geomain

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentIndex = 0
    private val listOfIndex = mutableListOf<Int>()
    private var countOfCorrectAnswers =0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.previous_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View ->
//            val toast = Toast.makeText(
//                    this,
//                    R.string.correct_toast,
//                    Toast.LENGTH_SHORT
//
//            )
//            toast.setGravity(Gravity.TOP, 0, 0)
//            toast.show()
            checkAnswer(true)

            listOfIndex.add(currentIndex)
            trueButton.isEnabled = false
            falseButton.isEnabled = false
            correctAnswerPercentage(listOfIndex.size,countOfCorrectAnswers)
        }
        falseButton.setOnClickListener { view: View ->
//            val toast = Toast.makeText(
//                    this,
//                    R.string.incorrect_toast,
//                    Toast.LENGTH_SHORT
//            )
//            toast.setGravity(Gravity.TOP, 0, 0)
//            toast.show()
            checkAnswer(false)
            listOfIndex.add(currentIndex)
            trueButton.isEnabled = false
            falseButton.isEnabled = false
            correctAnswerPercentage(listOfIndex.size,countOfCorrectAnswers)

        }
        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            checkQuestionIndex(currentIndex)
            updateQuestion()

        }
        questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        previousButton.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex = (currentIndex - 1) % questionBank.size
            } else currentIndex += questionBank.size - 1

            checkQuestionIndex(currentIndex)
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

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }
    private fun correctAnswerPercentage(size:Int,countOfAnswers:Int){
        if (questionBank.size==size){
            val a =countOfAnswers.toFloat()/(size.toFloat()/100)
            Toast.makeText(this, "percentage of correct answers: $a",Toast.LENGTH_LONG).show()
        }
    }

    private fun checkQuestionIndex(index: Int) {
        if (listOfIndex.contains(index)) {
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        } else {
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }

    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            countOfCorrectAnswers++
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
    }

}