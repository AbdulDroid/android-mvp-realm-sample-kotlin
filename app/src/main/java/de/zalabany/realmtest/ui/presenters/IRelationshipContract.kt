package de.zalabany.realmtest.ui.presenters

import de.zalabany.realmtest.models.Dog
import de.zalabany.realmtest.models.Person

interface IRelationshipContract {

    interface IRelationshipView {
        fun updatePersonsList(persons: List<Person>)
        fun updateDogsList(dogs: List<Dog>)
        fun toastResult(s: String)
    }

    interface IRelationshipPresenter {
        fun connectPersonAndDog(person: Person, dog: Dog)
    }
}