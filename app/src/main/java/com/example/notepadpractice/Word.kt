package com.example.notepadpractice

import androidx.room.Entity
import androidx.room.PrimaryKey

//데이터 클래스는 하나이상의 property가 필요함    데이터를 홀딩하기 위한 클래스


@Entity(tableName = "word")
data class Word(
    val text : String,
    val mean : String,
    val type : String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0, //autoGenerate 는 id값을 자동으로만들어줌
)
