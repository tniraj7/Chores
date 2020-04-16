package com.example.chores.model

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
}