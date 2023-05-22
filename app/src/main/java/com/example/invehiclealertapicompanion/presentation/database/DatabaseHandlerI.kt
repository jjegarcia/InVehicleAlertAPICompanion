package com.example.invehiclealertapicompanion.presentation.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

interface DatabaseHandlerI {
//    fun addDeviceData()
    fun getReference(): DatabaseReference
//    fun updateCoordinates()
//    fun setDeviceList(inputDeviceList: HashMap<String, DeviceData>?)
    fun getValueEventListener(): ValueEventListener
//    fun removeDevice()
    fun writeData()
}