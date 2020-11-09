package com.san.ipassmanager.room.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.san.ipassmanager.room.entity.OTTEntity
import com.san.ipassmanager.room.entity.OthersEntity

@Dao
interface OthersDao {

    @Query("SELECT * from tbl_other_credentials ORDER BY name ASC")
    fun getOTTCredentials(): LiveData<List<OTTEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOTTCredential(ottEntity: OthersEntity)

    @Update()
    suspend fun updateOTTCredential(ottEntity: OthersEntity)

    @Query("DELETE FROM tbl_other_credentials")
    suspend fun deleteAll()

    @Query("SELECT * FROM tbl_other_credentials WHERE name = :id")
    fun getOTTCredential(id: String): LiveData<OthersEntity>

    @Delete()
    suspend fun deleteOTTCredential(ottEntity: OthersEntity)

}