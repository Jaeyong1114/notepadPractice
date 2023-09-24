package com.example.notepadpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notepadpractice.databinding.ActivityAddBinding
import com.google.android.material.chip.Chip

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        binding.addButton.setOnClickListener{
            add()
        }

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

    private fun add(){
        val text = binding.textInputEditText.text.toString()
        val mean = binding.meanTextInputEditText.text.toString()
        val type = findViewById<Chip>( binding.typeChipGroup.checkedChipId).text.toString()
        val word = Word(text,mean, type)

        Thread{
            AppDatabase.getInstance(this)?.wordDao()?.insert(word) //쓰레드를만들어 해당작업 완료
            runOnUiThread{ // 토스트메세지는 UI 쓰레드에서 날려야하므로 여기다가 구현
                Toast.makeText(this,"저장을 완료했습니다",Toast.LENGTH_SHORT).show()

            }
            finish()
        }.start()


    }
}