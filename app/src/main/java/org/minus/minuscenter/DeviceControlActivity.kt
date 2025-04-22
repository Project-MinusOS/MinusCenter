package org.minus.minuscenter

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import org.minus.minuscenter.iotdevice.Device
import org.minus.minuscenter.ui.theme.MinusCenterTheme
import org.minus.minuscenter.ui.cards.DeviceCardContent

class DeviceControlActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val device = intent.getSerializableExtra("device") as Device
        setContent {
            MinusCenterTheme {
                enableEdgeToEdge()
                DeviceControlScreen(device)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceControlScreen(device: Device) {
    val context = LocalContext.current
    var isDeviceOn by remember { mutableStateOf(false) }
    var brightness by remember { mutableStateOf(50) }
    var temperature by remember { mutableStateOf(25) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(device.name) },
                navigationIcon = {
                    IconButton(onClick = { (context as Activity).finish() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                // 设备信息卡片
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        DeviceCardContent(device = device)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "IP地址: ${device.ipAddress}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                // 设备控制部分
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "设备控制",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // 开关控制
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "设备开关",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f)
                            )
                            Switch(
                                checked = isDeviceOn,
                                onCheckedChange = { isDeviceOn = it }
                            )
                        }

                        // 亮度控制（仅对灯光设备显示）
                        if (device.type == "卧室" || device.type == "客厅") {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Text(
                                    text = "亮度调节",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Slider(
                                    value = brightness.toFloat(),
                                    onValueChange = { brightness = it.toInt() },
                                    valueRange = 0f..100f,
                                    steps = 20
                                )
                                Text(
                                    text = "$brightness%",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        }

                        // 温度控制（仅对空调设备显示）
                        if (device.type == "客厅") {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Text(
                                    text = "温度调节",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Slider(
                                    value = temperature.toFloat(),
                                    onValueChange = { temperature = it.toInt() },
                                    valueRange = 16f..30f,
                                    steps = 14
                                )
                                Text(
                                    text = "${temperature}°C",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        }
                    }
                }

                // 状态信息
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "设备状态",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = "当前状态: ${if (isDeviceOn) "开启" else "关闭"}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        if (device.type == "卧室" || device.type == "客厅") {
                            Text(
                                text = "当前亮度: $brightness%",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        if (device.type == "客厅") {
                            Text(
                                text = "当前温度: ${temperature}°C",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    )
}

