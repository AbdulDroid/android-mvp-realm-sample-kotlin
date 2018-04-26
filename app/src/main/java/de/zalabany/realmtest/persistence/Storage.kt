package de.zalabany.realmtest.persistence

import de.zalabany.realmtest.models.Person
import de.zalabany.realmtest.interfaces.PersistenceCallbackListener
import de.zalabany.realmtest.models.Dog
import de.zalabany.realmtest.persistence.models.DogDB
import de.zalabany.realmtest.persistence.models.PersonDB
import io.realm.Realm
import io.realm.RealmList

/**
 * This class provides the persistence logic and can be later defined as a singleton
 * It implements the [Persistence] interface which provides the database operations and sends
 * the results back to the MainPresenter in the callback listener [persistenceCallbackListener]
 * however, this callback listener is optional, and can be removed and replaced by having function returns
 */

class Storage(private val persistenceCallbackListener: PersistenceCallbackListener) : Persistence {
    // region Persistence
    override fun sendToDatabase(person: Person) {
        val realm = Realm.getDefaultInstance()

        val dogsDBList = RealmList<DogDB>()
        person.dogs.forEach {
            dogsDBList.add(DogDB(it.name))
        }
        val personDB = PersonDB(person.name, dogsDBList)

        try {
            realm.run {
                beginTransaction()
                copyToRealm(personDB)
                commitTransaction()
            }
        } finally {
            realm.close()
        }
    }

    override fun retrieveAllPersons() {
        val realm = Realm.getDefaultInstance()

        try {
            val result = realm.where(PersonDB::class.java).findAll()
            val persons = ArrayList<Person>()
            result.forEach {
                persons.add(it.toPerson())
            }
            persistenceCallbackListener.onPersonsRetrieval(persons)
        } finally {
            realm.close()
        }
    }

    override fun retrieveAllDogs() {
        val realm = Realm.getDefaultInstance()

        try {
            val result = realm.where(DogDB::class.java).findAll()
            val dogs = ArrayList<Dog>()
            result.forEach {
                dogs.add(it.toDog())
            }
            persistenceCallbackListener.onDogsRetrieval(dogs)
        } finally {
            realm.close()
        }
    }

    override fun queryDatabase(query: String) {
        val realm = Realm.getDefaultInstance()

        try {
            val personsResult = realm.where(PersonDB::class.java)
                    .contains("name", query)
                    .findAll()

            val dogsResult = realm.where(DogDB::class.java)
                    .contains("name", query)
                    .findAll()

            val persons = ArrayList<Person>()
            val dogs = ArrayList<Dog>()

            personsResult.forEach {
                persons.add(it.toPerson())
            }
            dogsResult.forEach {
                dogs.add(it.toDog())
            }
            persistenceCallbackListener.onDatabaseQueryRetrieval(persons, dogs)
        } finally {
            realm.close()
        }
    }

    override fun updatePerson(person: Person) {
        val realm = Realm.getDefaultInstance()
        try {
            val personResultDB = realm.where(PersonDB::class.java)
                    .contains("name", person.name)
                    .findFirst()

            /*val dogResultDB = realm.where(DogDB::class.java)
                    .contains("name", person.dogs.last().name)
                    .findFirst()
            */
            personResultDB?.let {
                realm.run {
                    beginTransaction()
                    /*dogResultDB?.owners?.add(PersonDB(person.name))
                    personResultDB.dogs.add(DogDB(person.dogs.last().name))
                    insertOrUpdate(dogResultDB)*/
                    insertOrUpdate(it)
                    commitTransaction()
                }
            }

        } finally {
            realm.close()
        }
    }

    // endregion persistence

    // region extension functions

    private fun DogDB.toDog(): Dog {
        val dog = Dog()
        dog.name = this.name
        this.owners?.forEach {
            dog.owners?.add(Person(it.name))
        }
        return dog
    }

    private fun PersonDB.toPerson(): Person {
        val person = Person()
        person.name = this.name
        this.dogs.forEach {
            person.dogs.add(Dog(it.name))
        }
        return person
    }

    // endregion extension functions

}

