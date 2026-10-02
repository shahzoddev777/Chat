package shahzod.projects.chat.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Skrinshotdan o'lchab olingan ranglar
internal val ScreenBackground = Color(0xFFF1F1F3)
internal val CardBackground = Color.White
internal val AccentBlue = Color(0xFF3A8CCC)
internal val PrimaryText = Color.Black
internal val SecondaryText = Color(0xFF6B6B6F)
internal val HintText = Color(0xFF9B9B9B)
internal val CounterText = Color(0xFF8A8A8E)
internal val DividerColor = Color(0xFFEDEDED)
internal val CursorColor = Color(0xFF1C1C1E)

/** Oq, 16dp burchakli karta. */
@Composable
internal fun SettingsCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardBackground),
        content = content
    )
}

/** Ko'k sarlavha: "Ismingiz", "Axborotlaringiz". */
@Composable
internal fun SectionHeader(text: String) {
    Text(
        text = text,
        color = AccentBlue,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(start = 18.dp, top = 14.dp, bottom = 5.dp)
    )
}

/** Kartadan tashqaridagi kulrang izoh matni. */
@Composable
internal fun HelperText(
    text: AnnotatedString,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = SecondaryText,
        fontSize = 14.sp,
        lineHeight = 17.sp,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 16.dp)
    )
}

@Composable
internal fun HelperText(text: String) = HelperText(text = AnnotatedString(text))

/**
 * Chiziqsiz (Telegram uslubidagi) matn maydoni.
 * Balandligi 52dp: 14dp + 24dp qator + 14dp.
 */
@Composable
internal fun ProfileTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Words,
    imeAction: ImeAction = ImeAction.Next,
    singleLine: Boolean = true,
    trailing: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            textStyle = TextStyle(
                color = PrimaryText,
                fontSize = 17.sp,
                lineHeight = 24.sp
            ),
            cursorBrush = SolidColor(CursorColor),
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(
                capitalization = capitalization,
                imeAction = imeAction
            ),
            decorationBox = { innerTextField ->
                Box {
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            color = HintText,
                            fontSize = 17.sp,
                            lineHeight = 24.sp
                        )
                    }
                    innerTextField()
                }
            }
        )
        trailing?.invoke()
    }
}

/** Rangli, yumaloq-kvadrat ikonka (28dp), vertikal gradient. */
@Composable
internal fun IconBadge(
    top: Color,
    bottom: Color,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .clip(RoundedCornerShape(7.dp))
            .background(Brush.verticalGradient(listOf(top, bottom))),
        contentAlignment = Alignment.Center,
        content = content
    )
}

@Composable
internal fun GlyphBadge(
    icon: ImageVector,
    top: Color,
    bottom: Color
) {
    IconBadge(top = top, bottom = bottom) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(18.dp)
        )
    }
}

/** Sarlavha + kulrang izohli qator (telefon, username, tug'ilgan kun). */
@Composable
internal fun InfoRow(
    badge: @Composable () -> Unit,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp)
            .clickable(onClick = onClick)
            .padding(start = 18.dp, end = 22.dp, top = 2.dp, bottom = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        badge()
        Spacer(Modifier.width(18.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = PrimaryText,
                fontSize = 16.sp,
                lineHeight = 20.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = subtitle,
                color = SecondaryText,
                fontSize = 13.sp,
                lineHeight = 16.sp
            )
        }
    }
}

/** Bitta qatorli, 49dp balandlikdagi harakat qatori (Shaxsiy kanal, Chatlarni avtomatlashtirish). */
@Composable
internal fun ActionRow(
    badge: @Composable () -> Unit,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        badge()
        Spacer(Modifier.width(18.dp))
        content()
    }
}

/** "NEW" belgisi. */
@Composable
internal fun NewBadge() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0xFF4BA3FD))
            .padding(horizontal = 4.dp, vertical = 1.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "NEW",
            color = Color.White,
            fontSize = 10.sp,
            lineHeight = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
