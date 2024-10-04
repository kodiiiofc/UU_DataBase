package com.kodiiiofc.urbanuniversity.database

data class Person(val name: String, val occupation: String, val phone: String) {
    override fun toString(): String {
        return "$name, $occupation, $phone"
    }
}
