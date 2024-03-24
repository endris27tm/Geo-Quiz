package com.example.geoquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val scorePercentage = intent.getIntExtra("SCORE_PERCENTAGE", 0)

        val scoreTextView: TextView = findViewById(R.id.score_text_view)
        scoreTextView.text = getString(R.string.your_score, scorePercentage)

        val tryAgainButton: ImageButton = findViewById(R.id.try_again_button)
        tryAgainButton.setOnClickListener {
            val homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
            finish()
        }

    }
}