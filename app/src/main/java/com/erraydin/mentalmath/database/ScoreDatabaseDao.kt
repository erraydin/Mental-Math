package com.erraydin.mentalmath.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScoreDatabaseDao {

    @Insert
    fun insert(score: Score)

    @Query("SELECT * from score_history_table WHERE difficulty = :difficulty ORDER BY score, scoreId DESC")
    fun getALLOrderedByDifficulty(difficulty: String) : LiveData<List<Score>>

    @Query("SELECT * from score_history_table WHERE difficulty = :difficulty ORDER BY scoreId, score DESC")
    fun getALLOrderedByTime(difficulty: String) : LiveData<List<Score>>

    @Query("DELETE FROM score_history_table WHERE difficulty = :difficulty")
    fun clear(difficulty: String)
}