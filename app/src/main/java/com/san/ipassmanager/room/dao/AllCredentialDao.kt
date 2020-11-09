package com.san.ipassmanager.room.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.san.ipassmanager.room.entity.AllCredentialEntity

@Dao
interface AllCredentialDao {

    @Query("SELECT * from tbl_all_credentials ORDER BY name ASC")
    fun getCredentials(): LiveData<List<AllCredentialEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCredential(credentialEntity: AllCredentialEntity)

    @Update()
    suspend fun updateCredential(credentialEntity: AllCredentialEntity)

    @Query("DELETE FROM tbl_all_credentials")
    suspend fun deleteAll()

    @Query("SELECT * FROM tbl_all_credentials WHERE name = :id")
    fun getCredential(id: String): LiveData<AllCredentialEntity>

    @Query("SELECT * FROM tbl_all_credentials WHERE name LIKE :queryText")
    fun searchCredential(queryText: String): LiveData<List<AllCredentialEntity>>

    @Query("SELECT * FROM tbl_all_credentials WHERE credential_type =:type")
    fun getTypeOfCredentials(type: String): LiveData<List<AllCredentialEntity>>

    @Delete()
    suspend fun deleteCredential(credentialEntity: AllCredentialEntity)

}