package com.example.chores.model

import java.text.DateFormat
import java.util.*

class Chore() {

    var choreName: String? = null
    var assignedBy: String? = null
    var assignedTo: String? = null
    var timeAssigned: Long? = null
    var choreId: Int? = null

    constructor(choreName: String, assignedBy: String, assignedTo: String, timeAssigned: Long, choreId: Int): this() {
        this.choreName = choreName
        this.assignedBy = assignedBy
        this.assignedTo = assignedTo
        this.timeAssigned = timeAssigned
        this.choreId = choreId
    }

    fun showHumanReadableDate(timeAssigned: Long): String {

        var dateFormat: java.text.DateFormat = DateFormat.getDateInstance()
        var formattedDate = dateFormat.format(Date(timeAssigned).time)

        return  " Created At: ${formattedDate}"
    }

    override fun toString(): String {
        return "Chore(choreName: $choreName, assignedBy: $assignedBy, assignedTo: $assignedTo, timeAssigned: $timeAssigned, id: $choreId )"
    }

}