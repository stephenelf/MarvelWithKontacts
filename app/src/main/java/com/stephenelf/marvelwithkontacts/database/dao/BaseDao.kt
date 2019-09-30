package com.stephenelf.marvelwithkontacts.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t: List<T>): LongArray

    @Update
    fun update(t: T)

    @Delete
    fun delete(t: T)
}