package com.san.ipassmanager.ui

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.model.KeyPath
import com.san.ipassmanager.R
import com.san.ipassmanager.databinding.ActivityMainBinding
import com.san.ipassmanager.interfaces.FragmentInterface
import com.san.ipassmanager.other.ThemeHelper
import com.san.ipassmanager.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentInterface {

    private lateinit var binding: ActivityMainBinding

    private val navControllerMain by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    @Inject
    lateinit var sessionManager: SessionManager

    private val viewModel by viewModels<CommonViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // nav_view?.itemIconTintList = null
        ThemeHelper.applyTheme(sessionManager.theme)

    }


    override fun onSupportNavigateUp(): Boolean {
        return navControllerMain.navigateUp() || super.onSupportNavigateUp()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun setProgressBarVisible(shouldProgressBarBeVisible: Boolean) {

        clpb_main.addValueCallback(
            KeyPath("Heart Icon Outlines 2", "**"),
            LottieProperty.COLOR_FILTER,
            {
                PorterDuffColorFilter(
                    resources.getColor(R.color.secondaryColor),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        )


        if (shouldProgressBarBeVisible) {
            binding.clpbMain.visibility = View.VISIBLE
        } else {
            binding.clpbMain.visibility = View.GONE
        }
    }

}