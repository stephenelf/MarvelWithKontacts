package com.stephenelf.marvelwithkontacts.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stephenelf.marvelwithkontacts.data.database.Character
import com.stephenelf.marvelwithkontacts.data.database.MyDatabase
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LocalRepository(val myDatabase: MyDatabase) {

    fun getAllCharacters(): LiveData<List<Character>> {
        return myDatabase.characterDao().getAllCharacters()
    }

    fun saveCharacters(characters: List<Character>) {
        Completable.fromAction { myDatabase.characterDao().insert(characters) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }
}