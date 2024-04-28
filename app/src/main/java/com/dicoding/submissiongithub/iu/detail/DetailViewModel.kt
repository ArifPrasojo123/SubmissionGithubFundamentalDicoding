package com.dicoding.submissiongithub.iu.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submissiongithub.UserRepository
import com.dicoding.submissiongithub.data.response.DetailUserResponse
import com.dicoding.submissiongithub.data.retrofit.ApiConfig
import com.dicoding.submissiongithub.database.FavoriteUser
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite


    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun findDetailUser(users: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username = users)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailUser.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun addFavoriteUser() {
        viewModelScope.launch {
            val user = FavoriteUser(
                username = _detailUser.value?.login.toString(),
                avatarUrl = _detailUser.value?.avatarUrl
            )
            userRepository.addFavorite(item = user)
        }
    }

    fun deleteFavoriteUser() {
        viewModelScope.launch {
            val user = FavoriteUser(
                username = _detailUser.value?.login.toString(),
                avatarUrl = _detailUser.value?.avatarUrl
            )
            userRepository.deleteFavorite(item = user)
        }
    }

    fun getFavoriteUserByUsername(username: String) {
        viewModelScope.launch {
            val mIsFavoriteUser = userRepository.getFavoriteUserByusername(username)
            _isFavorite.postValue(mIsFavoriteUser)
        }
    }
}