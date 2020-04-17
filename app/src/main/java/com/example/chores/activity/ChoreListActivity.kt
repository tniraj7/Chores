package com.example.chores.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chores.R
import com.example.chores.data.ChoreListAdapter
import com.example.chores.data.ChoresDatabaseHandler
import com.example.chores.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*

class ChoreListActivity : AppCompatActivity() {

    private var adapter: ChoreListAdapter? = null
    private var choreList: ArrayList<Chore>? = null
    private var choreListItems: ArrayList<Chore>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    private var dbHandler: ChoresDatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)

        dbHandler = ChoresDatabaseHandler(this)

        choreList = ArrayList<Chore>()
        choreListItems = ArrayList()
        layoutManager = LinearLayoutManager(this)
        adapter = ChoreListAdapter(this, choreListItems!!)
        choreList = dbHandler?.readChores()

        for(c in choreList!!.iterator()) {
            val chore = Chore()
            chore.choreName = "Chore: ${c.choreName}"
            chore.assignedBy = "Assigned By: ${c.assignedBy}"
            chore.assignedTo = "Assigned to: ${c.assignedTo}"
            chore.choreId = c.choreId
            chore.showHumanReadableDate(c.timeAssigned!!)

            choreListItems!!.add(chore)
        }

        adapter?.notifyDataSetChanged()

        recyclerViewId.layoutManager = layoutManager
        recyclerViewId.adapter = adapter


    }
}
