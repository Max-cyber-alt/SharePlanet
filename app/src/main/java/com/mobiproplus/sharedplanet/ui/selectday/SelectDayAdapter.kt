package com.mobiproplus.sharedplanet.ui.selectday

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobiproplus.sharedplanet.R
import com.mobiproplus.sharedplanet.data.model.NasaDate
import kotlinx.android.synthetic.main.day_item.view.*

class SelectDayAdapter(val clickListener: NasaDateListener) :
    RecyclerView.Adapter<SelectDayAdapter.ItemVH>() {
    var data = listOf<NasaDate>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.day_item, parent, false)
        return ItemVH(view)
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        val item = data[position]
        holder.bind(item, clickListener)
    }

    override fun getItemCount() = data.size

    inner class ItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: NasaDate, clickListener: NasaDateListener) = with(itemView) {
            dateTxt.text = item.date
            setOnClickListener { clickListener.onClick(item) }
        }
    }

    class NasaDateListener(val clickListener: (itemDate: String) -> Unit) {
        fun onClick(item: NasaDate) = clickListener(item.date)
    }
}