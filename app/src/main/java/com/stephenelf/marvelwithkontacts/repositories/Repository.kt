package com.stephenelf.marvelwithkontacts.repositories

import android.content.Context
import android.net.Uri
import com.orhanobut.logger.Logger
import com.stephenelf.marvelwithkontacts.database.Character
import com.stephenelf.marvelwithkontacts.net.response.CharacterContainer
import com.stephenelf.marvelwithkontacts.net.response.CharacterResponse
import com.stephenelf.marvelwithkontacts.util.People
import io.reactivex.Maybe
import io.reactivex.MaybeSource
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import io.reactivex.schedulers.Schedulers
import ir.mirrajabi.rxcontacts.Contact
import java.util.*


class  Repository (val context: Context,private val localRepository: LocalRepository, private val remoteRepository: RemoteRepository,
                                      private val contactsRepository: ContactsRepository){

    fun getPeople(): Single<List<People>> {
        return Singles.zip(
            localRepository.getAllCharacters()
                .switchIfEmpty(Maybe.defer { getFromRemote() }).toSingle()
                .subscribeOn(Schedulers.io()),
            contactsRepository.getAllContacts(context).subscribeOn(Schedulers.io())
        ) { localCharacters:List<Character>, contacts: List<Contact> -> mergePeople(localCharacters, contacts) }

    }

    private fun getFromRemote(): MaybeSource<List<Character>> {
        Logger.e("Get from remote")
       // val characters: List<Character> =
            return remoteRepository.getCharacters()
            .onErrorReturnItem(CharacterResponse(0,"","","",
                "","", CharacterContainer(0,0,0,0, emptyList())
            )).flatMapMaybe { characterResponse: CharacterResponse -> Maybe.just(characterResponse.data.results) }
     //   localRepository.saveCharacters(characters)
       // Logger.d("remote characters:"+characters.size)
       // return Maybe.just(characters)
    }



    private fun mergePeople(localCharacters: List<Character>, contacts: List<Contact>): List<People> {
        localRepository.saveCharacters(localCharacters)
        val people = ArrayList<People>()

        Logger.d("merging contacts:"+contacts.size)
        Logger.d("merging characters:"+localCharacters.size)
        Logger.d("merging characters:"+localCharacters.isEmpty())

        localCharacters.forEach { character ->
            people.add(People(character.name, Uri.parse(character.thumbnail.toString()),""))}


        contacts.forEach { contact ->
             people.add(
               People(
                contact.displayName?:"no name",
                contact.thumbnail?:Uri.parse(""),
                phone = if (contact.phoneNumbers.size > 0) contact.phoneNumbers.iterator().next() else null
            )
        )}



        people.sortWith(Comparator { (name: String), (name1: String) -> name.compareTo(name1) })
        Logger.d("merging people:"+people.size)
        return people
    }
}