package com.san.ipassmanager.other

import android.os.Build
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatDelegate


object ThemeHelper {

    val LIGHT_MODE = "light"
    val DARK_MODE = "dark"
    val DEFAULT_MODE = "default"

    fun applyTheme(@NonNull themePref: String?) {
        when (themePref) {
            LIGHT_MODE -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            DARK_MODE -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            DEFAULT_MODE -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
            }
        }
    }
}