package com.san.ipassmanager.ui.credentials

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.san.ipassmanager.repository.CredentialRepository

class SearchViewModel @ViewModelInject constructor(
    private val credentialRepository: CredentialRepository
) : ViewModel() {


    fun searchCredential(searchQuery: String) = credentialRepository.searchCredential(searchQuery)

}