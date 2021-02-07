package com.example.mycalculator

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class NumberRecord : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var date: Date = Date()
    var formula: String = ""
    var sum: Float = 0f
}