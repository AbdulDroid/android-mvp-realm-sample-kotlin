package de.zalabany.realm_test.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import de.zalabany.realm_test.R
import de.zalabany.realm_test.ui.presenters.IMainContract
import de.zalabany.realm_test.ui.presenters.MainPresenter
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

        mPresenter = MainPresenter(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_save -> mPresenter.saveData(et_person_name.text.toString(),
                    et_first_dog_name.text.toString(),
                    et_second_dog_name.text.toString())
            R.id.btn_load_persons -> mPresenter.getPersons()
            R.id.btn_load_dogs -> mPresenter.getDogs()
        }
    }

    override fun updateLayout(results: String) {
        txt_result.text = results
    }


}
