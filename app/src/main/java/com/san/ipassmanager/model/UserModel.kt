package com.san.ipassmanager.model

import com.google.api.services.drive.DriveScopes


data class UserModel(
    val email: String? = null,
    val username: String? = null,
    val password: String? = null,
    val driveFileId: String? = null,
    val grantedScopes: String? = null,
    val requestedScopes: String? = null,
    val idToken: String? = null,
    val id: String? = null,
    val serverAuthCode: String? = null,
    val photoUrl: String? = null,
    val isExpired: Boolean? = null
)


