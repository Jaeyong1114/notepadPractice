package com.example.notepadpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notepadpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), WordAdapter.ItemClickListener {
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


        wordAdapter = WordAdapter(dummyList, this)
        binding.wordRecyclerView.apply {
            adapter =wordAdapter //어댑터 연결
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            val dividerItemDecoration = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)

        }

    }

    override fun onClick(word: Word) {
        Toast.makeText(this,"${word.text}가 클릭됐습니다",Toast.LENGTH_SHORT)
    }
}