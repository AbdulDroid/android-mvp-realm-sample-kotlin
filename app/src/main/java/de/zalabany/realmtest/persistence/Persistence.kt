package de.zalabany.realmtest.persistence

import de.zalabany.realmtest.models.Person

interface Persistence {
    fun retrieveAllPersons()
    fun retrieveAllDogs()
    fun sendToDatabase(person: Person)
    fun queryDatabase(query: String)
    fun updatePerson(person: Person)
}