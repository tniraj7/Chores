package com.example.chores.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chores.R
import com.example.chores.data.ChoreListAdapter
import com.example.chores.data.ChoresDatabaseHandler
import com.example.chores.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private var dbHandler: ChoresDatabaseHandler? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = ChoresDatabaseHandler(this)
        progressDialog = ProgressDialog(this)

        checkDB()

        saveChoreButtonId.setOnClickListener {
            progressDialog?.setMessage("Saving ..")

            if (!TextUtils.isEmpty(enterChoreId.text.toString())
                && !TextUtils.isEmpty(assignedById.text.toString())
                && !TextUtils.isEmpty(assignToId.text.toString())
            ) {
                progressDialog?.show()

                var chore = Chore()
                chore.choreName = enterChoreId.text.toString()
                chore.assignedTo = assignToId.text.toString()
                chore.assignedBy = assignedById.text.toString()

                saveToDatabase(chore)
                progressDialog?.cancel()

                startActivity(Intent(this, ChoreListActivity::class.java))

            } else {
                Toast.makeText(this, "Please enter all chore details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun saveToDatabase(chore: Chore) {

        dbHandler!!.createChore(chore)
        Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show()
        Log.d("Saved successfully: ", chore?.choreName)
    }

    fun checkDB() {
        if(dbHandler!!.getChoresCount() > 0) {
            var intent = Intent(this, ChoreListActivity::class.java)
            startActivity(intent)
        }
    }
}
