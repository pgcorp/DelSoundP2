package com.example.delsound.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserSessionViewModel : ViewModel() {

    // Private state — only visible to this ViewModel
    private val _isLoggedIn = MutableStateFlow(false)
    private val _userName = MutableStateFlow("")
    private val _userEmail = MutableStateFlow("")

    // Public state — exposed to the UI
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()
    val userName: StateFlow<String> = _userName.asStateFlow()
    val userEmail: StateFlow<String> = _userEmail.asStateFlow()

    // Called after successful login
    fun login(name: String, email: String) {
        _userName.value = name
        _userEmail.value = email
        _isLoggedIn.value = true
    }

    // Called when the user logs out
    fun logout() {
        _userName.value = ""
        _userEmail.value = ""
        _isLoggedIn.value = false
    }
}