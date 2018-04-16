package de.zalabany.realm_test.persistence.models

import io.realm.RealmList
import io.realm.RealmObject

open class PersonDB(

        var name: String? = null,
        var dogs: RealmList<DogDB> = RealmList()

) : RealmObject()