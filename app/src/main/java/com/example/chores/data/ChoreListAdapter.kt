package com.example.chores.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chores.R
import com.example.chores.model.Chore


class ChoreListAdapter(private val context: Context,
                       private val list : ArrayList<Chore>) : RecyclerView.Adapter<ChoreListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var  view = LayoutInflater.from(context).inflate(R.layout.chore_list_row, parent,false)

        return ViewHolder(view, context)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bindViews(list[position])
    }

    inner class ViewHolder(itemView: View, context: Context): RecyclerView.ViewHolder(itemView) {

        var choreName = itemView.findViewById<TextView?>(R.id.listChoreName)
        var assignedBy = itemView.findViewById<TextView?>(R.id.listAssignedBy)
        var assignedTo = itemView.findViewById<TextView?>(R.id.listAssignedTo)
        var assignedDate = itemView.findViewById<TextView?>(R.id.listDate)

        fun bindViews(chore: Chore) {
            choreName?.text = chore.choreName
            assignedBy?.text = chore.assignedBy
            assignedTo?.text = chore.assignedTo
            assignedDate?.text = chore.showHumanReadableDate(System.currentTimeMillis())

        }
    }


}