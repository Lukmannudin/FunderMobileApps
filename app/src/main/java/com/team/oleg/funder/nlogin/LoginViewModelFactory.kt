package com.team.oleg.funder.nlogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.oleg.funder.data.source.userdata.UserDataRepository

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory (
    private val repository: UserDataRepository
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>) = LoginViewModel(repository) as T
}