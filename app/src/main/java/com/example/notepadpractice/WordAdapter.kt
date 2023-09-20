package com.example.notepadpractice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notepadpractice.databinding.ItemWordBinding

class WordAdapter(private val list : MutableList<Word>) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() { //어댑터가 사용할 데이터 collection 필요함 data class MutableList 는 변경가능한 리스트(데이터 추가 삭제 가능)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemWordBinding.inflate(inflater, parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.binding.apply{
            val word = list[position]
            textTextView.text =word.text
            meanTextView.text = word.mean
            typeChip.text = word.type
        }
    }

    override fun getItemCount(): Int { //어댑터가 가지고있는 데이터의 리스트 갯수를 반환
        return list.size
    }


    class WordViewHolder(val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root){

    }
}

