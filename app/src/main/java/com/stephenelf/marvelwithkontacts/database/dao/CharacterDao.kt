package com.stephenelf.marvelwithkontacts.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.stephenelf.marvelwithkontacts.database.Character
import io.reactivex.Maybe

@Dao
interface CharacterDao:BaseDao<Character> {

    @Query("SELECT * FROM Character")
    fun getAllCharacters(): Maybe<List<Character>>
}