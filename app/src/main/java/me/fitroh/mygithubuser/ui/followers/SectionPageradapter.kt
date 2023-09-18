package me.fitroh.mygithubuser.ui.followers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPageradapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    lateinit var username: String
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment(username, position)
            1 -> fragment = FollowersFragment(username, position)
        }
        return fragment as Fragment
    }
}