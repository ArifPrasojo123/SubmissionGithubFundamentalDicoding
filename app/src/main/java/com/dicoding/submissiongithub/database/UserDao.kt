package com.dicoding.submissiongithub.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM favorite ORDER BY avatarUrl DESC")
    fun getNews(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM favorite WHERE bookmarked = 1")

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNews(favorite: List<FavoriteUser>)

    @Update
    fun updateNews(favorite: FavoriteUser)

    @Query("DELETE FROM favorite WHERE bookmarked = 0")
    fun deleteAll()

    @Query("SELECT * FROM favorite WHERE username = :username ")
    fun getFavoriteUserByusername(username: String): LiveData<List<FavoriteUser>>

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE username = :username AND bookmarked = 1)")
    fun isUserBookmarked(username: String): Boolean
}