package com.stephenelf.marvelwithkontacts.ui

import androidx.lifecycle.ViewModel

import com.stephenelf.marvelwithkontacts.data.People
import com.stephenelf.marvelwithkontacts.domain.repositories.Repository
import io.reactivex.Single
import javax.inject.Inject


import com.stephenelf.marvelwithkontacts.domain.repositories.Repository
import javax.inject.Inject

class ContactsViewModel @Inject constructor(private  val repository:Repository):
    ViewModel() {

    val people = repository.people


}

