package com.example.geoquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val finalScore = intent.getIntExtra("FINAL_SCORE", 0)
        val cheatPercentage = intent.getIntExtra("CHEAT_PERCENTAGE", 0)

        findViewById<TextView>(R.id.final_score_text_view).text = getString(R.string.final_score, finalScore)

        if (cheatPercentage > 0) {
            findViewById<TextView>(R.id.cheat_penalty_text_view).text = getString(R.string.cheat_penalty, -cheatPercentage)
        } else {
            findViewById<TextView>(R.id.cheat_penalty_text_view).visibility = View.GONE
        }

        val tryAgainButton: ImageButton = findViewById(R.id.try_again_button)
        tryAgainButton.setOnClickListener {
            val homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
            finish()
        }

    }
}