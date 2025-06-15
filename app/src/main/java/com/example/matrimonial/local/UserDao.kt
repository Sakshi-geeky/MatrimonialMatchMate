package com.example.matrimonial.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<UserEntity>

    @Query("DELETE FROM users")
    suspend fun clearAll()

    @Query("UPDATE users SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Int, status: String)

    @Query("SELECT status FROM users WHERE id = :userId")
    suspend fun getStatusById(userId: Int): String?
}

