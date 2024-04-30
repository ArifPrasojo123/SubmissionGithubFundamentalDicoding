package com.dicoding.submissiongithub

import androidx.lifecycle.LiveData
import com.dicoding.submissiongithub.database.FavoriteUser
import com.dicoding.submissiongithub.database.SettingPreferences
import com.dicoding.submissiongithub.database.UserDao
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val UserDao: UserDao,
    private val settingPreferences: SettingPreferences) {

    suspend fun getFavoriteUserByusername(username: String): Boolean {
        return UserDao.isUserIsExist(username)
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
            userDao: UserDao,
            settingPreferences: SettingPreferences
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository( userDao, settingPreferences)
            }.also { instance = it }
    }
}
