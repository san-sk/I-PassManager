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
import com.google.api.services.drive.model.User
import com.san.ipassmanager.R
import com.san.ipassmanager.model.UserModel
import com.san.ipassmanager.repository.CredentialRepository
import com.san.ipassmanager.room.database.CredentialsDatabase
import com.san.ipassmanager.room.entity.AllCredentialEntity
import com.san.ipassmanager.utils.Constants
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


    fun getUserDetails(): UserModel? {

        var user: UserModel? = null

        when (sessionManager.accountType) {

            Constants.LOCAL -> {

                user = UserModel(
                    username = sessionManager.userName,
                    password = sessionManager.password
                )

            }

            Constants.ONLINE -> {
                val acct = GoogleSignIn.getLastSignedInAccount(context)
                user = UserModel(
                    email = acct?.email,
                    username = sessionManager.userName,
                    password = sessionManager.password,
                    driveFileId = sessionManager.driveFileId,
                    grantedScopes = acct?.grantedScopes.toString(),
                    requestedScopes = acct?.requestedScopes.toString(),
                    id = acct?.id,
                    idToken = acct?.idToken,
                    serverAuthCode = acct?.serverAuthCode,
                    photoUrl = acct?.photoUrl.toString(),
                    isExpired = acct?.isExpired
                )
            }
        }

        return user

    }

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