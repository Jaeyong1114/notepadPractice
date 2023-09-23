package com.example.notepadpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notepadpractice.databinding.ActivityAddBinding
import com.google.android.material.chip.Chip

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        val types = listOf(
            "명사", "동사", "형용사", "대명사", "부사", "전치사", "접속사"
        )
        binding.typeChipGroup.apply {
            types.forEach { text ->
                addView(createChip(text))
            }

        }
    }

    private fun createChip(text: String): Chip {
        return Chip(this).apply{
            setText(text)
            isCheckable = true
            isClickable = true

        }
    }
}