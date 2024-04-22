package com.dicoding.submissiongithub.iu.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.submissiongithub.R
import com.dicoding.submissiongithub.data.response.DetailUserResponse
import com.dicoding.submissiongithub.databinding.ActivityDetailBinding
import com.dicoding.submissiongithub.adapter.SectionPageAdapter
import com.dicoding.submissiongithub.adapter.UserAdapater
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: UserAdapater

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java)

        val username = intent.getStringExtra("username")

        val sectionsPagerAdapter = SectionPageAdapter(this, username?: "")
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tabs, position ->
            tabs.text = resources.getString((TAB_TITLES[position]))
        }.attach()

        supportActionBar?.hide()


        detailViewModel.findDetailUser(username ?: "")

        detailViewModel.detailUser.observe(this) {detailUserRespone ->
            setDetailData(detailUserRespone)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setDetailData(detailUserResponse: DetailUserResponse) {
        binding.tvUsername.text = detailUserResponse.login
        binding.tvName.text = detailUserResponse.name
        binding.tvFollowers.text = "Followers: ${detailUserResponse.followers}"
        binding.tvFollowing.text = "Following: ${detailUserResponse.following}"
        Glide.with(this)
            .load(detailUserResponse.avatarUrl)
            .into(binding.ivDetail)
    }

    private fun showLoading(isLoading: Boolean) {
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}