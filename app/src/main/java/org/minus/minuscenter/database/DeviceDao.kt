// DeviceDao.kt
package org.minus.minuscenter.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.minus.minuscenter.iotdevice.Device

@Dao
interface DeviceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(device: Device)

    @Delete
    suspend fun delete(device: Device)

    @Query("SELECT * FROM devices ORDER BY id ASC")
    fun getAllDevices(): Flow<List<Device>>
}
