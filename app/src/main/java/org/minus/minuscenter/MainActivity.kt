package org.minus.minuscenter

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.minus.minuscenter.ui.theme.MinusCenterTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.unit.dp
import org.minus.minuscenter.ui.cards.DeviceCard
import androidx.lifecycle.ViewModelProvider
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import org.minus.minuscenter.iotdevice.Device
import org.minus.minuscenter.viewmodel.DeviceViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 注册ActivityResultLauncher
        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

        setContent {
            MinusCenterTheme {
                val viewModel = ViewModelProvider(
                    this,
                    ViewModelProvider.AndroidViewModelFactory.getInstance(application)
                )[DeviceViewModel::class.java]

                val deviceList by viewModel.allDevices.collectAsState(initial = emptyList())

                MainActivityContent(devices = deviceList, launcher = launcher)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityContent(
    devices: List<Device>,
    launcher: ActivityResultLauncher<Intent>
) {
    val context = LocalContext.current

    // 渐变背景定义
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF42A5F5),  // 深蓝
            Color(0xFFBBDEFB), // 浅蓝
            Color(0xFFF1F7FF), // 中蓝
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush) // 设置背景为渐变色
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                    actions = {
                        IconButton(onClick = {
                            launcher.launch(Intent(context, AddDeviceActivity::class.java))
                        }) {
                            Icon(Icons.Filled.Add, contentDescription = "Add Device")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White,  // 可根据背景调整文字颜色
                        actionIconContentColor = Color.White
                    )
                )

            },
            bottomBar = {
                NavigationBar(
                    containerColor = Color.Transparent, // 让渐变背景透出
                    tonalElevation = 0.dp
                ) {
                    val selectedColor = Color(0xFF1A237E) // 选中图标/文字颜色（深蓝灰色）
                    val unselectedColor = Color(0xFF424242) // 未选中颜色（深灰）

                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                        label = { Text("首页") },
                        selected = true,
                        onClick = { },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = selectedColor,
                            unselectedIconColor = unselectedColor,
                            selectedTextColor = selectedColor,
                            unselectedTextColor = unselectedColor,
                            indicatorColor = selectedColor.copy(alpha = 0.12f) // 选中时的背景色
                        )
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Devices, contentDescription = "Devices") },
                        label = { Text("设备") },
                        selected = false,
                        onClick = { },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = selectedColor,
                            unselectedIconColor = unselectedColor,
                            selectedTextColor = selectedColor,
                            unselectedTextColor = unselectedColor,
                            indicatorColor = selectedColor.copy(alpha = 0.12f)
                        )
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                        label = { Text("设置") },
                        selected = false,
                        onClick = { },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = selectedColor,
                            unselectedIconColor = unselectedColor,
                            selectedTextColor = selectedColor,
                            unselectedTextColor = unselectedColor,
                            indicatorColor = selectedColor.copy(alpha = 0.12f)
                        )
                    )
                }


            },
            containerColor = Color.Transparent, // 让Scaffold背景透明以显示渐变
            contentColor = MaterialTheme.colorScheme.onBackground
        ) { innerPadding ->
            if (devices.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "点击右上角添加按钮来添加设备",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(devices.size) { index ->
                        val device = devices[index]
                        DeviceCard(
                            device = device,
                            onClick = {
                                val intent = Intent(context, DeviceControlActivity::class.java).apply {
                                    putExtra("device", device)
                                }
                                context.startActivity(intent)
                            }
                        )
                    }
                }
            }
        }
    }
}
