package com.example.invehiclealertapicompanion.presentation.database

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private const val HEALTH_PATH = "health"

class DatabaseHandler() : DatabaseHandlerI {
    private val database = Firebase.database
    private val healthRef = database.getReference(HEALTH_PATH)

    private var valueEventListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
        }

        override fun onCancelled(error: DatabaseError) {
        }
    }

    override fun getReference() = healthRef

    override fun getValueEventListener(): ValueEventListener = valueEventListener

    override fun writeData() {
        healthRef.push().setValue("Testing from watch")
    }

    init {
        healthRef.addValueEventListener(valueEventListener)
    }
}