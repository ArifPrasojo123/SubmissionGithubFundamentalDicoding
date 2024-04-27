package com.dicoding.submissiongithub.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: FavoriteUser)

    @Delete
    suspend fun delete(item: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser WHERE username = :username ")
    fun getFavoriteUserByusername(username: String): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM FavoriteUser")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>

}