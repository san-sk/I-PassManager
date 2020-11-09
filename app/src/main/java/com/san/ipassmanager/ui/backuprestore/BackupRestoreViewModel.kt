package com.san.ipassmanager.ui.backuprestore

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.san.ipassmanager.repository.CredentialRepository
import com.san.ipassmanager.room.entity.AllCredentialEntity

class BackupRestoreViewModel @ViewModelInject constructor(private val credentialRepository: CredentialRepository) :
    ViewModel() {


    fun getTypeOfCredentials(type: String) = credentialRepository.getTypeOfCredentials(type)

    val allCredentials: LiveData<List<AllCredentialEntity>> = credentialRepository.allCredentials

}