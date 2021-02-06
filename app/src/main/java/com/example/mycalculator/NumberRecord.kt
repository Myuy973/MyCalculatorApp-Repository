package com.example.mycalculator

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class NumberRecord : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var formula: String = ""
    var sum: Float = 0f
}