package com.san.ipassmanager.room.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.san.ipassmanager.room.entity.ServerEntity

@Dao
interface ServerDao {

    @Query("SELECT * from tbl_server_credentials ORDER BY name ASC")
    fun getServerCredentials(): LiveData<List<ServerEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertServerCredential(serverEntity: ServerEntity)

    @Update()
    suspend fun updateServerCredential(serverEntity: ServerEntity)

    @Query("DELETE FROM tbl_server_credentials")
    suspend fun deleteAll()

    @Query("SELECT * FROM tbl_server_credentials WHERE email_id = :id")
    fun getServerCredential(id: String): LiveData<ServerEntity>

    @Delete()
    suspend fun deleteServerCredential(serverEntity: ServerEntity)

}