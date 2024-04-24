package com.dicoding.submissiongithub.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissiongithub.data.response.ItemsItem
import com.dicoding.submissiongithub.databinding.ItemUserBinding
import com.dicoding.submissiongithub.iu.main.DetailActivity

class UserAdapater : ListAdapter<ItemsItem, UserAdapater.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder (val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: ItemsItem) {
            binding.tvuser.text = "${user.login}\n-"
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.ivItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("username", user.login)
            holder.itemView.context.startActivity(intent)
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}