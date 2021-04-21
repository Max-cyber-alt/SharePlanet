package com.mobiproplus.sharedplanet.ui.selectday

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mobiproplus.sharedplanet.R
import com.mobiproplus.sharedplanet.data.model.NasaDate
import kotlinx.android.synthetic.main.day_item.view.*

class SelectDayAdapter : RecyclerView.Adapter<SelectDayAdapter.ItemVH>() {
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
        holder.bind(item)
    }

    override fun getItemCount() = data.size

    inner class ItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: NasaDate) = with(itemView) {
            dateTxt.text = item.date
            setOnClickListener {
                val dateAction = SelectDayFragmentDirections.actionMainFragmentToSelectPhotoFragment(item.date)
                it.findNavController().navigate(dateAction)
            }
        }
    }
}