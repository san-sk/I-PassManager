package com.san.ipassmanager.ui.credentials

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.palette.graphics.Palette
import com.san.ipassmanager.R
import com.san.ipassmanager.databinding.FragmentRdCredentialBinding
import com.san.ipassmanager.room.entity.AllCredentialEntity
import com.san.ipassmanager.utils.Constants
import com.san.ipassmanager.utils.Utils
import com.san.ipassmanager.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RDCredentialFragment : Fragment() {

    private lateinit var binding: FragmentRdCredentialBinding

    private val viewModel by viewModels<RDCredentialViewModel>()

    private val args: RDCredentialFragmentArgs by navArgs()

    private var credential = AllCredentialEntity()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /* sharedElementEnterTransition = MaterialContainerTransform()
         val binding: FragmentRdCredentialBinding =
             DataBindingUtil.inflate(inflater, R.layout.fragment_rd_credential, container, false)
 
         binding.viewmodel = viewModel
 
         return binding.root*/
        binding = FragmentRdCredentialBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        (activity as? AppCompatActivity)?.let {
            it.setSupportActionBar(binding.frdToolbar.materialToolbar)
            NavigationUI.setupActionBarWithNavController(it, findNavController())
        }

        displayCredentials()


        binding.bottomAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
        }

        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.delete -> {
                    // Handle search icon press
                    viewModel.delete(credential)
                    findNavController().navigateUp()
                    true
                }
                /* R.id.more -> {
                     // Handle more item (inside overflow menu) press
                     true
                 }*/
                else -> false
            }
        }

        binding.fabFv.setOnClickListener {

            findNavController().navigate(
                RDCredentialFragmentDirections.actionViewCredentialFragmentToAddCredentialFragment(
                    credential,
                    Constants.UPDATE
                )
            )
        }

    }

    private fun displayCredentials() {

        args.credential.name.let { id ->

            viewModel.getCredential(id)
                .observe(viewLifecycleOwner, Observer { credentials ->

                    credentials?.let { it ->

                        enableDisableViews(it.credentialType.orEmpty())
                        credential = it

                        binding.ivVcImage.loadImage("https://${it.websiteLink}/favicon.ico")
                        binding.tvVcTitle.text = it.name
                        binding.tvVcEmail.text = it.emailId
                        binding.tvVcWebsite.text = it.websiteLink
                        binding.tvVcPlatformName.text = it.platformName
                        binding.tvVcDevicename.text = it.deviceName
                        binding.tvVcSsid.text = it.ssid
                        binding.tvVcUsername.text = it.userName
                        binding.tvVcPassword.text = it.password
                        binding.tvVcMobileNo.text = it.mobileNo
                        binding.tvVcDescription.text = it.description
                    }


                })

        }

        binding.ivVcImage.drawable?.toBitmap()?.let { bmp ->

            Palette.from(bmp).generate().lightVibrantSwatch?.let {

                binding.frdToolbar.materialToolbar.setBackgroundColor(
                    (it.rgb)
                )

            }


        }


    }


    private fun enableDisableViews(type: String) {

        when (type) {

            "All" -> {
                Utils.makeVisible(
                    listOf(
                        binding.tvVcPlatformNameLabel, binding.tvVcPlatformName,
                        binding.tvVcDeviceNameLabel, binding.tvVcDevicename,
                        binding.tvVcSsidLabel, binding.tvVcSsid,
                        binding.tvVcUsernameLabel, binding.tvVcUsername,
                        binding.tvVcEmailIdLabel, binding.tvVcEmail,
                        binding.tvVcPasswordLabel, binding.tvVcPassword,
                        binding.tvVcMobileNoLabel, binding.tvVcMobileNo
                    )
                )
            }
            "Email", "Website", "Social Media" -> {

                Utils.makeVisible(
                    listOf(
                        binding.tvVcUsernameLabel, binding.tvVcUsername,
                        binding.tvVcPasswordLabel, binding.tvVcPassword,
                        binding.tvVcEmailIdLabel, binding.tvVcEmail,
                        binding.tvVcMobileNoLabel, binding.tvVcMobileNo
                    )
                )

                Utils.makeGone(
                    listOf(
                        binding.tvVcPlatformNameLabel, binding.tvVcPlatformName,
                        binding.tvVcDeviceNameLabel, binding.tvVcDevicename,
                        binding.tvVcSsidLabel, binding.tvVcSsid
                    )
                )

            }
            "Bank" -> {

            }
            "Network" -> {

                Utils.makeVisible(
                    listOf(
                        binding.tvVcSsidLabel, binding.tvVcSsid,
                        binding.tvVcUsernameLabel, binding.tvVcUsername,
                        binding.tvVcPasswordLabel, binding.tvVcPassword
                    )
                )

                Utils.makeGone(
                    listOf(
                        binding.tvVcPlatformNameLabel, binding.tvVcPlatformName,
                        binding.tvVcDeviceNameLabel, binding.tvVcDevicename,
                        binding.tvVcEmailIdLabel, binding.tvVcEmail,
                        binding.tvVcMobileNoLabel, binding.tvVcMobileNo
                    )
                )
            }
            "OTT Platform" -> {
                Utils.makeVisible(
                    listOf(
                        binding.tvVcPlatformNameLabel, binding.tvVcPlatformName,
                        binding.tvVcUsernameLabel, binding.tvVcUsername,
                        binding.tvVcEmailIdLabel, binding.tvVcEmail,
                        binding.tvVcPasswordLabel, binding.tvVcPassword,
                        binding.tvVcMobileNoLabel, binding.tvVcMobileNo
                    )
                )

                Utils.makeGone(
                    listOf(
                        binding.tvVcDeviceNameLabel, binding.tvVcDevicename,
                        binding.tvVcSsidLabel, binding.tvVcSsid
                    )
                )
            }
            "Device" -> {

                Utils.makeVisible(
                    listOf(
                        binding.tvVcDeviceNameLabel, binding.tvVcDevicename,
                        binding.tvVcUsernameLabel, binding.tvVcUsername,
                        binding.tvVcPasswordLabel, binding.tvVcPassword
                    )
                )

                Utils.makeGone(
                    listOf(
                        binding.tvVcPlatformNameLabel, binding.tvVcPlatformName,
                        binding.tvVcSsidLabel, binding.tvVcSsid,
                        binding.tvVcEmailIdLabel, binding.tvVcEmail,
                        binding.tvVcMobileNoLabel, binding.tvVcMobileNo
                    )
                )
            }
            "Others" -> {
            }
        }

    }
}