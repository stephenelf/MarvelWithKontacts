package com.stephenelf.marvelwithkontacts.ui

import androidx.lifecycle.ViewModel
import com.stephenelf.marvelwithkontacts.data.People
import com.stephenelf.marvelwithkontacts.domain.repositories.Repository
import io.reactivex.Single
import javax.inject.Inject

class ContactsViewModel: ViewModel() {

    @Inject
    lateinit var repository: Repository

    var people: Single<List<People>> = repository.getPeople()



}


