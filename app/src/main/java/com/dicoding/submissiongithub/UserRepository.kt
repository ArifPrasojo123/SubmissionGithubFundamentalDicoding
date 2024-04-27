package com.dicoding.submissiongithub

import androidx.lifecycle.LiveData
import com.dicoding.submissiongithub.data.response.GithubResponse
import com.dicoding.submissiongithub.data.retrofit.ApiService
import com.dicoding.submissiongithub.database.FavoriteUser
import com.dicoding.submissiongithub.database.SettingPreferences
import com.dicoding.submissiongithub.database.UserDao
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val apiService: ApiService,
    private val UserDao: UserDao,
    private val settingPreferences: SettingPreferences) {

    suspend fun getListUser(query: String): GithubResponse {
        return true
    }

    fun getFavoriteUserByusername(username: String): LiveData<List<FavoriteUser>> {
        return UserDao.getFavoriteUserByusername(username)
    }

    suspend fun addFavorite(item: FavoriteUser) {
        UserDao.insert(item)
    }

    suspend fun deleteFavorite(item: FavoriteUser) {
        UserDao.delete(item)
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>> {
        return UserDao.getFavoriteUser()
    }

    fun getThemeSettings(): Flow<Boolean> {
        return settingPreferences.getThemeSetting()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        settingPreferences.saveThemeSetting(isDarkModeActive)
    }

    companion object {
        private const val TAB = "UserRepository"

        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao,
            settingPreferences: SettingPreferences
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userDao, settingPreferences)
            }.also { instance = it }
    }
}