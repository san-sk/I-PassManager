package com.san.ipassmanager.room.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.san.ipassmanager.room.entity.NetworkEntity

@Dao
interface NetworkDao {

    @Query("SELECT * from tbl_network_credentials ORDER BY name ASC")
    fun getNetworkCredentials(): LiveData<List<NetworkEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNetworkCredential(networkEntity: NetworkEntity)

    @Update()
    suspend fun updateNetworkCredential(networkEntity: NetworkEntity)

    @Query("DELETE FROM tbl_network_credentials")
    suspend fun deleteAll()

    @Query("SELECT * FROM tbl_network_credentials WHERE wifi_ssid = :ssid")
    fun getNetworkCredential(ssid: String): LiveData<NetworkEntity>

    @Delete()
    suspend fun deleteNetworkCredential(networkEntity: NetworkEntity)

}