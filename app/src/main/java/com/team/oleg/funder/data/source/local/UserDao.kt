package com.team.oleg.funder.data.source.local

import androidx.room.*
import com.team.oleg.funder.data.Sponsor

//@Dao
//interface UserDao {
//
//    @Query("SELECT * FROM Users")
//    fun getAllUsers(): List<Sponsor>
//
//    @Query("SELECT * FROM Users WHERE userid = :userId")
//    fun getUserById(userId: String): Sponsor?
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertUser(user: Sponsor)
//
//    @Update
//    fun updateUser(user: Sponsor): Int
//
//    @Query("DELETE FROM Users")
//    fun deleteUsers()
//
//    @Query("DELETE FROM Users WHERE userid = :userid")
//    fun deleteUserById(userid: String)
//}