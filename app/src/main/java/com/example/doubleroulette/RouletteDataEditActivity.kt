package com.example.doubleroulette

import android.content.res.ColorStateList
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

        val rouletteId = intent?.getLongExtra("roulette_id", -1L)
        if (rouletteId != -1L) {
            val rouletteData = realm.where<RouletteData>()
                .equalTo("id", rouletteId).findFirst()
            typeSwitch.isChecked = rouletteData?.type!!
            editItemName.setText(rouletteData?.label)
            editItemColor.setText(rouletteData?.colorHex)
            deleteButton.visibility = View.VISIBLE
        } else {
            deleteButton.visibility = View.INVISIBLE
        }
        colorButton.setBackgroundColor(Color.parseColor("#"+editItemColor.text.toString()))

        saveButton.setOnClickListener { view: View ->
            when (rouletteId) {
                //新しいセルの処理
                -1L -> {
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
                        .setTextColor(Color.WHITE)
                        .show()
                }
                else -> {
                    //既存のセルの処理
                    realm.executeTransaction { db: Realm ->
                        val rouletteData = db.where<RouletteData>()
                            .equalTo("id", rouletteId).findFirst()
                        rouletteData?.type = typeSwitch.isChecked
                        rouletteData?.label = editItemName.text.toString()
                        rouletteData?.colorHex = editItemColor.text.toString()
                    }
                    Snackbar.make(view, "Modified!", Snackbar.LENGTH_SHORT)
                        .setAction("Back") { finish() }
                        .setTextColor(Color.WHITE)
                        .show()
                }
            }
        }

        deleteButton.setOnClickListener { view: View ->
            realm.executeTransaction { db: Realm ->
                db.where<RouletteData>().equalTo("id", rouletteId)
                    ?.findFirst()
                    ?.deleteFromRealm()
            }
            Snackbar.make(view, "Deleted!", Snackbar.LENGTH_SHORT)
                .setAction("Back") { finish() }
                .setActionTextColor(Color.WHITE)
                .show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
