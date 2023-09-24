package com.example.notepadpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import com.example.notepadpractice.databinding.ActivityAddBinding
import com.google.android.material.chip.Chip

class AddActivity : AppCompatActivity() {
    private var originWord : Word? = null
    private lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        binding.addButton.setOnClickListener{
            if(originWord == null) add() else edit()

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
        binding.textInputEditText.addTextChangedListener{
            it?.let{ text ->
                binding.textTextInputLayout.error = when(text.length) {
                    0 -> "값을 입력해주세요"
                    1 -> "2자 이상을 입력해주세요"
                    else -> null //에러가 null 이면 정상
                }
            }
        }

        originWord = intent.getParcelableExtra("originWord")
        originWord?.let{ word ->
            binding.textInputEditText.setText(word.text)
            binding.meanTextInputEditText.setText(word.mean)
            val selectedChip = binding.typeChipGroup.children.firstOrNull{ (it as Chip).text == word.type } as? Chip
            selectedChip?.isChecked = true
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
            val intent = Intent().putExtra("isUpdated",true)
            setResult(RESULT_OK,intent)
            finish()
        }.start()




    }

    private fun edit(){
        val text = binding.textInputEditText.text.toString()
        val mean = binding.meanTextInputEditText.text.toString()
        val type = findViewById<Chip>( binding.typeChipGroup.checkedChipId).text.toString()
        val editWord = originWord?.copy(text = text,mean = mean, type = type)

        Thread {
            editWord?.let { word ->
                AppDatabase.getInstance(this)?.wordDao()?.update(word)
                val intent = Intent().putExtra("editWord",editWord)
                setResult(RESULT_OK,intent)
                runOnUiThread { Toast.makeText(this, "수정을 완료했습니다", Toast.LENGTH_SHORT).show() }
                finish()
            }
        }.start()


    }
}