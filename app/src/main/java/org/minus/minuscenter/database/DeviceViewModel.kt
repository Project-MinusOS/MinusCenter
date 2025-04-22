package org.minus.minuscenter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.minus.minuscenter.database.AppDatabase
import org.minus.minuscenter.iotdevice.Device

class DeviceViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).deviceDao()
    val allDevices: Flow<List<Device>> = dao.getAllDevices()

    fun insertDevice(device: Device) {
        viewModelScope.launch {
            dao.insert(device)
        }
    }
}
