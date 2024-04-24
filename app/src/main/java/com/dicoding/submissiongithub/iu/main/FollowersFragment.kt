package com.dicoding.submissiongithub.iu.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissiongithub.data.response.ItemsItem
import com.dicoding.submissiongithub.databinding.FragmentFollowersBinding
import com.dicoding.submissiongithub.adapter.UserAdapater
import com.dicoding.submissiongithub.data.Result


class FollowersFragment : Fragment() {

    private var tabName: String? = null

    private lateinit var binding: FragmentFollowersBinding

    private lateinit var viewModel: FollowersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tabName = arguments?.getString(ARG_TAB)

        viewModel = ViewModelProvider(this).get(FollowersViewModel::class.java)

        val username = arguments?.getString("username")

        viewModel.findFollowers(username?: "")

        viewModel.followers.observe(viewLifecycleOwner) {consumerFollowers ->
            setFollowersData(consumerFollowers)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        private val detailViewModel by viewModels <DetailViewModel>{
            ViewModelFactory.getInstance(requireActivity())
        }

        if (tabName == TAB_NEWS) {
            viewModel.getHeadlineNews().observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding?.progressBar?.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding?.progressBar?.visibility = View.GONE
                            val newsData = result.data
                            UserAdapater.submitList(newsData)
                        }
                        is Result.Error -> {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Terjadi Kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
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

    companion object {
        const val ARG_TAB = "tab_name"
        const val TAB_NEWS = "news"
        const val TAB_BOOKMARK = "bookmark"
    }
}