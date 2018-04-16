package de.zalabany.realm_test.persistence.models

import io.realm.RealmObject

open class DogDB(
        var name: String? = null
//        var age: Int = 0
//        var ownerName: String? = null

) : RealmObject()