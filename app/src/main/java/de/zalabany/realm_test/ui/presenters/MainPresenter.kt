package de.zalabany.realm_test.ui.presenters

import de.zalabany.realm_test.models.Dog
import de.zalabany.realm_test.models.Person
import de.zalabany.realm_test.persistence.Persistence
import de.zalabany.realm_test.interfaces.PersistenceCallbackListener
import de.zalabany.realm_test.persistence.Storage
import de.zalabany.realm_test.persistence.models.DogDB
import de.zalabany.realm_test.persistence.models.PersonDB

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
        val person = Person(personName, listOf(Dog(firstDogName), Dog(secondDogName)))
        mStorage.sendToDatabase(person)
    }

    override fun getPersons() {
        mStorage.retrievePersons()
    }

    override fun getDogs() {
        mStorage.retrieveDogs()
    }

    // endregion IMainPresenter

    // region PersistenceCallbackListener
    override fun onPersonsRetrieval(result: List<PersonDB>) {
        var textResult = ""

        result.forEach{
            textResult += "Person Name: + ${it.name} + has dogs: ${it.dogs[0]?.name} and ${it.dogs[1]?.name} \n"
        }
        mView.updateLayout(textResult)
    }

    override fun onDogsRetrieval(result: List<DogDB>) {
        var textResult = ""

        result.forEach{
            textResult += "Dog Name: ${it.name} \n"
        }
        mView.updateLayout(textResult)
    }
    // endregion PersistenceCallbackListener

}