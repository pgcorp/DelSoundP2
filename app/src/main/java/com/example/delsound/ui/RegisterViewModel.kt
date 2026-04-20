package com.example.delsound.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel : ViewModel() {
    // Private MutableStateFlow — only the ViewModel can read and write
    private val _name = MutableStateFlow("")
    private val _nameError = MutableStateFlow(false)
    private val _email = MutableStateFlow("")
    private val _emailError = MutableStateFlow(false)
    private val _password = MutableStateFlow("")
    private val _passwordError = MutableStateFlow(false)
    private val _passwordConfirm = MutableStateFlow("")
    private val _passwordConfirmError = MutableStateFlow(false)
    private val _birthDate = MutableStateFlow("")
    private val _birthDateError = MutableStateFlow(false)
    private val _genre = MutableStateFlow("")
    private val _genreError = MutableStateFlow(false)
    private val _acceptedTerms = MutableStateFlow(false)
    private val _acceptedTermsError = MutableStateFlow(false)

    // Read-only StateFlow exposed to the UI — ViewModel controls the value through _name
    val name: StateFlow<String> = _name.asStateFlow()
    val nameError: StateFlow<Boolean> = _nameError.asStateFlow()
    val email: StateFlow<String> = _email.asStateFlow()
    val emailError: StateFlow<Boolean> = _emailError.asStateFlow()
    val password: StateFlow<String> = _password.asStateFlow()
    val passwordError: StateFlow<Boolean> = _passwordError.asStateFlow()
    val passwordConfirm: StateFlow<String> = _passwordConfirm.asStateFlow()
    val passwordConfirmError: StateFlow<Boolean> = _passwordConfirmError.asStateFlow()
    val birthDate: StateFlow<String> = _birthDate.asStateFlow()
    val birthDateError: StateFlow<Boolean> = _birthDateError.asStateFlow()
    val genre: StateFlow<String> = _genre.asStateFlow()
    val genreError: StateFlow<Boolean> = _genreError.asStateFlow()
    val acceptedTerms: StateFlow<Boolean> = _acceptedTerms.asStateFlow()
    val acceptedTermsError: StateFlow<Boolean> = _acceptedTermsError.asStateFlow()


    // Public functions — called by the UI to update the ViewModel state
    fun onNameChanged(newName: String) { _name.value = newName }
    fun onEmailChanged(newEmail: String) { _email.value = newEmail }
    fun onPasswordChanged(newPassword: String) { _password.value = newPassword }
    fun onPasswordConfirmChanged(newPasswordConfirm: String) { _passwordConfirm.value = newPasswordConfirm }
    fun onBirthDateChanged(newBirthDate: String) { _birthDate.value = newBirthDate }
    fun onGenreChanged(newGenre: String) { _genre.value = newGenre }
    fun onAcceptedTermsChanged(newAcceptedTerms: Boolean) { _acceptedTerms.value = newAcceptedTerms }

    // Function to validate the form and create the account
    fun validateAndRegister() : Boolean{
        _nameError.value = _name.value.isBlank()
        _emailError.value = _email.value.isBlank() || !_email.value.contains("@") || !_email.value.contains(".")
        _passwordError.value = _password.value.isBlank() || _password.value.length < 8
        _passwordConfirmError.value = _passwordConfirm.value.isBlank() || _passwordConfirm.value != _password.value
        _birthDateError.value = _birthDate.value.isBlank()
        _genreError.value = _genre.value.isBlank()
        _acceptedTermsError.value = !_acceptedTerms.value


        return ( !_nameError.value && !_emailError.value && !_passwordError.value
                && !_passwordConfirmError.value && !_birthDateError.value && !_genreError.value )
                && acceptedTerms.value
    }

}

