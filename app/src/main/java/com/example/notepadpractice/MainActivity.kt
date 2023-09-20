package com.example.notepadpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notepadpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var wordAdapter: WordAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dummyList = mutableListOf(
            Word("weather","날씨","명사"),
            Word("honey","꿀","명사"),
            Word("run","실행하다","동사"),
        )


        wordAdapter = WordAdapter(dummyList)
        binding.wordRecyclerView.apply {
            adapter =wordAdapter //어댑터 연결
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        }

    }
}