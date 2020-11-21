package com.san.ipassmanager.interfaces

import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent


interface FragmentInterface {

    fun setProgressBarVisible(shouldProgressBarBeVisible: Boolean)

}