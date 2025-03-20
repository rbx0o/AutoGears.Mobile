package com.example.autogears.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autogears.services.Supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState
    var emailUser by mutableStateOf("")
    var passwordUser by mutableStateOf("")

    fun onSignInEmailPassword() {
        viewModelScope.launch {
            try {
                _userState.value = UserState.Loading
                val user = Supabase.supabase.auth.signInWith(Email) {
                    email = emailUser
                    password = passwordUser
                }
                _userState.value = UserState.Success("True")
                Log.d("AuthViewModel Success", "${user}")
            } catch (e: Exception) {
                _userState.value = UserState.Error(e.message!!)
                Log.d("AuthViewModel", "${e.message}")
            }
        }
    }
}