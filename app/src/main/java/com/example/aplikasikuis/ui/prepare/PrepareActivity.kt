package com.example.aplikasikuis.ui.prepare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aplikasikuis.R
import com.example.aplikasikuis.databinding.ActivityPrepareBinding
import com.example.aplikasikuis.ui.content.ContentActivity
import com.example.aplikasikuis.ui.main.MainActivity
import org.jetbrains.anko.startActivity

class PrepareActivity : AppCompatActivity() {

    private lateinit var prepareBinding: ActivityPrepareBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareBinding = ActivityPrepareBinding.inflate(layoutInflater)
        setContentView(prepareBinding.root)

        onClick()
    }

    private fun onClick() {
        prepareBinding.btnStart.setOnClickListener {
            val nickname = prepareBinding.etNickname.text.toString()
            if (checkValidation(nickname)) {
                startActivity<ContentActivity>(
                    ContentActivity.EXTRA_NICKNAME to nickname
                )
            }
        }
    }

    private fun checkValidation(nickname: String): Boolean {
        return if (nickname.isEmpty()) {
            prepareBinding.etNickname.error = getString(R.string.please_field_your_nickname)
            false
        } else {
            true
        }
    }
}