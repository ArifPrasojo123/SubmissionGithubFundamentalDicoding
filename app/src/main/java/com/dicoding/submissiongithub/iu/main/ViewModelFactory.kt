package com.dicoding.submissiongithub.iu.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissiongithub.data.NewsRepository
import com.dicoding.submissiongithub.iu.insert.UserViewModel
import com.dicoding.submissiongithub.utils.Injection

class ViewModelFactory private constructor(private val newsRepository: NewsRepository) :
    ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(newsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class" + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}