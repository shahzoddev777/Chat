package shahzod.projects.chat.presentation.tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainScreen(private val initTab: Tab = HomeTab) : Screen {
    @Composable
    override fun Content() {
        TabNavigator(tab = initTab) { tabNavigator ->
            Box(Modifier.fillMaxSize()) {
                CurrentTab()
                LiquidGlassBottomBar(
                    tabs = listOf(HomeTab, GroupsTab, SettingsTab, ProfileScreen),
                    selected = tabNavigator.current,
                    onSelect = { tabNavigator.current = it },
                    badges = mapOf(HomeTab to 8),
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}

private val ActiveBlue = Color(0xFF0A84FF)
private val BadgeRed = Color(0xFFFF3B30)

@Composable
fun LiquidGlassBottomBar(
    tabs: List<Tab>,
    selected: Tab,
    onSelect: (Tab) -> Unit,
    modifier: Modifier = Modifier,
    badges: Map<Tab, Int> = emptyMap(),
) {
    val dark = isSystemInDarkTheme()
    val barShape = RoundedCornerShape(50)

    val glassBrush = if (dark)
        Brush.verticalGradient(listOf(Color(0xCC2C2C2E), Color(0xB31C1C1E)))
    else
        Brush.verticalGradient(listOf(Color(0xE6FFFFFF), Color(0xCCF2F4F7)))

    val rimBrush = Brush.verticalGradient(
        listOf(
            Color.White.copy(alpha = if (dark) 0.28f else 0.95f),
            Color.White.copy(alpha = if (dark) 0.06f else 0.30f)
        )
    )
    val inactive = if (dark) Color(0xFFB4B4B9) else Color(0xFF4A4A4A)
    val capsuleFill = if (dark) Color.White.copy(alpha = 0.14f) else Color.White.copy(alpha = 0.80f)

    val barHeight = 66.dp
    val innerPad = 6.dp
    val capsuleExtraW = 22.dp   // kapsula item'dan kengroq
    val capsuleExtraH = 14.dp   // kapsula bardan balandroq

    BoxWithConstraints(
        modifier = modifier
            .navigationBarsPadding()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .fillMaxWidth()
            .height(barHeight)
    ) {
        val itemWidth = (maxWidth - innerPad * 2) / tabs.size
        val index = tabs.indexOf(selected).coerceAtLeast(0)
        val capsuleX by animateDpAsState(
            targetValue = innerPad + itemWidth * index - capsuleExtraW / 2,
            animationSpec = spring(dampingRatio = 0.72f, stiffness = Spring.StiffnessMedium),
            label = "capsuleX"
        )

        // 1) Shisha fon
        Box(
            Modifier
                .matchParentSize()
                .shadow(
                    elevation = 18.dp,
                    shape = barShape,
                    clip = false,
                    ambientColor = Color.Black.copy(alpha = 0.10f),
                    spotColor = Color.Black.copy(alpha = 0.20f)
                )
                .clip(barShape)
                .background(glassBrush)
                .border(1.dp, rimBrush, barShape)
        )

        // 2) Tanlangan tab kapsulasi (bardan sal chiqib turadi)
        Box(
            Modifier
                .offset(x = capsuleX, y = -capsuleExtraH / 2)
                .width(itemWidth + capsuleExtraW)
                .height(barHeight + capsuleExtraH)
                .shadow(
                    elevation = 8.dp,
                    shape = barShape,
                    clip = false,
                    ambientColor = Color.Black.copy(alpha = 0.08f),
                    spotColor = Color.Black.copy(alpha = 0.16f)
                )
                .background(capsuleFill, barShape)
                .border(BorderStroke(1.dp, rimBrush), barShape)
        )

        // 3) Tab'lar
        Row(
            Modifier
                .matchParentSize()
                .padding(horizontal = innerPad)
        ) {
            tabs.forEach { tab ->
                val isSelected = tab == selected
                val color by animateColorAsState(
                    targetValue = if (isSelected) ActiveBlue else inactive,
                    label = "tabColor"
                )
                val count = badges[tab] ?: 0

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onSelect(tab) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box {
                        tab.options.icon?.let { painter ->
                            Icon(
                                painter = painter,
                                contentDescription = tab.options.title,
                                tint = color,
                                modifier = Modifier.size(27.dp)
                            )
                        }
                        if (count > 0) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(x = 12.dp, y = (-7).dp)
                                    .defaultMinSize(minWidth = 20.dp)
                                    .height(20.dp)
                                    .background(BadgeRed, CircleShape)
                                    .padding(horizontal = 5.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (count > 99) "99+" else count.toString(),
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(3.dp))
                    Text(
                        text = tab.options.title,
                        color = color,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
                    )
                }
            }
        }
    }
}