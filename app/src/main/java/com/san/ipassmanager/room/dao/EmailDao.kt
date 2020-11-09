package com.san.ipassmanager.room.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.san.ipassmanager.room.entity.EmailEntity

@Dao
interface EmailDao {

    @Query("SELECT * from tbl_email_credentials ORDER BY name ASC")
    fun getEmailCredentials(): LiveData<List<EmailEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEmailCredential(emailEntity: EmailEntity)

    @Update()
    suspend fun updateEmailCredential(emailEntity: EmailEntity)

    @Query("DELETE FROM tbl_email_credentials")
    suspend fun deleteAll()

    @Query("SELECT * FROM tbl_email_credentials WHERE email_id = :id")
    fun getEmailCredential(id: String): LiveData<EmailEntity>

    @Delete()
    suspend fun deleteEmailCredential(emailEntity: EmailEntity)

}