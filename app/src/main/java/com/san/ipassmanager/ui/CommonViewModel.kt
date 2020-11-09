package com.san.ipassmanager.ui

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.services.drive.DriveScopes
import com.san.ipassmanager.R
import com.san.ipassmanager.repository.CredentialRepository
import com.san.ipassmanager.room.database.CredentialsDatabase
import com.san.ipassmanager.room.entity.AllCredentialEntity
import com.san.ipassmanager.utils.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommonViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val credentialRepository: CredentialRepository,
    private val sessionManager: SessionManager,
    private val database: CredentialsDatabase
) : ViewModel() {


    val allCredentials: LiveData<List<AllCredentialEntity>> = credentialRepository.allCredentials

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        credentialRepository.deleteAll()
    }

    fun insertList(credentialEntity: List<AllCredentialEntity>) =
        viewModelScope.launch(Dispatchers.IO) {

            credentialEntity.forEach {

                credentialRepository.insert(it)
            }

        }


    fun isLoggedIn() =
        GoogleSignIn.getLastSignedInAccount(context)?.account != null && sessionManager.driveFileId != null

    fun logOut() {

        database.close()
        database.clearAllTables()

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .requestScopes(Scope(DriveScopes.DRIVE_FILE))
                .requestScopes(Scope(DriveScopes.DRIVE_APPDATA))
                .build()

        GoogleSignIn.getClient(context, gso)?.signOut()

        sessionManager.clear()

    }

}