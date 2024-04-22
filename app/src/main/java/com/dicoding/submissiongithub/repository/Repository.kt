package com.dicoding.submissiongithub.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.submissiongithub.database.FavoriteUser
import com.dicoding.submissiongithub.database.UserDao
import com.dicoding.submissiongithub.database.UserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(application: Application) {
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getAllNotes(): LiveData<List<FavoriteUser>> = mUserDao.getAllNotes()

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mUserDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute { mUserDao.delete(favoriteUser) }
    }

    fun update(favoriteUser: FavoriteUser) {
        executorService.execute { mUserDao.update(favoriteUser) }
    }
}