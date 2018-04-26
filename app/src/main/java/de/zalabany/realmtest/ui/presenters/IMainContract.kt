package de.zalabany.realmtest.ui.presenters

/**
 * This interface provides a contract that describes the communication between the MainPresenter and the MainView
 * The design of two inner interfaces is proposed by google in the Android Architecture Repository:
 * https://github.com/googlesamples/android-architecture
 * This shows that the view method has no logic
 *
 */

interface IMainContract {

    interface IMainView {
        fun updateLayout(results: String)
    }

    interface IMainPresenter {
        fun saveData(personName: String, firstDogName: String, secondDogName: String)
        fun getAllPersons()
        fun getAllDogs()
        fun searchDatabase(query: String)
    }

}