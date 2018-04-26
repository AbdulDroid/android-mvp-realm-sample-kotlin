package de.zalabany.realmtest.models

data class Person(var name: String? = "", var dogs: MutableList<Dog> = ArrayList())