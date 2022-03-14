package com.example.aplikasikuis.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.aplikasikuis.R
import com.example.aplikasikuis.databinding.ActivityMainBinding
import com.example.aplikasikuis.repository.Repository
import com.example.aplikasikuis.ui.prepare.PrepareActivity
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        onclick()
        val contents = Repository.getDataContents(this)
        Log.d("MainActivity","onCreate:size: ${contents?.size}")
        Log.d("MainActivity","onCreate:size: ${contents?.get(0)?.body}")
        Log.d("MainActivity","onCreate:size: ${contents?.get(0)?.answers?.get(0)?.answer}")
    }

    private fun onclick() {
        mainBinding.btnPlay.setOnClickListener {
            startActivity<PrepareActivity>()
        }
    }
}