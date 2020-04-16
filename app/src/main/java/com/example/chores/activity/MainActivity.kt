package com.example.chores.activity

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.chores.R
import com.example.chores.data.ChoresDatabaseHandler
import com.example.chores.model.Chore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    var dbHandler: ChoresDatabaseHandler? = null
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHandler = ChoresDatabaseHandler(this)
        setContentView(R.layout.activity_main)
        progressDialog = ProgressDialog(this)

        saveChoreButtonId.setOnClickListener {
            progressDialog?.setMessage("Saving ..")
            progressDialog?.show()

            if (!TextUtils.isEmpty(enterChoreId.text.toString())
                && !TextUtils.isEmpty(assignedById.text.toString())
                && !TextUtils.isEmpty(assignToId.text.toString())
            ) {

                var chore = Chore()
                chore.choreName = enterChoreId.text.toString()
                chore.assignedTo = assignToId.text.toString()
                chore.assignedBy = assignedById.text.toString()

                saveToDatabase(chore)
                progressDialog?.cancel()

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
}
