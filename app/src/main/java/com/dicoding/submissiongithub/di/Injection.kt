package com.dicoding.submissiongithub.di

import android.content.Context
import com.dicoding.submissiongithub.UserRepository
import com.dicoding.submissiongithub.data.retrofit.ApiConfig
import com.dicoding.submissiongithub.database.SettingPreferences
import com.dicoding.submissiongithub.database.UserDatabase
import com.dicoding.submissiongithub.database.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        val pref = SettingPreferences.getInstance(context.dataStore)
        return UserRepository.getInstance( dao, pref)
    }
}