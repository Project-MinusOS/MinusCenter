package org.minus.minuscenter.ui.cards

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.minus.minuscenter.iotdevice.Device

@Composable
fun DeviceCardContent(
    device: Device,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(12.dp)
    ) {
        // 第一行：设备图标 + 设置图标
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 根据设备类型显示不同的图标
            val icon = when (device.type) {
                "卧室" -> Icons.Filled.Bed
                "客厅" -> Icons.Filled.CoffeeMaker
                "厨房" -> Icons.Filled.Kitchen
                "浴室" -> Icons.Filled.Bathtub
                else -> Icons.Filled.Devices
            }
            Icon(
                imageVector = icon,
                contentDescription = device.type,
                modifier = Modifier.size(40.dp),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Settings",
                modifier = Modifier.size(40.dp),
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = device.name,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = device.description,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 2
        )
    }
} 