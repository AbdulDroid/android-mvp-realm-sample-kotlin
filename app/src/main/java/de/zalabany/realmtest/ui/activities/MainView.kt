package de.zalabany.realmtest.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import de.zalabany.realmtest.R
import de.zalabany.realmtest.ui.presenters.IMainContract
import de.zalabany.realmtest.ui.presenters.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

/**
 * MainView creates a presenter from [IMainContract.IMainPresenter] interface and passes itself
 * in the form of an interface [IMainContract.IMainView] to allow communication back from the presenter
 */

class MainView : AppCompatActivity(), View.OnClickListener, IMainContract.IMainView {

    private lateinit var mPresenter: IMainContract.IMainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_load_persons.setOnClickListener(this)
        btn_load_dogs.setOnClickListener(this)
        btn_save.setOnClickListener(this)
        btn_search_database.setOnClickListener(this)
        btn_relationships.setOnClickListener(this)

        mPresenter = MainPresenter(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_save -> mPresenter.saveData(et_person_name.text.toString(),
                    et_first_dog_name.text.toString(),
                    et_second_dog_name.text.toString())

            R.id.btn_load_persons -> mPresenter.getAllPersons()
            R.id.btn_load_dogs -> mPresenter.getAllDogs()
            R.id.btn_search_database -> mPresenter.searchDatabase(et_search_query.text.toString())
            R.id.btn_relationships -> startRelationships()
        }
    }

    override fun updateLayout(results: String) {
        txt_result.text = results
    }

    private fun startRelationships() {
        val intent = Intent(this, RelationshipsView::class.java)
        startActivity(intent)
    }


}
