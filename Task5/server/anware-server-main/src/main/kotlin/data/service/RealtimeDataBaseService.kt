package org.anware.data.service

import com.google.firebase.database.*
import org.springframework.stereotype.Service

@Service
class RealtimeDataBaseService {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun saveData(path: String, data: Any, callback: (Boolean, String?) -> Unit) {
        val ref: DatabaseReference = database.child(path)
        ref.setValue(data, DatabaseReference.CompletionListener { databaseError, _ ->
            if (databaseError != null) {
                callback(false, databaseError.message)
            } else {
                callback(true, null)
            }
        })
    }

    fun getData(path: String, callback: (Any?) -> Unit) {
        database.child(path).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot.value)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read data: ${error.message}")
                callback(null)
            }
        })
    }

    companion object{
        const val USERS_PATH = "users"
    }
}