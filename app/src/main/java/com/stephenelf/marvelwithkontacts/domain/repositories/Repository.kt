package com.stephenelf.marvelwithkontacts.domain.repositories

import android.content.Context
import android.net.Uri
import androidx.lifecycle.*

import com.orhanobut.logger.Logger
import com.stephenelf.marvelwithkontacts.data.People
import com.stephenelf.marvelwithkontacts.data.database.Character
import ir.mirrajabi.rxcontacts.Contact
import java.util.*


class Repository(
    val context: Context,
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository,
    private val contactsRepository: ContactsRepository
) {

    val charsLocal: LiveData<List<Character>> by lazy {
        MutableLiveData<List<Character>>().apply {
            localRepository.getAllCharacters()
        }
    }

    val charsRemote: LiveData<List<Character>> by lazy {
        getFromRemote()
    }

    val peple2 = MediatorLiveData<List<People>>()



    val people: LiveData<List<People>> by lazy {
        MutableLiveData<List<People>>().apply{
            Transformations.map(remoteRepository.getCharacters()){ list ->
               // Logger.e("Repo="+list.size)
                list.forEach {

                    People(
                        it.name,
                        Uri.parse(it.thumbnail.toString()),
                        ""
                    )
                }
            }
        }
    }

    /*
    val people: LiveData<List<People>> by lazy {

       // charsRemote.observe(context, androidx.lifecycle.Observer {

        })



        MutableLiveData<List<People>>().apply {
            Logger.d("merging characters:" + charsRemote.value?.size)
            charsRemote.value?.forEach { character ->
                People(
                    character.name,
                    Uri.parse(character.thumbnail.toString()),
                    ""
                )
            }
        }


    }

    /*
        return Singles.zip(
            localRepository.getAllCharacters()
                .switchIfEmpty( getFromRemote() ).toSingle()
               // .switchIfEmpty(Maybe.defer { getFromRemote() }).toSingle()
                .subscribeOn(Schedulers.io()),
            contactsRepository.getAllContacts(context).subscribeOn(Schedulers.io())
        ) { localCharacters:List<Character>, contacts: List<Contact> -> mergePeople(localCharacters, contacts) }
*/

     */

    private fun getFromRemote(): LiveData<List<Character>> {
        Logger.e("Get from remote")
        // val characters: List<Character> =
        return remoteRepository.getCharacters()
        /*
.onErrorReturnItem(CharacterResponse(0,"","","",
"","", CharacterContainer(0,0,0,0, emptyList())
)).flatMapMaybe { characterResponse: CharacterResponse -> Maybe.just(characterResponse.data.results) }
//   localRepository.saveCharacters(characters)
// Logger.d("remote characters:"+characters.size)
// return Maybe.just(characters)

         */
    }


    private fun mergePeople(
        localCharacters: List<Character>,
        contacts: List<Contact>
    ): List<People> {
        localRepository.saveCharacters(localCharacters)
        val people = ArrayList<People>()

        Logger.d("merging contacts:" + contacts.size)
        Logger.d("merging characters:" + localCharacters.size)
        Logger.d("merging characters:" + localCharacters.isEmpty())

        localCharacters.forEach { character ->
            people.add(
                People(
                    character.name,
                    Uri.parse(character.thumbnail.toString()),
                    ""
                )
            )
        }


        contacts.forEach { contact ->
            people.add(
                People(
                    contact.displayName ?: "no name",
                    contact.thumbnail ?: Uri.parse(""),
                    phone = if (contact.phoneNumbers.size > 0) contact.phoneNumbers.iterator().next() else null
                )
            )
        }



        people.sortWith(Comparator { (name: String), (name1: String) -> name.compareTo(name1) })
        Logger.d("merging people:" + people.size)
        return people
    }
}


