package com.example.aplikasikuis.ui.score

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.aplikasikuis.R
import com.example.aplikasikuis.databinding.ActivityScoreBinding
import com.example.aplikasikuis.repository.Repository
import com.example.aplikasikuis.ui.main.MainActivity
import org.jetbrains.anko.startActivity

class ScoreActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_SCORE = "extra_score"
        const val EXTRA_NICKNAME = "extra_nickname"
    }
    private lateinit var scoreBinding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scoreBinding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(scoreBinding.root)

        //Get Data
        if (intent != null){
            val score = intent.getIntExtra(EXTRA_SCORE, 0)
            val nickname = intent.getStringExtra(EXTRA_NICKNAME)

            scoreBinding.tvNickname.text = nickname
            scoreBinding.tvScore.text = score.toString()
        }

        onClick()
    }

    private fun onClick() {
        scoreBinding.btnDone.setOnClickListener {
            startActivity<MainActivity>()
            finishAffinity()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity<MainActivity>()
        finishAffinity()
    }
}