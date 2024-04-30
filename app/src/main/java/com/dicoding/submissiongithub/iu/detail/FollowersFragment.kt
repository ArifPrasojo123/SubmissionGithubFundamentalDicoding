package com.dicoding.submissiongithub.iu.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissiongithub.data.response.ItemsItem
import com.dicoding.submissiongithub.databinding.FragmentFollowersBinding
import com.dicoding.submissiongithub.iu.ViewModelFactory
import com.dicoding.submissiongithub.iu.list.UserAdapater


class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding

    private val viewModel by viewModels<FollowersViewModel>{
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val username = arguments?.getString("username")

        viewModel.findFollowers(username?: "")

        viewModel.followers.observe(viewLifecycleOwner) {consumerFollowers ->
            setFollowersData(consumerFollowers)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun setFollowersData(consumerFollowers: List<ItemsItem>) {
        val adapter = UserAdapater()
        adapter.submitList(consumerFollowers)
        binding.rvFollo.layoutManager = LinearLayoutManager (requireActivity())
        binding.rvFollo.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
