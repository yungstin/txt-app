package com.example.txtapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    private var titles = arrayOf("this will be the read data")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.group_entry_journals, parent, false)
        return ViewHolder(v)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemtitle.text = titles[position]
    }

    override fun getItemCount(): Int {
        return titles.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val itemtitle: TextView
        init {
            itemtitle = itemView.findViewById(R.id.tv_title)
            itemView.setOnClickListener{
                val position: Int = adapterPosition

                Toast.makeText(itemView.context, "you clicked on ${titles[position]}", Toast.LENGTH_LONG).show()
            }
        }
    }
}