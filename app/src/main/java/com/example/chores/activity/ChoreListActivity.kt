package com.example.chores.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chores.R
import com.example.chores.data.ChoreListAdapter
import com.example.chores.data.ChoresDatabaseHandler
import com.example.chores.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*
import kotlinx.android.synthetic.main.popup.view.*

class ChoreListActivity : AppCompatActivity() {

    private var adapter: ChoreListAdapter? = null
    private var choreList: ArrayList<Chore>? = null
    private var choreListItems: ArrayList<Chore>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var dialogBuilder: AlertDialog.Builder? = null
    private  var dialog: AlertDialog? = null

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
        choreList?.reverse()

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_menu_button) {
            Log.d("Item Clicekd", "Yes")
            createPopupDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    fun createPopupDialog() {

        var view = layoutInflater.inflate(R.layout.popup, null)

        var choreName = view.popupChoreId
        var choreAssignedBy = view.popupAssignedById
        var assignedTo = view.popupAssignToId
        var saveBtn = view.popupSaveChoreButtonId

        dialogBuilder = AlertDialog.Builder(this).setView(view)
        dialog = dialogBuilder!!.create()
        dialog!!.show()

        saveBtn.setOnClickListener{

            var name = choreName.text.toString().trim()
            var aBy = choreAssignedBy.text.toString().trim()
            var aTo = assignedTo.text.toString().trim()

            if (!TextUtils.isEmpty(name)
                && !TextUtils.isEmpty(aBy)
                && !TextUtils.isEmpty(aTo)
            ) {

                var chore = Chore()
                chore.choreName = name
                chore.assignedBy = aBy
                chore.assignedTo = aTo

                dbHandler?.createChore(chore)

                dialog?.dismiss()

                startActivity(Intent(this, ChoreListActivity::class.java))
                finish()

            } else {
                Toast.makeText(this, "Please enter all chore details", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
