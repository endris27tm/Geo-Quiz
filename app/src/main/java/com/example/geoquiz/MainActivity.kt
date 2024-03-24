package com.example.geoquiz

import android.content.Intent
import android.nfc.Tag
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import com.example.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }
    private lateinit var binding: ActivityMainBinding
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


    private val questionBank = listOf(
        Question(R.string.heading_australia, R.string.question_australia, true),
        Question(R.string.heading_oceans, R.string.question_oceans, true),
        Question(R.string.heading_mideast, R.string.question_mideast, true),
        Question(R.string.heading_Africa, R.string.question_africa, false),
        Question(R.string.heading_americas, R.string.question_americas, false),
        Question(R.string.heading_asia, R.string.question_asia, false),
        Question(R.string.heading_life, R.string.question_life, false),
        Question(R.string.heading_tree, R.string.question_tree, true),
        Question(R.string.heading_amazon, R.string.question_amazon, true),
    )


    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt("CurrentQuestionIndex", 0)
        }

        updateQuestion()

        binding.yesButton.setOnClickListener { _: View ->
            checkAnswer(true)
        }
        binding.noButton.setOnClickListener { _: View ->
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        binding.cheatButton.setOnClickListener{
            // start CheatActivity
            val intent = Intent(this,CheatActivity::class.java)
            startActivity(intent)
        }

        binding.prvButton.setOnClickListener {
            if (currentIndex == 0) {
                val snackbar = Snackbar.make(binding.root, "You may not create a paradox", Snackbar.LENGTH_SHORT)
                snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.snackbarActionText))
                snackbar.setTextColor(ContextCompat.getColor(this, R.color.snackbarText))
                snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.snackbarBackground))
                snackbar.show()
            } else {
                currentIndex = (currentIndex - 1) % questionBank.size
                updateQuestion()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState() called")
        outState.putInt("CurrentQuestionIndex", currentIndex)
    }

    private fun updateQuestion() {
        val question = questionBank[currentIndex]

        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)

        val headingTextResId = questionBank[currentIndex].headingResId
        binding.headingTextView.setText(headingTextResId)

        binding.yesButton.isEnabled = !question.isAnswered
        binding.noButton.isEnabled = !question.isAnswered
    }

    private var score = 0

    private fun checkAnswer(userAnswer: Boolean) {
        if (questionBank[currentIndex].isAnswered) {
            // Show a snackbar message indicating the question has already been answered
            Snackbar.make(binding.root, R.string.already_answered, Snackbar.LENGTH_SHORT).apply {
                setActionTextColor(ContextCompat.getColor(context, R.color.snackbarActionText))
                setTextColor(ContextCompat.getColor(context, R.color.snackbarText))
                setBackgroundTint(ContextCompat.getColor(context, R.color.snackbarBackground))
                show()
            }
            return
        }

        val correctAnswer = questionBank[currentIndex].answer
        if (userAnswer == correctAnswer) {
            score++ // Increment score if the answer is correct
        }

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        // Display the snackbar message for correct/incorrect answer
        Snackbar.make(binding.root, messageResId, Snackbar.LENGTH_SHORT).apply {
            setActionTextColor(ContextCompat.getColor(context, R.color.snackbarActionText))
            setTextColor(ContextCompat.getColor(context, R.color.snackbarText))
            setBackgroundTint(ContextCompat.getColor(context, R.color.snackbarBackground))
            show()
        }

        questionBank[currentIndex].isAnswered = true
        binding.yesButton.isEnabled = false
        binding.noButton.isEnabled = false

        // Check if all questions have been answered and show the score if so
        if (questionBank.all { it.isAnswered }) {
            showScoreScreen()
        }
    }
    private fun showScoreScreen() {
        val scorePercentage = (score * 100) / questionBank.size
        val intent = Intent(this, ScoreActivity::class.java).apply {
            putExtra("SCORE_PERCENTAGE", scorePercentage)
        }
        startActivity(intent)
        finish()
    }


}

