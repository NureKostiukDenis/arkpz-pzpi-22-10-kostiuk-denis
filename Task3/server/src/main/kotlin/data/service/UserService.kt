package org.anware.data.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class UserService @Autowired constructor(
    val firebaseAuth: FirebaseAuth
) {

    fun create(emailId: String?, password: String?) {
        val request = UserRecord.CreateRequest()
            .setEmail(emailId)
            .setPassword(password)
            .setEmailVerified(true)

        try {
            firebaseAuth.createUser(request)
        } catch (exception: FirebaseAuthException) {
            if (exception.message?.contains("EMAIL_EXISTS") == true) {
                throw AccountAlreadyExistsException("Account with given email-id already exists")
            }
            throw exception
        }
    }
}

class AccountAlreadyExistsException(message: String) : RuntimeException(message)

