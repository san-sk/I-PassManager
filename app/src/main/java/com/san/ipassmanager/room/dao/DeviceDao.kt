package com.san.ipassmanager.room.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.san.ipassmanager.room.entity.DeviceEntity

@Dao
interface DeviceDao {

    @Query("SELECT * from tbl_device_credentials ORDER BY name ASC")
    fun getDeviceCredentials(): LiveData<List<DeviceEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDeviceCredential(deviceEntity: DeviceEntity)

    @Update()
    suspend fun updateDeviceCredential(deviceEntity: DeviceEntity)

    @Query("DELETE FROM tbl_device_credentials")
    suspend fun deleteAll()

    @Query("SELECT * FROM tbl_device_credentials WHERE user_name = :userName")
    fun getCredential(userName: String): LiveData<DeviceEntity>

    @Delete()
    suspend fun deleteDeviceCredential(deviceEntity: DeviceEntity)

}