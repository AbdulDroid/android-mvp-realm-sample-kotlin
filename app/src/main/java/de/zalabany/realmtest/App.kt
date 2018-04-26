package de.zalabany.realmtest

import android.app.Application
import io.realm.Realm

/**
 * so far this class is used for Realm init
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}