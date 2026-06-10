package com.memy.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.memy.app.ui.components.*
import com.memy.app.ui.theme.*
import kotlinx.coroutines.delay

// ── Splash ────────────────────────────────────────────────────────────────────
@Composable
fun SplashScreen(onComplete: () -> Unit) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        delay(1_600)
        onComplete()
    }

    AnimatedVisibility(
        visible = visible,
        enter   = fadeIn(tween(400)) + slideInVertically(tween(400)) { 30 },
    ) {
        Box(
            modifier         = Modifier
                .fillMaxSize()
                .background(SurfacePage),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // App icon
                Box(
                    modifier = Modifier
                        .size(104.dp)
                        .shadow(
                            elevation    = 14.dp,
                            shape        = RoundedCornerShape(24.dp),
                            spotColor    = Coral400.copy(alpha = 0.28f),
                            ambientColor = Coral400.copy(alpha = 0.18f),
                        )
                        .background(Brand, RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        "m",
                        style = MaterialTheme.typography.displayLarge.copy(
                            color      = OnBrand,
                            fontSize   = 56.sp,
                        )
                    )
                }
                Spacer(Modifier.height(20.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "memy",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontFamily = RubikFamily,
                            fontWeight = FontWeight.Bold,
                            color      = TextStrong,
                        )
                    )
                    Spacer(Modifier.width(3.dp))
                    Box(Modifier
                        .size(7.dp)
                        .background(Brand, CircleShape)
                        .align(Alignment.Bottom)
                        .padding(bottom = 4.dp)
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    "your memories, everywhere",
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextMuted),
                )
            }
        }
    }
}

// ── Welcome ───────────────────────────────────────────────────────────────────
@Composable
fun WelcomeScreen(onContinue: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(SurfacePage)) {
        // Wave header fills ~70% of screen
        WaveHeader(
            modifier  = Modifier.align(Alignment.TopCenter),
            heightDp  = 480.dp,
        ) {
            // Hero text in the lower portion of the wave
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 28.dp, end = 28.dp, bottom = 64.dp),
            ) {
                Text(
                    "Welcome",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontFamily = RubikFamily,
                        fontWeight = FontWeight.Bold,
                        color      = OnBrand,
                    )
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    "Capture, organize and revisit\nthe memories you love.",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color      = OnBrand.copy(alpha = 0.85f),
                        lineHeight = 24.sp,
                    )
                )
            }
        }

        // Continue FAB — bottom right
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 28.dp, bottom = 40.dp)
                .size(60.dp)
                .shadow(14.dp, CircleShape, spotColor = Coral400.copy(0.28f), ambientColor = Coral400.copy(0.18f))
                .background(Brand, CircleShape)
                .clickable(onClick = onContinue),
            contentAlignment = Alignment.Center,
        ) {
            Icon(Icons.Filled.ArrowForward, contentDescription = "Continue",
                tint = OnBrand, modifier = Modifier.size(26.dp))
        }
    }
}

// ── Login ─────────────────────────────────────────────────────────────────────
@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onSignUp: () -> Unit,
) {
    var email      by remember { mutableStateOf("") }
    var password   by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var showPass   by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfacePage)
            .verticalScroll(rememberScrollState())
    ) {
        // Wave header ~1/3 screen
        WaveHeader(heightDp = 220.dp)

        // Form
        Column(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp),
        ) {
            // Title with underline
            Column {
                Text(
                    "Sign in",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontFamily = RubikFamily,
                        color      = TextStrong,
                    )
                )
                Spacer(Modifier.height(6.dp))
                Box(Modifier
                    .width(74.dp)
                    .height(3.dp)
                    .background(Brand, RoundedCornerShape(999.dp))
                )
            }

            Spacer(Modifier.height(28.dp))

            UnderlineTextField(
                value         = email,
                onValueChange = { email = it },
                label         = "Email",
                leadingIcon   = {
                    Icon(Icons.Outlined.Mail, null, tint = TextMuted, modifier = Modifier.size(20.dp))
                },
                placeholder   = "demo@email.com",
            )

            Spacer(Modifier.height(20.dp))

            UnderlineTextField(
                value                = password,
                onValueChange        = { password = it },
                label                = "Password",
                leadingIcon          = {
                    Icon(Icons.Outlined.Lock, null, tint = TextMuted, modifier = Modifier.size(20.dp))
                },
                trailingIcon         = {
                    IconButton(onClick = { showPass = !showPass }) {
                        Icon(
                            if (showPass) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            null,
                            tint = TextMuted,
                        )
                    }
                },
                visualTransformation = if (showPass) VisualTransformation.None
                                       else PasswordVisualTransformation(),
            )

            Spacer(Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier          = Modifier.fillMaxWidth(),
            ) {
                Checkbox(
                    checked         = rememberMe,
                    onCheckedChange = { rememberMe = it },
                    colors          = CheckboxDefaults.colors(
                        checkedColor   = Brand,
                        checkmarkColor = OnBrand,
                    ),
                    modifier = Modifier.size(20.dp),
                )
                Spacer(Modifier.width(8.dp))
                Text("Remember Me",
                    style = MaterialTheme.typography.bodySmall.copy(color = TextBody))
                Spacer(Modifier.weight(1f))
                Text(
                    "Forgot Password?",
                    style    = MaterialTheme.typography.bodySmall.copy(color = Brand),
                    modifier = Modifier.clickable { },
                )
            }

            Spacer(Modifier.height(28.dp))

            MemyButton("Login", onClick = onLogin)

            Spacer(Modifier.height(20.dp))

            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text("Don't have an Account? ",
                    style = MaterialTheme.typography.bodySmall.copy(color = TextMuted))
                Text(
                    "Sign up",
                    style    = MaterialTheme.typography.bodySmall.copy(
                        color      = Brand,
                        fontWeight = FontWeight.SemiBold,
                    ),
                    modifier = Modifier.clickable(onClick = onSignUp),
                )
            }
            Spacer(Modifier.height(40.dp))
        }
    }
}

