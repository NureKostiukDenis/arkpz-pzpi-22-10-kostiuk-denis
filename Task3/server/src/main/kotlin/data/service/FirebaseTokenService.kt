package org.anware.data.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FirebaseTokenService @Autowired constructor(
    val firebaseAuth: FirebaseAuth
) {

    fun getUidFromToken(token: String): String {
        val decodedToken: FirebaseToken = firebaseAuth.verifyIdToken(token)
        return decodedToken.uid
    }
}