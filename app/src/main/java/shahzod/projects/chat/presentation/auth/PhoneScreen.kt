package shahzod.projects.chat.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
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
import kotlinx.coroutines.flow.collectLatest
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import shahzod.projects.presentation.auth.phone.PhoneAuthContract
import shahzod.projects.presentation.auth.phone.PhoneAuthViewModel
import shahzod.projects.chat.R

class PhoneScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<PhoneAuthViewModel>()
        val navigator = LocalNavigator.currentOrThrow

        PhoneAuthScreen(
            viewModel = viewModel,
            onNavigateToOtp = { phoneNumber ->
                navigator.push(OtpScreen(phoneNumber))
            }
        )
    }
}

@Composable
fun PhoneAuthScreen(
    viewModel: PhoneAuthViewModel,
    onNavigateToOtp: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.sideEffect.collectLatest { effect ->
            when (effect) {
                is PhoneAuthContract.SideEffect.NavigateToOtp -> {
                    onNavigateToOtp(effect.phone)
                }
                is PhoneAuthContract.SideEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                PhoneAuthContract.SideEffect.NavigateBack -> {
                    // Orqaga qaytish amali
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3FA))
            .padding(16.dp)
    ) {
        IconButton(
            onClick = { viewModel.onIntent(PhoneAuthContract.Intent.OnBackClick) },
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
            text = "Telefon raqamingiz",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1B4B)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Ushbu raqamga tasdiqlash kodi yuboriladi",
            fontSize = 14.sp,
            color = Color(0xFF5E6380)
        )

        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .border(1.dp, Color(0xFFE0E0EF), RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_uz_flag),
                contentDescription = "Bayroq",
                tint = Color.Unspecified,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "+998",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1B4B)
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = Color(0xFF5E6380)
            )

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(24.dp)
                    .background(Color(0xFFE0E0EF))
                    .padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            val formattedNumber = formatPhoneNumber(uiState.phoneNumber)
            Text(
                text = if (formattedNumber.isEmpty()) "90 123 45 67" else formattedNumber,
                fontSize = 16.sp,
                color = if (uiState.phoneNumber.isEmpty()) Color(0xFF9E9FA8) else Color(0xFF1A1B4B)
            )
        }

        if (!uiState.errorMessage.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = uiState.errorMessage!!,
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.onIntent(PhoneAuthContract.Intent.OnSendCodeClick) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3D5AFE)),
            shape = RoundedCornerShape(26.dp),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(
                    text = "Kod yuborish",
                    fontSize = 16.sp,
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
                                                viewModel.onIntent(PhoneAuthContract.Intent.OnBackspaceClick)
                                            } else {
                                                viewModel.onIntent(PhoneAuthContract.Intent.OnKeyClick(key))
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

private fun formatPhoneNumber(phone: String): String {
    val sb = StringBuilder()
    for (i in phone.indices) {
        if (i == 2 || i == 5 || i == 7) {
            sb.append(" ")
        }
        sb.append(phone[i])
    }
    return sb.toString()
}