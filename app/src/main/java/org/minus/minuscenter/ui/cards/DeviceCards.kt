package org.minus.minuscenter.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.minus.minuscenter.iotdevice.Device

@Composable
fun DeviceCard(
    device: Device,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 根据设备类型选择不同的柔和渐变颜色
    val gradientColors = when (device.type) {
        "卧室" -> listOf(
            Color(0xFF93CBFA), // 浅蓝色
            Color(0xFFCFE4FD)  // 更浅的蓝色
        )
        "客厅" -> listOf(
            Color(0xFFFD9873),// 浅紫色
            Color(0xFFFFD1BE) // 更浅的紫色
        )
        "厨房" -> listOf(
            Color(0xFFA5D6A7),  // 更浅的绿色
            Color(0xFFC8E6C9) // 浅绿色
        )
        "浴室" -> listOf(
            Color(0xFFFFAEAE), // 浅青色
            Color(0xFFFFE0EA)  // 更浅的青色
        )
        else -> listOf(
            Color(0xFF63B6FF), // 浅灰色
            Color(0xFFC5E6FF)  // 稍深灰色
        )
    }

    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(24.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(180.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // 背景灰色渐变
            Box(
                modifier = Modifier.fillMaxSize().background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFFFF),
                            Color(0xFFF1F1F1)
                        ),
                        startY = 0f, endY = Float.POSITIVE_INFINITY
                    )
                )
            )
            // 顶部柔和彩色渐变
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(
                        brush = Brush.verticalGradient(colors = gradientColors)
                    )
            )
            // 内容区域：名称和描述
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                Text(
                    text = device.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold, fontSize = 20.sp
                    ),
                    color = Color.Black, maxLines = 1, overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = device.description,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    color = Color.Gray, maxLines = 2, overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            // 设备类型标签，放置在灰色区域底部
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Brush.horizontalGradient(colors = gradientColors))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = device.type,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Medium, fontSize = 12.sp
                    ),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun DeviceCard(
    modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(24.dp),
        modifier = modifier.fillMaxWidth()
            .padding(8.dp)
            .height(180.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF5F5F5),
                            Color(0xFFE0E0E0)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
fun DeviceCard(
    onClick: () -> Unit, modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(24.dp),
        modifier = modifier.fillMaxWidth()
            .padding(8.dp)
            .height(180.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF5F5F5),
                            Color(0xFFE0E0E0)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                content()
            }
        }
    }
}
