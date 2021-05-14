package com.erraydin.mentalmath.screens.history

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erraydin.mentalmath.R
import com.erraydin.mentalmath.database.Score
import com.erraydin.mentalmath.utils.TextItemViewHolder

class ScoreAdapter: RecyclerView.Adapter<TextItemViewHolder>() {
    var data = listOf<Score>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.score.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.score_item, parent, false) as TextView
        return TextItemViewHolder(view)
    }
}