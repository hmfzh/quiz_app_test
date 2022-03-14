package com.example.aplikasikuis.ui.content

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasikuis.R
import com.example.aplikasikuis.adapter.ContentAdapter
import com.example.aplikasikuis.databinding.ActivityContentBinding
import com.example.aplikasikuis.model.Content
import com.example.aplikasikuis.repository.Repository
import com.example.aplikasikuis.ui.content.ContentActivity.Companion.EXTRA_NICKNAME
import com.example.aplikasikuis.ui.main.MainActivity
import com.example.aplikasikuis.ui.score.ScoreActivity
import org.jetbrains.anko.startActivity

class ContentActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NICKNAME = "extra_nickname"
        const val EXTRA_CONTENTS = "extra_contents"
    }

    private lateinit var contentAdapter: ContentAdapter
    private lateinit var contentBinding: ActivityContentBinding
    private lateinit var layoutManager: LinearLayoutManager
    private var dataSize = 0
    private var currentPosition = 0
    private var nickname: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentBinding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(contentBinding.root)

        //Init
        contentAdapter = ContentAdapter()

        //Get Data
        if (intent != null) {
            nickname = intent.getStringExtra(EXTRA_NICKNAME)
        }

        if (savedInstanceState != null) {
            nickname = savedInstanceState.getString(EXTRA_NICKNAME)
            val contents = savedInstanceState.getParcelableArrayList<Content>(EXTRA_CONTENTS)
            showDataContents(contents)
        } else {
            //Get Data from Repository
            val contents = Repository.getDataContents(this)
            //Show Data
            showDataContents(contents)
        }

        onClick()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_NICKNAME, nickname)
        val contents = contentAdapter.getResults()
        outState.putParcelableArrayList(EXTRA_CONTENTS, contents as ArrayList)
    }


    private fun showDataContents(contents: List<Content>?) {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        if (contents != null) {
            contentAdapter.setData(contents as MutableList<Content>)
        }
        snapHelper.attachToRecyclerView(contentBinding.rvContent)
        contentBinding.rvContent.layoutManager = layoutManager
        contentBinding.rvContent.adapter = contentAdapter

        dataSize = layoutManager.itemCount
        contentBinding.pbContent.max = dataSize - 1

        //First Show Index
        var index = "${currentPosition + 1} / $dataSize"
        contentBinding.tvIndexContent.text = index

        contentBinding.rvContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentPosition = layoutManager.findFirstVisibleItemPosition()
                index = "${currentPosition + 1} / $dataSize"
                contentBinding.tvIndexContent.text = index
                contentBinding.pbContent.progress = currentPosition
            }
        })
    }

    private fun onClick() {
        contentBinding.btnBack.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.are_you_sure))
                .setMessage(getString(R.string.message_exit))
                .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                    dialog.dismiss()
                    startActivity<MainActivity>()
                    finishAffinity()
                }
                .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
        contentBinding.btnNextContent.setOnClickListener {
            if (currentPosition < dataSize - 1){
                contentBinding.rvContent.smoothScrollToPosition(currentPosition + 1)
            }else{
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.are_you_sure))
                    .setMessage(getString(R.string.message_input_data))
                    .setPositiveButton(getString(R.string.yes)){dialog,_->
                        val contents = contentAdapter.getResults()
                        val totalQuiz = contents.size
                        var totalCorrectAnswer = 0

                        for (content in contents){
                            for (answer in content.answers!!){
                                if (answer.isClick == true && answer.correctAnswer == true){
                                    totalCorrectAnswer += 1
                                }
                            }
                        }

                        val totalScore = totalCorrectAnswer.toDouble() / totalQuiz * 100

                        startActivity<ScoreActivity>(
                            ScoreActivity.EXTRA_NICKNAME to nickname,
                            ScoreActivity.EXTRA_SCORE to totalScore.toInt()
                        )
                        dialog.dismiss()
                    }
                    .setNegativeButton(getString(R.string.no)){dialog,_->
                        dialog.dismiss()
                    }
                    .show()
            }
        }

        contentBinding.btnPrevContent.setOnClickListener {
            contentBinding.rvContent.smoothScrollToPosition(currentPosition - 1)
        }
    }


}