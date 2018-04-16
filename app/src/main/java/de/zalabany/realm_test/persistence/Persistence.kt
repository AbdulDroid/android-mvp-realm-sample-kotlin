package de.zalabany.realm_test.persistence

import de.zalabany.realm_test.models.Person

interface Persistence {
    fun retrievePersons()
    fun retrieveDogs()
    fun sendToDatabase(person: Person)
}