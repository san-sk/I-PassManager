package com.san.ipassmanager.utils

import android.content.SharedPreferences


class SessionManager(private val prefs: SharedPreferences) {


    fun clear() {
        prefs.edit().clear().apply()
    }

    var userId: String?
        get() {
            return prefs.getString("userId", "")
        }
        set(userId) {
            prefs.edit().putString("userId", userId).apply()
        }

    var password: String?
        get() {
            return prefs.getString("password", null)
        }
        set(password) {
            prefs.edit().putString("password", password).apply()
        }

    var driveFileId: String?
        get() {
            return prefs.getString("driveFileId", null)
        }
        set(driveFileId) {
            prefs.edit().putString("driveFileId", driveFileId).apply()
        }

    var accountType: String?
        get() {
            return prefs.getString("accountType", null)
        }
        set(accountType) {
            prefs.edit().putString("accountType", accountType).apply()
        }

    var fcmToken: String?
        get() {
            return prefs.getString("fcm_token", "")
        }
        set(fcmToken) {
            prefs.edit().putString("fcm_token", fcmToken).apply()
        }


    var theme: String?
        get() {
            return prefs.getString("theme", "default")
        }
        set(theme) {
            prefs.edit().putString("theme", theme).apply()
        }

}
