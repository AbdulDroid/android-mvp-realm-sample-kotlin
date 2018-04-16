package de.zalabany.realm_test.persistence

import de.zalabany.realm_test.models.Person
import de.zalabany.realm_test.interfaces.PersistenceCallbackListener
import de.zalabany.realm_test.persistence.models.DogDB
import de.zalabany.realm_test.persistence.models.PersonDB
import io.realm.Realm
import io.realm.RealmList

/**
 * This class provides the persistence logic and can be later defined as a singleton
 * It implements the [Persistence] interface which provides the database operations and sends
 * the results back to the MainPresenter in the callback listener [persistenceCallbackListener]
 * however, this callback listener is optional, and can be removed and replaced by having function returns
 */

class Storage(private val persistenceCallbackListener: PersistenceCallbackListener) : Persistence {

    override fun sendToDatabase(person: Person) {
        val realm = Realm.getDefaultInstance()

        // todo refactor
        val dogsDBList = RealmList(DogDB(person.dogs[0].name), DogDB(person.dogs[1].name))
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

    override fun retrievePersons() {
        val realm = Realm.getDefaultInstance()

        try {
            val result = realm.where(PersonDB::class.java).findAll()
            val list = ArrayList<PersonDB>()
            result.forEach {
                list.add(it)
            }
            persistenceCallbackListener.onPersonsRetrieval(list)
        } finally {
            realm.close()
        }
    }

    override fun retrieveDogs() {
        val realm = Realm.getDefaultInstance()

        try {
            val result = realm.where(DogDB::class.java).findAll()
            val list = ArrayList<DogDB>()
            result.forEach {
                list.add(it)
            }
            persistenceCallbackListener.onDogsRetrieval(list)
        } finally {
            realm.close()
        }
    }

}