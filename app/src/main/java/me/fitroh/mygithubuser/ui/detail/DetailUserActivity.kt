package me.fitroh.mygithubuser.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import me.fitroh.mygithubuser.R
import me.fitroh.mygithubuser.data.response.DetailUserResponse
import me.fitroh.mygithubuser.databinding.ActivityDetailUserBinding
import me.fitroh.mygithubuser.ui.followers.FollowersFragment
import me.fitroh.mygithubuser.ui.followers.SectionPageradapter


class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    var user: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getStringExtra("username")

        supportActionBar?.apply {
            title = "${user}"
            setDisplayHomeAsUpEnabled(true)
        }

        detailViewModel.getDetailUser(user.toString())

        detailViewModel.detailUser.observe(this) { detailUser ->
            setDetailUser(detailUser)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val sectionPageradapter = SectionPageradapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPageradapter
        val tabs : TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager){tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
            Log.d("position", "${tab.text}")
        }.attach()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FollowersFragment())
            .commit()
    }


    private fun setDetailUser(username: DetailUserResponse) {
        binding.tvDetailUsername.text = username.login
        binding.tvRepo.text = username.publicRepos.toString()
        binding.tvFollowers.text = username.followers.toString()
        binding.tvFollowing.text = username.following.toString()
        Glide.with(this@DetailUserActivity)
            .load(username.avatarUrl)
            .into(binding.profileImage)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}
