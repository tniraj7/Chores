package com.example.chores.data

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.chores.R
import com.example.chores.activity.ChoreListActivity
import com.example.chores.model.Chore
import kotlinx.android.synthetic.main.popup.view.*


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

    inner class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView), OnClickListener{

        var choreName = itemView.findViewById<TextView?>(R.id.listChoreName)
        var assignedBy = itemView.findViewById<TextView?>(R.id.listAssignedBy)
        var assignedTo = itemView.findViewById<TextView?>(R.id.listAssignedTo)
        var assignedDate = itemView.findViewById<TextView?>(R.id.listDate)

        var deleteBtn = itemView.findViewById<Button?>(R.id.listDeletebutton)
        var editBtn = itemView.findViewById<Button?>(R.id.listEditbutton)

        fun bindViews(chore: Chore) {
            choreName?.text = chore.choreName
            assignedBy?.text = chore.assignedBy
            assignedTo?.text = chore.assignedTo
            assignedDate?.text = chore.showHumanReadableDate(System.currentTimeMillis())

            deleteBtn?.setOnClickListener(this)
            editBtn?.setOnClickListener(this)

        }

        override fun onClick(v: View?) {

            var chore = list[adapterPosition]

            when(v!!.id) {
                deleteBtn!!.id -> {

                    deleteChore(chore.choreId!!)
                    list.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }

                editBtn!!.id -> {
                    editChore(chore)
                }
            }
        }

        fun deleteChore(id: Int) {
            var db: ChoresDatabaseHandler = ChoresDatabaseHandler(context)
            db.deleteChore(id)
        }

        fun editChore(chore: Chore) {

            var dialogBuilder: AlertDialog.Builder? = null
            var dialog: AlertDialog? = null
            var dbHandler: ChoresDatabaseHandler? = ChoresDatabaseHandler(context)

            var view = LayoutInflater.from(context).inflate(R.layout.popup, null)

            var choreName = view?.popupChoreId
            var choreAssignedBy = view?.popupAssignedById
            var assignedTo = view?.popupAssignToId
            var saveBtn = view?.popupSaveChoreButtonId

            dialogBuilder = AlertDialog.Builder(context).setView(view)
            dialog = dialogBuilder!!.create()
            dialog!!.show()

            saveBtn?.setOnClickListener{

                var name = choreName?.text.toString().trim()
                var aBy = choreAssignedBy?.text.toString().trim()
                var aTo = assignedTo?.text.toString().trim()

                if (!TextUtils.isEmpty(name)
                    && !TextUtils.isEmpty(aBy)
                    && !TextUtils.isEmpty(aTo)
                ) {
                    chore.choreName = name
                    chore.assignedBy = aBy
                    chore.assignedTo = aTo

                    dbHandler?.updateChore(chore)
                    notifyItemChanged(adapterPosition, chore)

                    dialog?.dismiss()

                } else {
                    Toast.makeText(context, "Please enter all chore details", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}