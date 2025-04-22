package org.minus.minuscenter

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import org.minus.minuscenter.iotdevice.Device
import org.minus.minuscenter.ui.theme.MinusCenterTheme
import org.minus.minuscenter.viewmodel.DeviceViewModel
import androidx.compose.runtime.Composable


class AddDeviceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinusCenterTheme {
                enableEdgeToEdge()
                AddDeviceScreen { device ->
                    // 创建返回结果
                    val resultIntent = Intent().apply {
                        putExtra("deviceName", device.name)
                        putExtra("deviceDescription", device.description)
                        putExtra("deviceType", device.type)
                        putExtra("deviceIpAddress", device.ipAddress)
                    }
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDeviceScreen(onDeviceAdded: (Device) -> Unit) {
    // 获取 Context
    val context = LocalContext.current

    // 初始化 SettingsProvider
    val settingsProvider = remember { SettingsProvider(context) }

    // 设备名称状态
    var deviceName by remember { mutableStateOf("物联网设备01") } // 初始化设备名称
    // 设备描述状态
    var deviceDescription by remember { mutableStateOf("设备描述") } // 初始化设备描述
    // 设备类型状态
    var selectedDeviceType by remember { mutableStateOf<String?>(null) }
    // 设备图标状态
    var selectedIcon by remember { mutableStateOf<ImageVector?>(null) } // 默认图标为空
    // 设备 IP 地址状态
    var ipAddress by remember { mutableStateOf("") }

    val viewModel = remember {
        ViewModelProvider(
            context as ComponentActivity,
            ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
        )[DeviceViewModel::class.java]
    }

    // 设备类型与对应图标映射
    val deviceTypesWithIcons = mapOf(
        "卧室" to Icons.Filled.Bed,
        "客厅" to Icons.Filled.CoffeeMaker,
        "厨房" to Icons.Filled.Kitchen,
        "浴室" to Icons.Filled.Bathtub
    )

    // 下拉菜单展开状态
    var expanded by remember { mutableStateOf(false) }

    // IP 地址验证函数
    fun isValidIp(ip: String): Boolean {
        val regex = Regex(
            "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\$"
        )
        return regex.matches(ip)
    }

    // Scaffold 布局
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = (context as Activity)::finish) {
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
            ) {
                // 标题部分
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp) // 底部间距
                ) {
                    Text(
                        text = "添加设备",
                        style = MaterialTheme.typography.headlineLarge, // 使用较大的字体
                        modifier = Modifier.align(Alignment.Center) // 水平和垂直居中
                    )
                }

                // 设备名称输入框
                OutlinedTextField(
                    value = deviceName,
                    onValueChange = { deviceName = it },
                    label = { Text("设备名称") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 设备描述输入框
                OutlinedTextField(
                    value = deviceDescription,
                    onValueChange = { deviceDescription = it },
                    label = { Text("设备描述") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 设备 IP 地址输入框
                OutlinedTextField(
                    value = ipAddress,
                    onValueChange = { ipAddress = it },
                    label = { Text("设备 IP 地址") },
                    isError = ipAddress.isNotEmpty() && !isValidIp(ipAddress),
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = {
                        if (ipAddress.isNotEmpty() && !isValidIp(ipAddress)) {
                            Text("请输入有效的 IP 地址", color = MaterialTheme.colorScheme.error)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 设备类型选择部分
                Text(text = "选择房间类型", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))

                // 使用 Box 包裹选择框和下拉菜单
                Box {
                    // 设备类型选择框
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true }
                            .padding(8.dp)
                    ) {
                        if (selectedIcon != null) {
                            Icon(
                                imageVector = selectedIcon!!,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp).align(Alignment.CenterVertically)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(
                            text = selectedDeviceType ?: "请选择房间类型",
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Default.ExpandMore,
                            contentDescription = "展开菜单"
                        )
                    }

                    // 处理下拉菜单显示
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .widthIn(max = 300.dp) // 限制宽度，稍微小一点
                            .fillMaxWidth()
                            .padding(top = 8.dp) // 确保下拉菜单与选择框之间有适当的间距
                            .clip(RoundedCornerShape(8.dp)) // 圆角
                            .background(MaterialTheme.colorScheme.surface) // 背景色
                    ) {
                        // 循环展示所有设备类型项
                        deviceTypesWithIcons.forEach { (type, icon) ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedDeviceType = type
                                    selectedIcon = icon
                                    expanded = false
                                },
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = icon,
                                            contentDescription = null,
                                            modifier = Modifier.size(24.dp).align(Alignment.CenterVertically)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(text = type)
                                    }
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 提交按钮
                Button(
                    onClick = {
                        if (isValidIp(ipAddress) && selectedDeviceType != null) {
                            // 创建设备对象
                            val newDevice = Device(
                                name = deviceName,
                                type = selectedDeviceType!!,
                                description = deviceDescription,
                                ipAddress = ipAddress
                            )
                            viewModel.insertDevice(newDevice)
                            onDeviceAdded(newDevice)
                        } else {
                            Toast.makeText(context, "请填写完整信息并输入有效的 IP 地址", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("提交")
                }
            }
        }
    )
}


