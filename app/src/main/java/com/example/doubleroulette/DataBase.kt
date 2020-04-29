package com.example.doubleroulette

import android.app.Application
import io.realm.Realm

class DataBase : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}