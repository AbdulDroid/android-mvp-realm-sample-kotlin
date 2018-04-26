package de.zalabany.realmtest.ui.presenters

import de.zalabany.realmtest.interfaces.PersistenceCallbackListener
import de.zalabany.realmtest.models.Dog
import de.zalabany.realmtest.models.Person
import de.zalabany.realmtest.persistence.Persistence
import de.zalabany.realmtest.persistence.Storage

class RelationshipPresenter(private val mView: IRelationshipContract.IRelationshipView): IRelationshipContract.IRelationshipPresenter, PersistenceCallbackListener {
    private val mStorage: Persistence

    init {
        mStorage = Storage(this)
        mStorage.retrieveAllPersons()
        mStorage.retrieveAllDogs()
    }

    // region PersistenceCallbackListener
    override fun onPersonsRetrieval(persons: List<Person>) {
        mView.updatePersonsList(persons)
    }

    override fun onDogsRetrieval(dogs: List<Dog>) {
        mView.updateDogsList(dogs)
    }

    override fun onDatabaseQueryRetrieval(personsResult: List<Person>, dogsResult: List<Dog>) {

    }

    // endregion

    // region IRelationshipPresenter
    override fun connectPersonAndDog(person: Person, dog: Dog) {
        person.dogs.add(dog)
        val personUpdated = Person(person.name, person.dogs)
        mStorage.updatePerson(personUpdated)
        mView.toastResult("Dog and Owner connected")
    }

    // endregion
}