package com.example.leetpeek.com.example.leetpeek

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.leetpeek.R
import com.example.leetpeek.TotalSubmissionNum

class SubmissionAdapter(
    val submissionList: List<TotalSubmissionNum>
) : RecyclerView.Adapter<SubmissionAdapter.viewHolder>() {

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val difficulty: TextView = itemView.findViewById(R.id.difficulty)
        val count: TextView = itemView.findViewById(R.id.count)
        val total: TextView = itemView.findViewById(R.id.total)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.submission_item, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val submission = submissionList[position]
        holder.difficulty.text = submission.difficulty
        holder.count.text = "Solved: ${submission.count}"
        holder.total.text = "Total Submissions: ${submission.submissions}"
    }

    override fun getItemCount(): Int {
        return submissionList.size
    }
}