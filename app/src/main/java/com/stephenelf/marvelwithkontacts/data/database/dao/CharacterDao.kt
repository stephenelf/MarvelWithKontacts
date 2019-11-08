package com.stephenelf.marvelwithkontacts.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.stephenelf.marvelwithkontacts.data.database.Character
import io.reactivex.Maybe

@Dao
interface CharacterDao:BaseDao<Character> {

    @Query("SELECT * FROM Character")
    fun getAllCharacters(): Maybe<List<Character>>
}