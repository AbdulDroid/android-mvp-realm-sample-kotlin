package de.zalabany.realmtest.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import de.zalabany.realmtest.R
import de.zalabany.realmtest.models.Dog
import de.zalabany.realmtest.models.Person
import de.zalabany.realmtest.ui.presenters.IRelationshipContract
import de.zalabany.realmtest.ui.presenters.RelationshipPresenter
import kotlinx.android.synthetic.main.activity_relationships_view.*


class RelationshipsView : AppCompatActivity(), IRelationshipContract.IRelationshipView, View.OnClickListener {
    private lateinit var mPresenter: IRelationshipContract.IRelationshipPresenter
    private lateinit var mSelectedPerson: Person
    private lateinit var mSelectedDog: Dog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relationships_view)

        mPresenter = RelationshipPresenter(this)

        btn_assign_owner.setOnClickListener(this)

    }

    override fun updatePersonsList(persons: List<Person>) {
        val personsList = ArrayList<String>()
        persons.forEach {
            it.name?.let { personsList.add(it) }
        }
        val personsAdapter = ArrayAdapter<String>(this, R.layout.item_person, personsList)

        list_persons.run {
            adapter = personsAdapter
            onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                setItemChecked(position, true)
                mSelectedPerson = persons[position]
            }
        }
    }

    override fun updateDogsList(dogs: List<Dog>) {
        val dogsList = ArrayList<String>()
        dogs.forEach {
            it.name?.let { dogsList.add(it) }
        }
        val dogsAdapter = ArrayAdapter<String>(this, R.layout.item_dog, dogsList)
        list_dogs.run {
            adapter = dogsAdapter
            onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                setItemChecked(position, true)
                mSelectedDog = dogs[position]
            }
        }
    }

    override fun toastResult(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_assign_owner ->
                if (::mSelectedPerson.isInitialized && ::mSelectedDog.isInitialized) {
                    mPresenter.connectPersonAndDog(mSelectedPerson, mSelectedDog)
                } else {
                    Toast.makeText(this, "Select a person and a dog", Toast.LENGTH_SHORT).show()
                }
        }
    }


}
