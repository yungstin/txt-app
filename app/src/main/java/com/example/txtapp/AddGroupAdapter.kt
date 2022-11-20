package com.example.txtapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.userlayout.view.*

class AddGroupAdapter(private val context: Context, private val myList: ArrayList<String>): RecyclerView.Adapter<AddGroupAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGruop: ViewGroup, viewType: Int): ViewHolder {
        val myListItem = LayoutInflater.from(context).inflate(R.layout.group_entry_journals, viewGruop, false )
        return ViewHolder(myListItem)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(myList[position])
    }

    override fun getItemCount(): Int {
        return myList.count()
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(myItem: String)
        {
            itemView.tv_title.text = myItem
            itemView.setOnClickListener{
                Toast.makeText(context, myItem, Toast.LENGTH_LONG).show()
            }
        }

    }

}