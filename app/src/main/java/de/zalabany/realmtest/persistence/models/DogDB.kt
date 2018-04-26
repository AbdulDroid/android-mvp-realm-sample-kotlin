package de.zalabany.realmtest.persistence.models

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects

open class DogDB(
        var name: String? = null,

        @LinkingObjects("dogs")
        val owners: RealmResults<PersonDB>? = null

) : RealmObject()