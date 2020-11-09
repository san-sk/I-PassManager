package com.san.ipassmanager.ui.credentials

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.ipassmanager.repository.CredentialRepository
import com.san.ipassmanager.room.entity.AllCredentialEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RDCredentialViewModel @ViewModelInject constructor(
    private val credentialRepository: CredentialRepository
) : ViewModel() {


    fun delete(credentialEntity: AllCredentialEntity) = viewModelScope.launch(Dispatchers.IO) {
        credentialRepository.delete(credentialEntity)
    }


    fun getCredential(id: String) = credentialRepository.getCredential(id)


}