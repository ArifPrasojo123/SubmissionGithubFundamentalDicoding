package com.dicoding.submissiongithub.utils

import android.content.Context
import com.dicoding.submissiongithub.data.NewsRepository
import com.dicoding.submissiongithub.data.retrofit.ApiConfig
import com.dicoding.submissiongithub.database.UserDatabase

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getDatabase(context)
        val dao = database.userDao()
        val appExecutors = AppExecutors()
        return NewsRepository.getInstance(apiService, dao, appExecutors)
    }
}