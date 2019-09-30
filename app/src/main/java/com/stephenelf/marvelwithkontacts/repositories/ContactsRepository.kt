package com.stephenelf.marvelwithkontacts.repositories

import android.content.Context
import io.reactivex.Single
import ir.mirrajabi.rxcontacts.Contact
import ir.mirrajabi.rxcontacts.RxContacts

class ContactsRepository {

    fun getAllContacts(context: Context): Single<List<Contact>> {
        return RxContacts.fetch(context)
            .filter { m -> m.inVisibleGroup == 1 }
            .toSortedList { obj, other -> obj.compareTo(other) }
    }
}