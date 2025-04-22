package org.minus.minuscenter

import android.content.Context

class SettingsProvider(private val context: Context) {
    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    var deviceName: String
        get() {
            return prefs.getString("deviceName", context.getString(R.string.device_name_default_value)) ?: "Default Device"
        }
        set(value) {
            prefs.edit().putString("deviceName", value).apply()
        }

    var deviceType: String
        get() {
            return prefs.getString("deviceType", "Unknown") ?: "Unknown"
        }
        set(value) {
            prefs.edit().putString("deviceType", value).apply()
        }

    var deviceIpAddress: String
        get() {
            return prefs.getString("deviceIpAddress", "192.168.1.100") ?: "192.168.1.100" // 默认IP
        }
        set(value) {
            prefs.edit().putString("deviceIpAddress", value).apply()
        }

    var deviceDescription: String
        get() {
            return prefs.getString("deviceDescription", "这是一个默认设备描述。") ?: "这是一个默认设备描述。"
        }
        set(value) {
            prefs.edit().putString("deviceDescription", value).apply()
        }
}
