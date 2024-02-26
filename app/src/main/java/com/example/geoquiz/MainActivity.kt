package com.example.geoquiz

import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.yesButton.setOnClickListener{ view : View ->
        checkAnswer(true)

        }
        binding.noButton.setOnClickListener{ view : View ->
        checkAnswer(false)
        }

        binding.nextButton.setOnClickListener{
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        binding.prvButton.setOnClickListener{
            if (currentIndex == 0) {
                val snackbar = Snackbar.make(binding.root, "You may not create a paradox", Snackbar.LENGTH_SHORT)

                snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.snackbarActionText))
                snackbar.setTextColor(ContextCompat.getColor(this, R.color.snackbarText))
                snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.snackbarBackground))
                snackbar.show()
            }else{
                currentIndex = (currentIndex - 1) % questionBank.size
                updateQuestion()
            }

        }

        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)

        val headingTextResId = questionBank[currentIndex].headingResId
        binding.headingTextView.setText(headingTextResId)

        }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)

        val headingTextResId = questionBank[currentIndex].headingResId
        binding.headingTextView.setText(headingTextResId)
    }

    private fun showSnackbar() {
        val snackbar = Snackbar.make(binding.root, "This is a Snackbar!", Snackbar.LENGTH_LONG)

        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.snackbarActionText))
        snackbar.setTextColor(ContextCompat.getColor(this, R.color.snackbarText))
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.snackbarBackground))

        snackbar.show()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        val snackbar = Snackbar.make(binding.root, messageResId, Snackbar.LENGTH_SHORT)

        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.snackbarActionText))
        snackbar.setTextColor(ContextCompat.getColor(this, R.color.snackbarText))
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.snackbarBackground))
        snackbar.show()
    }

}

