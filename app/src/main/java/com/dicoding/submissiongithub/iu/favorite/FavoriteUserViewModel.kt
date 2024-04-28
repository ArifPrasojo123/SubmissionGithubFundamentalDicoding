package com.dicoding.submissiongithub.iu.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissiongithub.UserRepository
import com.dicoding.submissiongithub.database.FavoriteUser

class FavoriteUserViewModel (private val userRepository: UserRepository) : ViewModel() {

    fun getFavoriteUser(): LiveData<List<FavoriteUser>> {
        return userRepository.getFavoriteUser()
    }
}
