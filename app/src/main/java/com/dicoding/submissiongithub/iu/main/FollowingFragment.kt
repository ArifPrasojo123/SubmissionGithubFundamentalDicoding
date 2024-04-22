package com.dicoding.submissiongithub.iu.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissiongithub.data.response.ItemsItem
import com.dicoding.submissiongithub.databinding.FragmentFollowingBinding
import com.dicoding.submissiongithub.adapter.UserAdapater

class FollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowingBinding

    private lateinit var viewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)

        val username = arguments?.getString("username")

        viewModel.findFollowing(username?: "")

        viewModel.following.observe(viewLifecycleOwner) {consumerFollowing ->
            setFollowingsData(consumerFollowing)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setFollowingsData(consumerFollowing: List<ItemsItem>) {
        val adapter = UserAdapater()
        adapter.submitList(consumerFollowing)
        binding.rvFollow.layoutManager = LinearLayoutManager (requireActivity())
        binding.rvFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}