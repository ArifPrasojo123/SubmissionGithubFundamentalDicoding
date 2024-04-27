package com.dicoding.submissiongithub.iu.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissiongithub.data.response.ItemsItem
import com.dicoding.submissiongithub.database.FavoriteUser
import com.dicoding.submissiongithub.databinding.ActivityFavoriteBinding
import com.dicoding.submissiongithub.iu.ViewModelFactory
import com.dicoding.submissiongithub.iu.list.UserAdapater

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapater

    private val viewModel by viewModels<FavoriteUserViewModel>(){
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        adapter = UserAdapater()

        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = adapter

        viewModel.getFavoriteUser().observe(this) { users: List<FavoriteUser> ->
            val items = arrayListOf<ItemsItem>()
            users.map{
                val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            adapter.submitList(items)
        }
    }
}