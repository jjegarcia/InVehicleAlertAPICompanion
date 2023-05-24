package com.example.invehiclealertapicompanion.presentation.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

interface DatabaseHandlerI {
    fun getReference(): DatabaseReference
    fun getValueEventListener(): ValueEventListener
    fun writeData()
    fun updateHealthData(data: Double)
}