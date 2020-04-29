package com.example.doubleroulette

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RouletteData : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var type: Boolean = false
    var label: String = "item"
    var colorHex: String = "FF0000"
}