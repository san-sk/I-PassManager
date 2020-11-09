package com.san.ipassmanager.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.san.ipassmanager.R
import kotlinx.android.synthetic.main.layout_toolbar_normal.*

class SettingsContainerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_settings_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.let {
            it.setSupportActionBar(material_toolbar)
            NavigationUI.setupActionBarWithNavController(it, findNavController())
        }

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fl_settings_container, SettingsFragment())
            .commit()


    }
}