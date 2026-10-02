package shahzod.projects.chat.presentation.profile

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import cafe.adriel.voyager.hilt.getViewModel
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import org.orbitmvi.orbit.compose.collectAsState
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.flow.collectLatest
import shahzod.projects.chat.presentation.ui.theme.ChatTheme
import shahzod.projects.presentation.profile.ProfileContract
import shahzod.projects.presentation.profile.ProfileViewModel

class ProfileScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = getViewModel<ProfileViewModel>()
        val uiState by viewModel.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            viewModel.onEventDispatcher(ProfileContract.ProfileIntent.LoadData)

            viewModel.container.sideEffectFlow.collectLatest { effect ->
                when (effect) {
                    is ProfileContract.SideEffect.ShowToast ->
                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    ProfileContract.SideEffect.NavigateBack ->
                        navigator.pop()
                    ProfileContract.SideEffect.NavigateToLogin -> {
                    }
                }
            }
        }

        EditProfileContent(
            state = uiState,
            onIntent = viewModel::onEventDispatcher
        )
    }
}

@Composable
fun EditProfileContent(
    state: ProfileContract.UiState,
    onIntent: (ProfileContract.ProfileIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .statusBarsPadding()
            .imePadding()
    ) {
        EditProfileTopBar(
            showSave = state.online,
            isLoading = state.isLoading,
            onBack = { onIntent(ProfileContract.ProfileIntent.OnBackClicked) },
            onSave = { onIntent(ProfileContract.ProfileIntent.OnSaveClicked) }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp)
        ) {
            Spacer(Modifier.height(4.dp))

            SettingsCard {
                SectionHeader("Ismingiz")
                // 1-maydon: Username uchun
                ProfileTextField(
                    value = state.username,
                    onValueChange = { onIntent(ProfileContract.ProfileIntent.OnUsernameChanged(it)) },
                    hint = "Username"
                )
                HorizontalDivider(
                    modifier = Modifier.padding(start = 20.dp),
                    thickness = 0.5.dp,
                    color = DividerColor
                )
                // 2-maydon: DisplayName (Ism va Familiya) uchun
                ProfileTextField(
                    value = state.displayName,
                    onValueChange = { onIntent(ProfileContract.ProfileIntent.OnDisplayNameChanged(it)) },
                    hint = "Ism va Familiya"
                )
            }

            Spacer(Modifier.height(12.dp))

            SettingsCard {
                SectionHeader("Axborotlaringiz")
                InfoRow(
                    badge = { GlyphBadge(Icons.Filled.Phone, GreenTop, GreenBottom) },
                    title = state.formattedPhone,
                    subtitle = "Telefon raqamini almashtirish uchun bosing",
                    onClick = {  }
                )
                InfoRow(
                    badge = { GlyphBadge(Icons.Filled.AlternateEmail, OrangeTop, OrangeBottom) },
                    title = state.username.ifEmpty { "Kiritilmagan" },
                    subtitle = "Foydalanuvchi nomi",
                    onClick = {  }
                )
            }

            Spacer(Modifier.height(12.dp))

            SettingsCard {
                ActionRow(
                    badge = { GlyphBadge(Icons.Filled.Campaign, OrangeTop, OrangeBottom) },
                    onClick = {  }
                ) {
                    Text(
                        text = "Shaxsiy kanal",
                        color = PrimaryText,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Qo‘shish",
                        color = AccentBlue,
                        fontSize = 16.sp
                    )
                }
                ActionRow(
                    badge = {
                        IconBadge(top = PurpleTop, bottom = PurpleBottom) {
                            Text(
                                text = "Ai",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = Icons.Filled.AutoAwesome,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(start = 3.dp, top = 3.dp)
                                    .height(8.dp)
                                    .width(8.dp)
                            )
                        }
                    },
                    onClick = {  }
                ) {
                    Text(
                        text = "Chatlarni avtomatlashtirish",
                        color = PrimaryText,
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.width(8.dp))
                    NewBadge()
                }
            }
            HelperText("Xabarlarga sizning nomingizdan javob berishi uchun bot qo‘shing.")

            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
        }
    }
}

@Composable
private fun EditProfileTopBar(
    showSave: Boolean,
    isLoading: Boolean,
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.padding(start = 4.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Orqaga",
                tint = PrimaryText
            )
        }
        Text(
            text = "Hisob",
            color = PrimaryText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp)
        )
        if (isLoading) {
            androidx.compose.material3.CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 12.dp),
                color = AccentBlue,
                strokeWidth = 2.dp
            )
        } else {
            AnimatedVisibility(
                visible = showSave,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                IconButton(
                    onClick = onSave,
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Saqlash",
                        tint = AccentBlue
                    )
                }
            }
        }
    }
}

@Composable
private fun UsernameDialog(
    initial: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var value by remember { mutableStateOf(initial) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Foydalanuvchi nomi") },
        text = {
            Column {
                OutlinedTextField(
                    value = value,
                    onValueChange = { input ->
                        value = input.filter { it.isUsernameChar() }.take(32)
                    },
                    prefix = { Text("@") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Lotin harflari (a–z), raqamlar va pastki chiziq (_) ishlatish mumkin. Kamida 5 ta belgi.",
                    color = SecondaryText,
                    fontSize = 13.sp,
                    lineHeight = 16.sp
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(value) },
                colors = ButtonDefaults.textButtonColors(contentColor = AccentBlue)
            ) { Text("Saqlash") }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = AccentBlue)
            ) { Text("Bekor qilish") }
        }
    )
}

private fun Char.isUsernameChar(): Boolean =
    this in 'a'..'z' || this in 'A'..'Z' || this in '0'..'9' || this == '_'

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun EditProfilePreview() {
    ChatTheme(dynamicColor = false) {
        EditProfileContent(
            state = ProfileContract.UiState(
                phone = "+998777773928",
                displayName = "Shahzod1"
            ),
            onIntent = {}
        )
    }
}

private val GreenTop = Color(0xFF4FC942)
private val GreenBottom = Color(0xFF2EB034)
private val OrangeTop = Color(0xFFF6A01E)
private val OrangeBottom = Color(0xFFE48D14)
private val BlueTop = Color(0xFF23A2E5)
private val BlueBottom = Color(0xFF158DE4)
private val PurpleTop = Color(0xFFC48BF3)
private val PurpleBottom = Color(0xFF9E58DD)