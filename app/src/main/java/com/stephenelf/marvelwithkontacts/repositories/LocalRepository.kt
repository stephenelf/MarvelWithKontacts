package com.stephenelf.marvelwithkontacts.repositories

import com.stephenelf.marvelwithkontacts.database.Character
import com.stephenelf.marvelwithkontacts.database.MyDatabase
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LocalRepository(val myDatabase: MyDatabase) {

    fun getAllCharacters(): Maybe<List<Character>> {
        return myDatabase.characterDao().getAllCharacters()
    }

    fun saveCharacters(characters: List<Character>) {
        Completable.fromAction { myDatabase.characterDao().insert(characters) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }
}