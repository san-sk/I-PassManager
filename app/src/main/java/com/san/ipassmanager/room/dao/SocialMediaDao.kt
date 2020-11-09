package com.san.ipassmanager.room.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.san.ipassmanager.room.entity.SocialMediaEntity

@Dao
interface SocialMediaDao {

    @Query("SELECT * from tbl_social_media_credentials ORDER BY name ASC")
    fun getSocialMediaCredentials(): LiveData<List<SocialMediaEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSocialMediaCredential(socialMediaEntity: SocialMediaEntity)

    @Update()
    suspend fun updateSocialMediaCredential(socialMediaEntity: SocialMediaEntity)

    @Query("DELETE FROM tbl_social_media_credentials")
    suspend fun deleteAll()

    @Query("SELECT * FROM tbl_social_media_credentials WHERE email_id = :id")
    fun getSocialMediaCredential(id: String): LiveData<SocialMediaEntity>

    @Delete()
    suspend fun deleteSocialMediaCredential(socialMediaEntity: SocialMediaEntity)

}