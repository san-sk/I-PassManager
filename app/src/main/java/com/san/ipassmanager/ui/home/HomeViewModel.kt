package com.san.ipassmanager.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.san.ipassmanager.repository.CredentialRepository
import com.san.ipassmanager.repository.TestRepository
import com.san.ipassmanager.room.entity.AllCredentialEntity

class HomeViewModel @ViewModelInject constructor(
    private val credentialRepository: CredentialRepository,
    private val testRepository: TestRepository
) : ViewModel() {


    fun getTypeOfCredentials(type: String) = credentialRepository.getTypeOfCredentials(type)

    val allCredentials: LiveData<List<AllCredentialEntity>> = credentialRepository.allCredentials

    fun fetchTestResponse() = testRepository.fetchTestResponse()

    fun fetchTestResponse2() = testRepository.fetchTestResponse2()
}