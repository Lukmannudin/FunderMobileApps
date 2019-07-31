package com.team.oleg.funder.nlogin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.team.oleg.funder.data.User
import com.team.oleg.funder.data.source.userdata.UserDataRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private var disposable: Disposable? = null

//    private val _users = MutableLiveData<User>()
//    val user: LiveData<User>
//        get() = _users

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    fun onClickLogin(user: User) {
        loadUsers(true, user)
    }

    fun loadUsers(forceUpdate: Boolean, user: User) {
        Log.d("testLogin:eoName = ",user.eoName)
        Log.d("testLogin:eoPassword = ",user.eoPassword)
        if (forceUpdate) {
            disposable = userDataRepository.login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        Log.d("testLogin", result.data[0].eoName)
                    },
                    { error ->
                        Log.d("testLogin Error", error.localizedMessage)
                        error.printStackTrace()
                    }
                )

        }
    }


    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }


}