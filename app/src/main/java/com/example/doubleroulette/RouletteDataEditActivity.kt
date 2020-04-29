package com.example.doubleroulette

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_roulette_data_edit.*

class RouletteDataEditActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roulette_data_edit)
        realm = Realm.getDefaultInstance()

        saveButton.setOnClickListener { view: View ->
            realm.executeTransaction { db: Realm ->
                val maxId = db.where<RouletteData>().max("id")
                val nextId = (maxId?.toLong() ?: 0L) + 1
                val rouletteData = db.createObject<RouletteData>(nextId)
                rouletteData.type = typeSwitch.isChecked
                rouletteData.label = editItemName.text.toString()
                rouletteData.colorHex = editItemColor.text.toString()

            }
            Snackbar.make(view, "Added!", Snackbar.LENGTH_SHORT)
                .setAction("Back") { finish() }
                .setTextColor(Color.YELLOW)
                .show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
