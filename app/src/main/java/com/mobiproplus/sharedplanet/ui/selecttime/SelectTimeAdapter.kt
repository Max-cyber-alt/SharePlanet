package com.mobiproplus.sharedplanet.ui.selecttime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobiproplus.sharedplanet.R
import com.mobiproplus.sharedplanet.data.model.NasaPhoto
import kotlinx.android.synthetic.main.time_item.view.*

class SelectTimeAdapter(val clickListener: NasaTimeListener) :
    RecyclerView.Adapter<SelectTimeAdapter.ItemVH>() {
    var data = listOf<NasaPhoto>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.time_item, parent, false)
        return ItemVH(view)
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        val item = data[position]
        holder.bind(item, clickListener)
    }

    override fun getItemCount() = data.size

    inner class ItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: NasaPhoto, clickListener: NasaTimeListener) = with(itemView) {
            timeText.text = item.date
            setOnClickListener { clickListener.onClick(item) }
        }
    }

    class NasaTimeListener(val clickListener: (imageUrl: String) -> Unit) {
        fun onClick(item: NasaPhoto) = clickListener(item.getImageUrl())
    }
}