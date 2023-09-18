package me.fitroh.mygithubuser.ui.followers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import me.fitroh.mygithubuser.databinding.FragmentFollowBinding
import me.fitroh.mygithubuser.ui.detail.DetailViewModel
import me.fitroh.mygithubuser.ui.home.UserAdapter


class FollowersFragment(userID: String, private var position: Int) : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private var username = userID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        when (position) {
            0 -> followersSetup()
            1 -> followingSetup()
        }
    }

    private fun followersSetup() {
        Toast.makeText(requireActivity(), "followers $username", Toast.LENGTH_SHORT).show()
        val adapter = UserAdapter()
        detailViewModel.getFollowers(username)
        detailViewModel.followers.observe(viewLifecycleOwner) { followerList ->
            adapter.submitList(followerList)
        }
        binding.rvFollow.adapter
    }

    private fun followingSetup() {
        Toast.makeText(requireActivity(), "following $username", Toast.LENGTH_SHORT).show()
        val adapter = UserAdapter()
        detailViewModel.getFollowing(username)
        detailViewModel.following.observe(viewLifecycleOwner) { followerList ->
            adapter.submitList(followerList)
        }
        binding.rvFollow.adapter
    }
}
