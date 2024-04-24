package com.dicoding.submissiongithub.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.submissiongithub.data.response.DetailUserResponse
import com.dicoding.submissiongithub.data.retrofit.ApiService
import com.dicoding.submissiongithub.database.FavoriteUser
import com.dicoding.submissiongithub.database.UserDao
import com.dicoding.submissiongithub.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val appExecutors: AppExecutors

) {
    private val result = MediatorLiveData<Result<List<FavoriteUser>>>()

    fun getHeadlineNews(users: String): LiveData<Result<List<FavoriteUser>>> {
        result.value = Result.Loading
        val client = apiService.getUserDetail(username = users)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    val detailUserResponse = response.body()
                    detailUserResponse?.let { detailResponse ->
                        val favoriteList = ArrayList<FavoriteUser>()
                        appExecutors.diskIO.execute {
                            detailResponse.avatarUrl?.let { avatarUrl ->
                                val isBookmarked = UserDao.isUserBookmarked(avatarUrl)
                                val favorite = FavoriteUser(
                                    username = "",
                                    avatarUrl = avatarUrl,
                                    isBookmarked = isBookmarked
                                )
                                favoriteList.add(favorite)
                            }
                            userDao.deleteAll()
                            userDao.insertNews(favoriteList)
                        }
                    }
                }
            }


            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })

        val localData = UserDao.getNews()
        result.addSource(localData) { newData: List<FavoriteUser> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao,
            appExecutors: AppExecutors
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService,userDao, appExecutors)
            }.also { instance = it }
    }
}