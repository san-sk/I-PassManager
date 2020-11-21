package com.san.ipassmanager.di

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.san.ipassmanager.R
import com.san.ipassmanager.interfaces.FragmentInterface
import com.san.ipassmanager.retrofit.Api
import com.san.ipassmanager.utils.Constants
import com.san.ipassmanager.utils.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object GeneralModule {

    //sharedpref module
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(Constants.TAG, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideSessionManager(sharedPreferences: SharedPreferences) =
        SessionManager(sharedPreferences)


    @Provides
    fun provideCircularProgressDrawable(@ApplicationContext context: Context) =
        CircularProgressDrawable(context).apply {
            strokeWidth = 15f
            centerRadius = 75f
            setColorSchemeColors(ContextCompat.getColor(context, R.color.secondaryColor))
            start()
        }


}