// ── Sign Up ───────────────────────────────────────────────────────────────────
@Composable
fun SignUpScreen(
    onCreateAccount: () -> Unit,
    onLogin: () -> Unit,
) {
    var email     by remember { mutableStateOf("") }
    var phone     by remember { mutableStateOf("") }
    var password  by remember { mutableStateOf("") }
    var confirm   by remember { mutableStateOf("") }
    var showPass  by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfacePage)
            .verticalScroll(rememberScrollState())
    ) {
        WaveHeader(heightDp = 160.dp)

        Column(modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp)) {
            Text(
                "Create account",
                style = MaterialTheme.typography.headlineLarge.copy(color = TextStrong)
            )
            Spacer(Modifier.height(6.dp))
            Box(Modifier.width(74.dp).height(3.dp).background(Brand, RoundedCornerShape(999.dp)))
            Spacer(Modifier.height(28.dp))

            UnderlineTextField(email, { email = it }, "Email",
                leadingIcon = { Icon(Icons.Outlined.Mail, null, tint = TextMuted, modifier = Modifier.size(20.dp)) })
            Spacer(Modifier.height(20.dp))
            UnderlineTextField(phone, { phone = it }, "Phone number",
                leadingIcon = { Icon(Icons.Outlined.Phone, null, tint = TextMuted, modifier = Modifier.size(20.dp)) })
            Spacer(Modifier.height(20.dp))
            UnderlineTextField(password, { password = it }, "Password",
                leadingIcon = { Icon(Icons.Outlined.Lock, null, tint = TextMuted, modifier = Modifier.size(20.dp)) },
                visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation())
            Spacer(Modifier.height(20.dp))
            UnderlineTextField(confirm, { confirm = it }, "Confirm password",
                leadingIcon = { Icon(Icons.Outlined.Lock, null, tint = TextMuted, modifier = Modifier.size(20.dp)) },
                visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation())

            Spacer(Modifier.height(32.dp))
            MemyButton("Create account", onClick = onCreateAccount)
            Spacer(Modifier.height(20.dp))

            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text("Already have an account? ",
                    style = MaterialTheme.typography.bodySmall.copy(color = TextMuted))
                Text("Login",
                    style    = MaterialTheme.typography.bodySmall.copy(
                        color = Brand, fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.clickable(onClick = onLogin))
            }
            Spacer(Modifier.height(40.dp))
        }
    }
}

// ── Auth Success ──────────────────────────────────────────────────────────────
@Composable
fun AuthSuccessScreen(onContinue: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1_800)
        onContinue()
    }

    Box(
        modifier         = Modifier
            .fillMaxSize()
            .background(SurfacePage),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Brand, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Filled.Check, null, tint = OnBrand, modifier = Modifier.size(40.dp))
            }
            Spacer(Modifier.height(24.dp))
            Text(
                "You're in!",
                style = MaterialTheme.typography.headlineLarge.copy(color = TextStrong)
            )
            Spacer(Modifier.height(32.dp))
            MemyButton("Continue", onClick = onContinue,
                modifier = Modifier.widthIn(min = 200.dp), fullWidth = false)
        }
    }
}
