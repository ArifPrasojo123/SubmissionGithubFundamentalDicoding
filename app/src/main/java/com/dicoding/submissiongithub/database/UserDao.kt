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
    fun insert(item: FavoriteUser)

    @Delete
    fun delete(item: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT EXISTS(SELECT * FROM FavoriteUser WHERE username = :username)")
    suspend fun isUserIsExist(username : String) : Boolean

}