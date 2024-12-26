package org.anware.data.repository

import org.anware.data.dto.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<UserModel, Int> {
    @Query("SELECT u FROM UserModel u WHERE u.uid = :uid")
    fun findByUid(@Param("uid") uid: String): UserModel?

    @Query("""
        SELECT uw.warehouse 
        FROM UserWarehouseModel uw 
        WHERE uw.user.uid = :uid
    """)
    fun findWarehouseByUserUid(@Param("uid") uid: String): WarehouseModel?
}

