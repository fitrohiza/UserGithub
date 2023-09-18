package me.fitroh.mygithubuser.ui.home

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.fitroh.mygithubuser.data.response.GithubResponse
import me.fitroh.mygithubuser.data.response.ItemsItem
import me.fitroh.mygithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    companion object{
        private const val TAG = "MainViewModel"
        private const val USER_QUERY = "fitroh"
    }

    init{
        getUser(USER_QUERY)
    }

    private fun getUser(searchData : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUsers(searchData)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                Log.d(TAG, "${response.body()}")
                if (response.isSuccessful) {
                    if (response.isSuccessful) {
                        _listUser.value = response.body()?.items as List<ItemsItem>?
                    }
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun findGithubUser(query : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUsers(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val userList = response.body()?.items
                    if (userList.isNullOrEmpty()) {
                        _toastMessage.postValue("User tidak ditemukan")
                    } else {
                        _listUser.value = userList as List<ItemsItem>?
                    }
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })

    }
}