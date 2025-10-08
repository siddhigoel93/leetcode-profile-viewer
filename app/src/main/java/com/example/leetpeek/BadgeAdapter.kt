package com.example.leetpeek

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.leetpeek.dataClasses.Badge

class BadgeAdapter(
    val BadgeList: List<Badge>
) : RecyclerView.Adapter<BadgeAdapter.viewHolder>() {

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bName: TextView = itemView.findViewById(R.id.badgeName)
        val bDate: TextView = itemView.findViewById(R.id.date)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.badge_item, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val badge = BadgeList[position]
        holder.bName.text = badge.displayName
        holder.bDate.text = "Created on: ${badge.creationDate}"
    }

    override fun getItemCount(): Int {
        return BadgeList.size
    }
}