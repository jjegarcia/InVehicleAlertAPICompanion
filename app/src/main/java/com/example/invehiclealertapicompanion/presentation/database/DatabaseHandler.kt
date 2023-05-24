package com.example.invehiclealertapicompanion.presentation.database

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.getValue

private const val HEALTH_PATH = "health"
private const val TARGET_TOKEN =
    "fahO8zk-SYqnKcKyo7Ka4v:APA91bE2XJuW2KNrAqjyqZuMPQnEV_zFaG48aSVig2hboMUgyw0dAjaJ13tcPCMn9p0sPaYnR28oEaUn2s-uo_Cnowcd08n1JC2E8ZKEjb5exT73so-vR_SxN2cDM4lRfZoLieWXPMxX"

class DatabaseHandler() : DatabaseHandlerI {
    private val token = TARGET_TOKEN
    private val path = HEALTH_PATH
    private val database = Firebase.database
    private val healthRef = database.getReference(path)
    private var deviceList: java.util.HashMap<String, HealthData>? = null

    private var valueEventListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            deviceList =
                snapshot.getValue<HashMap<String, HealthData>>()
        }

        override fun onCancelled(error: DatabaseError) {
        }
    }

    override fun getReference() = healthRef

    override fun getValueEventListener(): ValueEventListener = valueEventListener

    override fun writeData() {
        if (!isTokenInList())
            healthRef.push().setValue(HealthData(TARGET_TOKEN))
    }

    private fun isTokenInList(): Boolean {
        if (deviceList == null) return false
        return deviceList?.map { entry -> entry.value.token }?.contains(token) ?: false
        }

    override fun updateHealthData(data: Double) {
        val key = findKey(token)
        if (key != null) {
            healthRef.child(key).child("pulse")
                .setValue(data)
            Log.d(TAG, "Updated DB Pulse:$data ")
            healthRef
        }
    }

    private fun findKey(token: String): String? {
        val list = deviceList?.filter { entry ->
            entry.value.token == token
        }?.keys?.toList()
        if (list != null) {
            if (list.isEmpty()) return null
            return list[0]
        }
        return null
    }

    init {
        healthRef.addValueEventListener(valueEventListener)
    }
}

data class HealthData(
    val token: String = "",
    val pulse: Double = 0.0
)