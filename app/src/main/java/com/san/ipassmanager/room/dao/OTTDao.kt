package com.san.ipassmanager.room.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.san.ipassmanager.room.entity.OTTEntity

@Dao
interface OTTDao {

    @Query("SELECT * from tbl_ott_credentials ORDER BY name ASC")
    fun getOTTCredentials(): LiveData<List<OTTEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOTTCredential(ottEntity: OTTEntity)

    @Update()
    suspend fun updateOTTCredential(ottEntity: OTTEntity)

    @Query("DELETE FROM tbl_ott_credentials")
    suspend fun deleteAll()

    @Query("SELECT * FROM tbl_ott_credentials WHERE name = :id")
    fun getOTTCredential(id: String): LiveData<OTTEntity>

    @Delete()
    suspend fun deleteOTTCredential(ottEntity: OTTEntity)

}