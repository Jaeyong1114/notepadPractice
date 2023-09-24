package com.example.notepadpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notepadpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), WordAdapter.ItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var wordAdapter: WordAdapter
    private var selectedWord:Word? = null
    private val updateAddWordResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
    // 액티비티에서 result 가져오기 데이터 바뀌면 액티비티 다시돌아왔을때 로드하기위해
        result ->
        val isUpdated =result.data?.getBooleanExtra("isUpdated",false)?:false
        if(result.resultCode == RESULT_OK && isUpdated){
            updateAddWord()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        binding.addButton.setOnClickListener {

            Intent(this, AddActivity::class.java).let {
                updateAddWordResult.launch(it)
            }
        }

        binding.deleteImageView.setOnClickListener{
            delete()
        }

        binding.editImageView.setOnClickListener{
            edit()
        }

    }


    private fun initRecyclerView() {
        wordAdapter = WordAdapter(mutableListOf(), this)
        binding.wordRecyclerView.apply {
            adapter = wordAdapter //어댑터 연결
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            val dividerItemDecoration = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)

        }
        Thread {
            val list = AppDatabase.getInstance(this)?.wordDao()?.getAll() ?: emptyList()
            wordAdapter.list.addAll(list)
            runOnUiThread {
            wordAdapter.notifyDataSetChanged() //데이터 넣었다는걸 어댑터한테 알려줘야함. 화면에 로드하기위해서
        }
        }.start()
    }

    private fun updateAddWord(){
        Thread{
            AppDatabase.getInstance(this)?.wordDao()?.getLatestWord()?.let{word ->
                wordAdapter.list.add(0,word)
                runOnUiThread{
                    wordAdapter.notifyDataSetChanged()
                }
            }

        }.start()
    }




    private fun updateEditWord(word: Word){
        val index = wordAdapter.list.indexOfFirst { it.id == word.id }
        wordAdapter.list[index] = word
        runOnUiThread{
            selectedWord = word
            wordAdapter.notifyItemChanged(index)
            binding.textTextView.text = word.text
            binding.meanTextView.text = word.mean

        }
    }

    private fun delete(){
        if(selectedWord == null) return
        Thread{
            selectedWord?.let{
                word->
                AppDatabase.getInstance(this)?.wordDao()?.delete(word)
                runOnUiThread{
                    wordAdapter.list.remove(word)
                    wordAdapter.notifyDataSetChanged()
                    binding.textTextView.text = ""
                    binding.meanTextView.text = ""
                    Toast.makeText(this,"삭제가 완료 됐습니다",Toast.LENGTH_SHORT).show()
                }
            }

        }.start()
    }


    private val updateEditWordResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        // 액티비티에서 result 가져오기 데이터 바뀌면 액티비티 다시돌아왔을때 로드하기위해
            result ->
        val editWord=result.data?.getParcelableExtra<Word>("editWord")
        if(result.resultCode == RESULT_OK && editWord !=null) {
                updateEditWord(editWord)
            }
    }

    private fun edit(){
        if(selectedWord == null) return

        val intent = Intent(this,AddActivity::class.java).putExtra("originWord",selectedWord)
        updateEditWordResult.launch(intent)
    }


    override fun onClick(word: Word) {
        selectedWord = word
        binding.textTextView.text = word.text
        binding.meanTextView.text = word.mean

        Log.d("onClickTest", "클릭중")
    }
}