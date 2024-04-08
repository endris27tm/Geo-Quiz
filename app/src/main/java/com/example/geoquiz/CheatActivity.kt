package com.example.geoquiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheatBinding
    private var answerShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.showAnswerButton.setOnClickListener {

            binding.cheatHeadingTxt.setText(R.string.answerIs_text)

            val answerIsTrue = intent.getBooleanExtra("ANSWER_IS_TRUE", false)
            binding.answerTextView.text = if (answerIsTrue) "Yes" else "No"

            answerShown = true
            val resultData = Intent().apply {
                putExtra("EXTRA_CHEATED", true)
            }
            setResult(Activity.RESULT_OK, resultData)
        }
    }

    override fun onBackPressed() {
        Log.d("CheatActivity", "Back pressed, answerShown: $answerShown")
        if (answerShown) {
            Log.d("CheatActivity", "Finishing activity due to cheating.")
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra("EXTRA_CHEATED", true)
            })
            finish()
        } else {
            super.onBackPressed()
        }
    }

}
