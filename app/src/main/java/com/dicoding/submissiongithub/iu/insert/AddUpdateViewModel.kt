package com.dicoding.submissiongithub.iu.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.submissiongithub.database.FavoriteUser
import com.dicoding.submissiongithub.repository.Repository

class AddUpdateViewModel(application: Application) : ViewModel() {
    private val mRepository: Repository = Repository(application)

    fun insert(favoriteUser: FavoriteUser) {
        mRepository.insert(favoriteUser)
    }

    fun update(favoriteUser: FavoriteUser) {
        mRepository.update(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser) {
        mRepository.delete(favoriteUser)
    }
}