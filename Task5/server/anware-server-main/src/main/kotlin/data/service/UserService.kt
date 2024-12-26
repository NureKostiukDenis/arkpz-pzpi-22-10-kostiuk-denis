package org.anware.data.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserRecord
import jakarta.persistence.EntityNotFoundException
import org.anware.core.exeptions.AccountAlreadyExistsException
import org.anware.data.dto.*
import org.anware.data.dto.firebase.FirebaseRealTimeDatabaseUserModel
import org.anware.data.repository.UserRepository
import org.anware.data.repository.UserWarehouseRepository
import org.anware.data.repository.WarehouseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserService @Autowired constructor(
    val firebaseAuth: FirebaseAuth,
    val realtimeDataBaseService: RealtimeDataBaseService,
    val userDatabaseRepository: UserRepository,
    val warehouseRepository: WarehouseRepository,
    val userWarehouseRepository: UserWarehouseRepository
) {

    fun create(emailId: String?, password: String?, name: String?) {

        val request = UserRecord.CreateRequest()
            .setEmail(emailId)
            .setPassword(password)
            .setEmailVerified(true)

        try {
            val userRecord = firebaseAuth.createUser(request)
            val uid = userRecord.uid

            val userData = FirebaseRealTimeDatabaseUserModel(name = name.orEmpty(), email = emailId.orEmpty())

            val savePath = "${RealtimeDataBaseService.USERS_PATH}/$uid"

            realtimeDataBaseService.saveData(savePath, userData) { success, errorMsg ->
                if (!success) {
                    throw RuntimeException("Failed to save user data to Realtime Database: $errorMsg")
                }
                val userDataBase = UserModel(id = null, uid = uid, role = UserRole.USER)
                userDatabaseRepository.save(userDataBase)
            }

        } catch (exception: FirebaseAuthException) {
            if (exception.message?.contains("EMAIL_EXISTS") == true) {
                throw AccountAlreadyExistsException("Account with given email-id already exists")
            }
            throw exception
        }
    }

    fun findUserByUID(firebaseUid: String): UserModel? {
        return userDatabaseRepository.findByUid(firebaseUid)
    }

    fun findWarehouseByUserUID(firebaseUid: String): WarehouseModel? {
        return userDatabaseRepository.findWarehouseByUserUid(firebaseUid)
    }

    fun attachUserToWarehouse(firebaseUid: String, warehouseId: Int) {
        val warehouse = warehouseRepository.findById(warehouseId).orElseThrow {
            EntityNotFoundException("Warehouse not found")
        }

        val user = userDatabaseRepository.findByUid(firebaseUid)
            ?: throw EntityNotFoundException("User with UID $firebaseUid not found")

        val warehouseUser = userWarehouseRepository.findById(UserWarehouseId(user.id!!, warehouseId))

        if (warehouseUser.isPresent){
            throw IllegalArgumentException("User is invalid")
        }

        val userWarehouse = UserWarehouseModel(
            id = UserWarehouseId(userId = user.id, warehouseId = warehouse.id!!),
            user = user,
            warehouse = warehouse,
        )

        userWarehouseRepository.save(userWarehouse)
        makeUserStaff(firebaseUid)
    }

    @Transactional
    fun detachUserFromWarehouse(firebaseUid: String, warehouseId: Int) {
        warehouseRepository.findById(warehouseId).orElseThrow {
            EntityNotFoundException("Warehouse not found")
        }

        val user = userDatabaseRepository.findByUid(firebaseUid)
            ?: throw EntityNotFoundException("User with UID $firebaseUid not found")

        val warehouseUser = userWarehouseRepository.findById(UserWarehouseId(user.id!!, warehouseId)).orElseThrow {
            throw IllegalArgumentException("User is invalid")
        }
        deletePermissions(user.uid!!)
        userWarehouseRepository.deleteById(warehouseUser.id)
        deletePermissions(firebaseUid)
    }

    fun makeUserAdmin(firebaseUid: String) {
        changeUserRole(firebaseUid, UserRole.ADMIN)
    }

    fun makeUserStaff(firebaseUid: String) {
        changeUserRole(firebaseUid, UserRole.STAFF)
    }

    fun deletePermissions(firebaseUid: String) {
        changeUserRole(firebaseUid, UserRole.USER)
    }

    fun changeUserRole(firebaseUid: String, role: UserRole){
        val user = userDatabaseRepository.findByUid(firebaseUid)
            ?: throw IllegalArgumentException("User with UID $firebaseUid not found")
        val updatedUser = user.copy(role = role)
        userDatabaseRepository.save(updatedUser)
    }
}

