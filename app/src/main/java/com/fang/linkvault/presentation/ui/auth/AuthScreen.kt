package com.fang.linkvault.presentation.ui.auth

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fang.linkvault.R

@Composable
fun AuthScreen(
    onNavigateToHome: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var isLoginMode by remember { mutableStateOf(true) }
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        Log.d("AuthScreen", "Started collecting events")
        viewModel.events.collect { event ->
            Log.d("AuthScreen", "Received event: $event")
            when (event) {
                is AuthUiEvent.NavigateToHome -> {
                    Log.d("AuthScreen", "Navigating to home")
                    onNavigateToHome()
                }
            }
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0B0C0D),
                            Color(0xFF161C1F),
                            Color(0xFF192023),
                            Color(0xFF232A2E),
                            Color(0xFF1A2125)
                        )
                    )
                )
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "App logo",
                    modifier = Modifier
                        .size(120.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = CircleShape,
                            clip = false,
                            ambientColor = Color.Cyan,
                            spotColor = Color.Cyan
                        )
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(19.dp))

                Text(
                    text = "LinkVault",
                    fontFamily = FontFamily.Monospace,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    ),
                    color = Color.White
                )

                Text(
                    text = "Secure your links, simplify your life",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Auth Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1F2629).copy(alpha = 0.95f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Toggle Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { isLoginMode = true },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isLoginMode)
                                        Color(0xFF00D9FF)
                                    else
                                        Color.Transparent,
                                    contentColor = if (isLoginMode)
                                        Color(0xFF0B0C0D)
                                    else
                                        Color(0xFF00D9FF)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "Login",
                                    fontWeight = if (isLoginMode) FontWeight.Bold else FontWeight.Normal
                                )
                            }

                            Button(
                                onClick = { isLoginMode = false },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (!isLoginMode)
                                        Color(0xFF00D9FF)
                                    else
                                        Color.Transparent,
                                    contentColor = if (!isLoginMode)
                                        Color(0xFF0B0C0D)
                                    else
                                        Color(0xFF00D9FF)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "Register",
                                    fontWeight = if (!isLoginMode) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        AnimatedContent(
                            targetState = isLoginMode,
                            transitionSpec = {
                                slideInHorizontally { width -> if (targetState) -width else width } togetherWith
                                        slideOutHorizontally { width -> if (targetState) width else -width }
                            },
                            label = "auth_content"
                        ) { loginMode ->
                            if (loginMode) {
                                LoginContent(
                                    state = state,
                                    passwordVisible = passwordVisible,
                                    onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
                                    onEmailChanged = viewModel::onEmailChanged,
                                    onPasswordChanged = viewModel::onPasswordChanged,
                                    onLoginClicked = viewModel::onLoginClicked
                                )
                            } else {
                                RegisterContent(
                                    state = state,
                                    passwordVisible = passwordVisible,
                                    onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
                                    onNameChanged = viewModel::onNameChanged,
                                    onEmailChanged = viewModel::onEmailChanged,
                                    onPasswordChanged = viewModel::onPasswordChanged,
                                    onRegisterClicked = viewModel::onRegisterClicked
                                )
                            }
                        }

                        if (state.error != null) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = state.error!!,
                                color = Color(0xFFD32F2F),
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun LoginContent(
    state: AuthState,
    passwordVisible: Boolean,
    onPasswordVisibilityToggle: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome Back!",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Monospace
            ),
            color = Color.White
        )

        Text(
            text = "Login to continue",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = onEmailChanged,
            label = { Text("Email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email icon",
                    tint = Color(0xFF00D9FF)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = state.error != null,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF00D9FF),
                focusedLabelColor = Color(0xFF00D9FF),
                focusedLeadingIconColor = Color(0xFF00D9FF),
                unfocusedBorderColor = Color(0xFF3A4449),
                unfocusedLabelColor = Color(0xFF8B9499),
                unfocusedLeadingIconColor = Color(0xFF8B9499),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color(0xFFE0E0E0)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = onPasswordChanged,
            label = { Text("Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password icon",
                    tint = Color(0xFF00D9FF)
                )
            },
            trailingIcon = {
                IconButton(onClick = onPasswordVisibilityToggle) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Outlined.Lock else Icons.Default.Person,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        tint = Color(0xFF8B9499)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = state.error != null,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF00D9FF),
                focusedLabelColor = Color(0xFF00D9FF),
                focusedLeadingIconColor = Color(0xFF00D9FF),
                unfocusedBorderColor = Color(0xFF3A4449),
                unfocusedLabelColor = Color(0xFF8B9499),
                unfocusedLeadingIconColor = Color(0xFF8B9499),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color(0xFFE0E0E0)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { /* Handle forgot password */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "Forgot Password?",
                color = Color(0xFF35718C)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (state.isLoadiing) {
            CircularProgressIndicator(color = Color(0xFF00D9FF))
        } else {
            Button(
                onClick = onLoginClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00D9FF)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Login",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0B0C0D)
                )
            }
        }
    }
}

@Composable
            fun RegisterContent(
                state: AuthState,
                passwordVisible: Boolean,
                onPasswordVisibilityToggle: () -> Unit,
                onNameChanged: (String) -> Unit,
                onEmailChanged: (String) -> Unit,
                onPasswordChanged: (String) -> Unit,
                onRegisterClicked: () -> Unit
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Create Account",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Normal,
                                    fontFamily = FontFamily.Monospace
                        ),
                        color = Color.White
                    )

                    Text(
                        text = "Sign up to get started",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF8B9499)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = state.name,
                        onValueChange = onNameChanged,
                        label = { Text("Full Name") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Name icon",
                                tint = Color(0xFF00D9FF)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        isError = state.error != null,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF00D9FF),
                            focusedLabelColor = Color(0xFF00D9FF),
                            focusedLeadingIconColor = Color(0xFF00D9FF),
                            unfocusedBorderColor = Color(0xFF3A4449),
                            unfocusedLabelColor = Color(0xFF8B9499),
                            unfocusedLeadingIconColor = Color(0xFF8B9499),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color(0xFFE0E0E0)
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = state.email,
                        onValueChange = onEmailChanged,
                        label = { Text("Email") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email icon",
                                tint = Color(0xFF00D9FF)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = state.error != null,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF00D9FF),
                            focusedLabelColor = Color(0xFF00D9FF),
                            focusedLeadingIconColor = Color(0xFF00D9FF),
                            unfocusedBorderColor = Color(0xFF3A4449),
                            unfocusedLabelColor = Color(0xFF8B9499),
                            unfocusedLeadingIconColor = Color(0xFF8B9499),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color(0xFFE0E0E0)
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = state.password,
                        onValueChange = onPasswordChanged,
                        label = { Text("Password") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Password icon",
                                tint = Color(0xFF00D9FF)
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = onPasswordVisibilityToggle) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Outlined.Lock else Icons.Default.Person,
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                    tint = Color(0xFF8B9499)
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        isError = state.error != null,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF00D9FF),
                            focusedLabelColor = Color(0xFF00D9FF),
                            focusedLeadingIconColor = Color(0xFF00D9FF),
                            unfocusedBorderColor = Color(0xFF3A4449),
                            unfocusedLabelColor = Color(0xFF8B9499),
                            unfocusedLeadingIconColor = Color(0xFF8B9499),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color(0xFFE0E0E0)
                        )
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    if (state.isLoadiing) {
                        CircularProgressIndicator(color = Color(0xFF00D9FF))
                    } else {
                        Button(
                            onClick = onRegisterClicked,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF00D9FF)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Create Account",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0B0C0D)
                            )
                        }
                    }
                }
            }
