package com.stephenelf.marvelwithkontacts.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stephenelf.marvelwithkontacts.data.database.dao.CharacterDao

@TypeConverters(Converters::class)
@Database(entities = arrayOf(com.stephenelf.marvelwithkontacts.data.database.Character::class), exportSchema = false, version = 1)
abstract class MyDatabase:RoomDatabase() {

    abstract fun characterDao(): CharacterDao
}