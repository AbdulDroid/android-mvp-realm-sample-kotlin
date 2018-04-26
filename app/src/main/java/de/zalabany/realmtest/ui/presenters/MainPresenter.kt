package de.zalabany.realmtest.ui.presenters

import android.content.Intent
import de.zalabany.realmtest.interfaces.PersistenceCallbackListener
import de.zalabany.realmtest.models.Dog
import de.zalabany.realmtest.models.Person
import de.zalabany.realmtest.persistence.Persistence
import de.zalabany.realmtest.persistence.Storage

/**
 * The main presenter communicates with both the MainView and the Storage class.
 *
 * (MainPresenter ---> Storage) communication done by [mStorage] object of type [Persistence] interface
 * (MainPresenter <--- Storage) communication done by [PersistenceCallbackListener] interface implementation
 *
 * (MainPresenter ---> MainView) communication done by [mView] object of type [IMainContract.IMainView] interface
 * (MainPresenter <--- MainView) communication done by [IMainContract.IMainPresenter] interface implementation
 *
 */
class MainPresenter(private val mView: IMainContract.IMainView) : IMainContract.IMainPresenter, PersistenceCallbackListener{
    private val mStorage: Persistence

    init {
        mStorage = Storage(this)
    }

    // region IMainPresenter
    override fun saveData(personName: String, firstDogName: String, secondDogName: String) {
        val person = Person(personName, mutableListOf(Dog(firstDogName), Dog(secondDogName)))
        mStorage.sendToDatabase(person)
    }

    override fun getAllPersons() {
        mStorage.retrieveAllPersons()
    }

    override fun getAllDogs() {
        mStorage.retrieveAllDogs()
    }

    override fun searchDatabase(query: String) {
        mStorage.queryDatabase(query)
    }

    // endregion IMainPresenter

    // region PersistenceCallbackListener

    override fun onPersonsRetrieval(persons: List<Person>) {
        mView.updateLayout(createStringFromPersonList(persons))
    }

    private fun createStringFromPersonList(result: List<Person>): String {
        var textResult1 = ""
        result.forEach {
            val dogsOfPerson = it.dogs
            textResult1 += "Person Name: ${it.name} has dogs: "
            dogsOfPerson.forEach {
                textResult1 += "${it.name}, \n"
            }
        }
        return textResult1
    }

    override fun onDogsRetrieval(dogs: List<Dog>) {
        mView.updateLayout(createStringFromDogsList(dogs))
    }

    private fun createStringFromDogsList(result: List<Dog>): String {
        var textResult = ""

        result.forEach {
            var owners = ""
            it.owners?.forEach {
                owners = owners + it.name + ", "
            }
            textResult += "Dog Name: ${it.name} whose owner is: $owners \n"
        }
        return textResult
    }


    override fun onDatabaseQueryRetrieval(personsResult: List<Person>, dogsResult: List<Dog>) {
        mView.updateLayout(createStringFromPersonList(personsResult) + createStringFromDogsList(dogsResult))
    }

    // endregion PersistenceCallbackListener
}
