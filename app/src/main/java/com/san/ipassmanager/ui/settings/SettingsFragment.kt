package com.san.ipassmanager.ui.settings

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.*
import com.san.ipassmanager.R
import com.san.ipassmanager.other.ThemeHelper
import com.san.ipassmanager.ui.CommonViewModel
import com.san.ipassmanager.utils.Constants
import com.san.ipassmanager.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {


    @Inject
    lateinit var sessionManager: SessionManager

    private val viewModel by viewModels<CommonViewModel>()



    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        findPreference<SwitchPreferenceCompat>(Constants.NOTIFICATION)?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->

                when (newValue) {
                    true -> null//viewModel.enableNotification()
                    false -> null//viewModel.disableNotification()
                }

                true
            }


        findPreference<DialogPreference>(Constants.THEME)?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->

                when (newValue) {
                    "Dark" -> {
                        ThemeHelper.applyTheme("dark")
                        sessionManager.theme = "dark"
                    }
                    "Light" -> {
                        ThemeHelper.applyTheme("light")
                        sessionManager.theme = "light"
                    }
                    "System Default" -> {
                        ThemeHelper.applyTheme("default")
                        sessionManager.theme = "default"
                    }
                }
                true
            }
    }


}

