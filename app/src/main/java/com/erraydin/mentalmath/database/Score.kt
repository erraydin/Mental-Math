package com.erraydin.mentalmath.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score_history_table")
data class Score(
    @PrimaryKey(autoGenerate = true)
    var scoreId: Long = 0L,

    @ColumnInfo(name = "difficulty")
    var difficulty: String,

    @ColumnInfo(name = "date_milli")
    var dateMilli: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "score")
    var score: Int = 0
)
