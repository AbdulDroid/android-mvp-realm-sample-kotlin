package de.zalabany.realmtest.interfaces

import de.zalabany.realmtest.models.Dog
import de.zalabany.realmtest.models.Person

/**
 * in the case of database, this interface is an overkill
 * but it would be needed when we have a network model to take
 * the delay into account
 *
 */
interface PersistenceCallbackListener {
    fun onPersonsRetrieval(persons: List<Person>)
    fun onDogsRetrieval(dogs: List<Dog>)
    fun onDatabaseQueryRetrieval(personsResult: List<Person>, dogsResult: List<Dog>)
}