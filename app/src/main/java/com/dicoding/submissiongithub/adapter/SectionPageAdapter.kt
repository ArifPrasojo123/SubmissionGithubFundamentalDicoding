package com.dicoding.submissiongithub.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.submissiongithub.iu.main.DetailActivity
import com.dicoding.submissiongithub.iu.main.FollowersFragment
import com.dicoding.submissiongithub.iu.main.FollowingFragment

class SectionPageAdapter (activity: DetailActivity, private val username: String) : FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        val mBundle = Bundle()
        mBundle.putString("username", username)
        fragment?.arguments = mBundle
        return fragment as Fragment
    }
}