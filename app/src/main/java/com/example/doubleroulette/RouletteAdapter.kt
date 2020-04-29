package com.example.doubleroulette

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class RouletteAdapter(data: OrderedRealmCollection<RouletteData>) : RealmRecyclerViewAdapter<RouletteData, RouletteAdapter.ViewHolder>(data, true) {

    init {
        setHasStableIds(true)
    }

    class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell) {
        val label: TextView = cell.findViewById(android.R.id.text1)
        val color: TextView = cell.findViewById(android.R.id.text2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouletteAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rouletteData: RouletteData? = getItem(position)
        holder.label.text = rouletteData?.label
        holder.color.text = rouletteData?.colorHex
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.id ?: 0
    }
}