   package com.example.leetpeek

    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView
    import com.example.leetpeek.dataClasses.Submission

   class SolvedAdapter(
        var solvedList: List<Submission>
    ) : RecyclerView.Adapter<SolvedAdapter.viewHolder>() {

        class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.ques)
            val lang: TextView = itemView.findViewById(R.id.lang)
            val status: TextView = itemView.findViewById(R.id.status)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.ques_card, parent, false)
            return viewHolder(view)
        }

        override fun onBindViewHolder(holder: viewHolder, position: Int) {
            val solved = solvedList[position]
            holder.title.text = solved.title
            holder.lang.text = solved.lang
            holder.status.text = solved.statusDisplay
        }

        override fun getItemCount(): Int {
            return solvedList.size
        }
    }
