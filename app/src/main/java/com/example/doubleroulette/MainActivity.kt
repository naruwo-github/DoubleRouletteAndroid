package com.example.doubleroulette

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.kotlin.where

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        realm = Realm.getDefaultInstance()
        rouletteList.layoutManager = LinearLayoutManager(this)
        val rouletteData = realm.where<RouletteData>().findAll()
        val adapter = RouletteAdapter(rouletteData)
        rouletteList.adapter = adapter

        addButton.setOnClickListener { view ->
            val intent = Intent(this, RouletteDataEditActivity::class.java)
            startActivity(intent)
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
        }

        playButton.setOnClickListener {view ->
            val intent = Intent(this, RouletteActivity::class.java)
            startActivity(intent)
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        }

        //セルをタップしたときのコールバック処理を追加
        adapter.setOnItemClickListener { id ->
            //idをRouletteDataEditActivityに渡していると言うこと
            val intent = Intent(this, RouletteDataEditActivity::class.java)
                .putExtra("roulette_id", id)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
