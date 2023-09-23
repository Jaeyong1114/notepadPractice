package com.example.notepadpractice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notepadpractice.databinding.ItemWordBinding


class WordAdapter(
    private val list : MutableList<Word>,//어댑터가 사용할 데이터 collection 필요함 data class MutableList 는 변경가능한 리스트(데이터 추가 삭제 가능)
    private val itemClickListener: ItemClickListener? = null
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        /*val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater*/
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWordBinding.inflate(inflater, parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = list[position]
        holder.bind(word)
        holder.itemView.setOnClickListener{
            itemClickListener?.onClick(word)
        }

    }

    override fun getItemCount(): Int { //어댑터가 가지고있는 데이터의 리스트 갯수를 반환
        return list.size
    }



    class WordViewHolder(private val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(word: Word){
            binding.apply{
                textTextView.text =word.text
                meanTextView.text = word.mean
                typeChip.text = word.type

            }

        }
    }

    interface ItemClickListener {
        fun onClick(word: Word)
    }

}

