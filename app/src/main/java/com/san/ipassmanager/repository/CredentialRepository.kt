package com.san.ipassmanager.repository

import androidx.lifecycle.LiveData
import com.san.ipassmanager.room.dao.AllCredentialDao
import com.san.ipassmanager.room.entity.AllCredentialEntity
import javax.inject.Inject


class CredentialRepository @Inject constructor(private val credentialDao: AllCredentialDao) {

    val allCredentials: LiveData<List<AllCredentialEntity>> = credentialDao.getCredentials()

    fun getCredential(id: String) = credentialDao.getCredential(id)

    fun getTypeOfCredentials(type: String) = credentialDao.getTypeOfCredentials(type)


    fun searchCredential(queryString: String) = credentialDao.searchCredential(queryString)

    suspend fun insert(credentialEntity: AllCredentialEntity) {
        credentialDao.insertCredential(credentialEntity)
    }

    suspend fun update(credentialEntity: AllCredentialEntity) {
        credentialDao.updateCredential(credentialEntity)
    }

    suspend fun delete(credentialEntity: AllCredentialEntity) {
        credentialDao.deleteCredential(credentialEntity)
    }

    suspend fun deleteAll() {
        credentialDao.deleteAll()
    }

}