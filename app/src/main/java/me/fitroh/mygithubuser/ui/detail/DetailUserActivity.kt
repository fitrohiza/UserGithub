package me.fitroh.mygithubuser.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import me.fitroh.mygithubuser.R
import me.fitroh.mygithubuser.data.response.DetailUserResponse
import me.fitroh.mygithubuser.data.response.ItemsItem
import me.fitroh.mygithubuser.databinding.ActivityDetailUserBinding
import me.fitroh.mygithubuser.ui.followers.FollowersFragment
import me.fitroh.mygithubuser.ui.followers.SectionPageradapter
import me.fitroh.mygithubuser.ui.home.UserAdapter


class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    private var user: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getStringExtra("username")
        val stringUser = user.toString()

        supportActionBar?.apply {
            title = "$user"
            setDisplayHomeAsUpEnabled(true)
        }

        detailViewModel.getDetailUser(stringUser)

        detailViewModel.detailUser.observe(this) { detailUser ->
            setDetailUser(detailUser)
        }

        detailViewModel.listUser.observe(this){listUser->
            setUserData(listUser)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        setAdapter(stringUser)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FollowersFragment(stringUser, 0))
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

    private fun setAdapter(username: String) {
        val sectionPageradapter = SectionPageradapter(this)
        sectionPageradapter.username = username

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPageradapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setUserData(listUser: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(listUser)

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
