package com.example.finalprojectfh

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @ColumnInfo
    val content: String,
    @ColumnInfo
    val timestamp: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
)