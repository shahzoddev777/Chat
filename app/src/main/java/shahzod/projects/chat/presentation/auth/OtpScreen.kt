package shahzod.projects.chat.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.flow.collectLatest
import shahzod.projects.chat.R
import shahzod.projects.chat.presentation.tab.MainScreen
import shahzod.projects.presentation.auth.otp.OtpAuthContract
import shahzod.projects.presentation.auth.otp.OtpAuthViewModel

private const val OTP_LENGTH = 6

data class OtpScreen(val phone: String) : Screen {

    @Composable
    override fun Content() {
        val viewModel = getViewModel<OtpAuthViewModel>()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(phone) {
            viewModel.setPhone(phone)
        }

        OtpAuthScreen(
            viewModel = viewModel,
            onNavigateToHome = {
                navigator.replaceAll(MainScreen())
            },
            onNavigateBack = {
                navigator.pop()
            }
        )
    }
}

@Composable
fun OtpAuthScreen(
    viewModel: OtpAuthViewModel,
    onNavigateToHome: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.sideEffect.collectLatest { effect ->
            when (effect) {
                OtpAuthContract.SideEffect.NavigateToHome -> {
                    onNavigateToHome()
                }
                is OtpAuthContract.SideEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                OtpAuthContract.SideEffect.NavigateBack -> {
                    onNavigateBack()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF8F2))
            .padding(16.dp)
    ) {
        IconButton(
            onClick = { viewModel.onIntent(OtpAuthContract.Intent.OnBackClick) },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Orqaga",
                tint = Color(0xFF1A1B4B)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tasdiqlash kodi",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1B4B)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${uiState.phone} raqamiga yuborilgan\nkodni kiriting",
            fontSize = 14.sp,
            color = Color(0xFF5E6380)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 0 until OTP_LENGTH) {
                val char = uiState.code.getOrNull(i)?.toString() ?: ""
                val isFocused = uiState.code.length == i

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(58.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White)
                        .border(
                            border = BorderStroke(
                                width = if (isFocused) 1.5.dp else 1.dp,
                                color = if (isFocused) Color(0xFF00A884) else Color(0xFFE0E0EF)
                            ),
                            shape = RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = char,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1B4B)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isResendEnabled) {
                Text(
                    text = "Qayta yuborish",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF00A884),
                    modifier = Modifier.clickable {
                        viewModel.onIntent(OtpAuthContract.Intent.OnResendClick)
                    }
                )
            } else {
                Text(
                    text = "Qayta yuborish (${uiState.formattedTimer})",
                    fontSize = 14.sp,
                    color = Color(0xFF5E6380)
                )
            }
        }

        if (!uiState.errorMessage.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = uiState.errorMessage!!,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.onIntent(OtpAuthContract.Intent.OnVerifyClick) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00A884),
                disabledContainerColor = Color(0xFF00A884).copy(alpha = 0.5f),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(26.dp),
            enabled = uiState.isCodeFilled && !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(
                    text = "Tasdiqlash",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        val keypadRows = listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
            listOf("", "0", "del")
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            keypadRows.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { key ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (key.isNotEmpty()) Color.White else Color.Transparent)
                                .then(
                                    if (key.isNotEmpty()) {
                                        Modifier.clickable {
                                            if (key == "del") {
                                                viewModel.onIntent(OtpAuthContract.Intent.OnBackspaceClick)
                                            } else {
                                                viewModel.onIntent(OtpAuthContract.Intent.OnKeyClick(key))
                                            }
                                        }
                                    } else Modifier
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (key == "del") {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_backspace),
                                    contentDescription = "O'chirish",
                                    tint = Color(0xFF1A1B4B)
                                )
                            } else if (key.isNotEmpty()) {
                                Text(
                                    text = key,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1A1B4B)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}