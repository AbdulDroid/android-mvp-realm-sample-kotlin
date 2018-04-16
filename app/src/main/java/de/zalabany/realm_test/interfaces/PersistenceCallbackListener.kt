package de.zalabany.realm_test.interfaces

import de.zalabany.realm_test.persistence.models.DogDB
import de.zalabany.realm_test.persistence.models.PersonDB

/**
 * in the case of database, this interface is an overkill
 * but it would be needed when we have a network model to take
 * the delay into account
 *
 */
interface PersistenceCallbackListener {
    fun onPersonsRetrieval(result: List<PersonDB>)
    fun onDogsRetrieval(result: List<DogDB>)
}