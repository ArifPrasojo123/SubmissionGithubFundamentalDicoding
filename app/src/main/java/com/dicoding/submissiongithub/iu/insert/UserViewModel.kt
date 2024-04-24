package com.dicoding.submissiongithub.iu.insert

import androidx.lifecycle.ViewModel
import com.dicoding.submissiongithub.data.NewsRepository

class UserViewModel (private val newsRepository: NewsRepository) : ViewModel() {
    fun getHeadlinNews() = NewsRepository.getHeadlineNews()
}