package com.erraydin.mentalmath.screens.history

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erraydin.mentalmath.R
import com.erraydin.mentalmath.database.Score
import com.erraydin.mentalmath.utils.TextItemViewHolder

class ScoreAdapter : RecyclerView.Adapter<ScoreAdapter.ViewHolder>() {
    var data = listOf<Score>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.textViewDate.text = DateUtils.formatDateTime(
            holder.textViewDate.context,
            item.dateMilli,
            DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_DATE or
                    DateUtils.FORMAT_SHOW_YEAR
        )
        val scoreText = "Score: ${item.score}"
        holder.textViewScore.text = scoreText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_score, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewScore: TextView = itemView.findViewById(R.id.text_view_score)
        val textViewDate: TextView = itemView.findViewById(R.id.text_view_date)

    }
}