package com.san.ipassmanager.ui.credentials

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.ipassmanager.repository.CredentialRepository
import com.san.ipassmanager.room.entity.AllCredentialEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CUCredentialViewModel @ViewModelInject constructor(
    private val credentialRepository: CredentialRepository
) : ViewModel() {


    fun insert(credentialEntity: AllCredentialEntity) = viewModelScope.launch(Dispatchers.IO) {
        credentialRepository.insert(credentialEntity)
    }

    fun update(credentialEntity: AllCredentialEntity) = viewModelScope.launch(Dispatchers.IO) {
        credentialRepository.update(credentialEntity)
    }

}