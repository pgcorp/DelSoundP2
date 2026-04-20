package com.example.delsound.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.delsound.ui.LoginViewModel
import com.example.delsound.ui.theme.DelSoundTheme
import kotlinx.coroutines.launch

@Composable
fun LoginContent(
    paddingValues: PaddingValues,
    email: String,
    onEmailChanged: (String) -> Unit,
    emailError: Boolean,
    password: String,
    onPasswordChanged: (String) -> Unit,
    passwordError: Boolean,
    rememberSession: Boolean,
    onRememberSession: (Boolean) -> Unit,
    onLoginClick: () -> Unit,
    onNavigationToRegister: () -> Unit
){


   // Column para organizar los elementos de la interfaz de usuario verticalmente
    Column( modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(paddingValues)
        .verticalScroll(rememberScrollState())
        .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "S",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary
        ) // fin del texto de bienvenida
        Text(
            text = "SoundIn",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {onEmailChanged(it)},
            label = { Text ( "Email Address" ) },
            isError = emailError,
            supportingText = {
                if (emailError){
                    Text( "Enter a valid email address" )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        ) // fin del campo de entrada de email
        //Aqui continua el codigo
        // Password field with visibility toggle
        var passwordVisible by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = password,
            onValueChange = { onPasswordChanged(it)},
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            isError = passwordError,
            supportingText = {
                if(passwordError){
                    Text(" Minimum 8 characters")// or any other error message
                }
            },
            visualTransformation = if (passwordVisible){
                VisualTransformation.None
            }else{
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible })
                {
                    Icon(
                        imageVector =
                            if (passwordVisible){
                                Icons.Default.Visibility
                            } else {
                                Icons.Default.VisibilityOff
                            },
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        // Switch for remember session
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Remember session",
                style = MaterialTheme.typography.bodyMedium
            )
            Switch(
                checked = rememberSession,
                onCheckedChange = { onRememberSession(it) }
            )
        }
        // Login button with validation
        Button(
            onClick = {
                onLoginClick()
            },

            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log In")
        }
        // Link to register
        TextButton(
            onClick = onNavigationToRegister
        ) {
            Text("Don't have an account? Register")
        }

    } // fin de la columna

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel() ,
    onNavigationToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
){
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

// We observe the StateFlow using collectAsStateWithLifecycle
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val rememberSession by viewModel.rememberSession.collectAsStateWithLifecycle()
    val emailError by viewModel.emailError.collectAsStateWithLifecycle()
    val passwordError by viewModel.passwordError.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
                TopAppBar(
                    title = { Text ( "SoundIn" )},
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                 },
        snackbarHost = {
            SnackbarHost (hostState  = snackBarHostState)
        }
    ) { paddingValues ->
        LoginContent(
            paddingValues = paddingValues,
            email = email,
            onEmailChanged = viewModel::onEmailChanged,
            emailError = emailError,
            password = password,
            onPasswordChanged = viewModel::onPasswordChanged,
            passwordError = passwordError,
            rememberSession = rememberSession,
            onRememberSession  = viewModel::onRememberSessionChanged,
            onLoginClick = {
                val isValid = viewModel.validateAndLogin()
                scope.launch {
                    if (isValid) {
                        //snackBarHostState.showSnackbar( message = "Welcome to SoundIn" )
                        onLoginSuccess()
                    } else {
                        snackBarHostState.showSnackbar(
                            message = "Please review the marked fields"
                        )
                    }
                }
            },
            onNavigationToRegister = onNavigationToRegister
        )
    }

}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview(){
    DelSoundTheme {
        LoginContent(
            paddingValues = PaddingValues(0.dp),
            email = "",
            onEmailChanged = {},
            emailError = false,
            password = "",
            onPasswordChanged = {_: String ->},
            passwordError = false,
            rememberSession = false,
            onRememberSession = {_: Boolean ->},
            onLoginClick = {},
            onNavigationToRegister = {}
        )
    }
}