package com.san.ipassmanager.ui.credentials

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.palette.graphics.Palette
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.san.ipassmanager.BuildConfig
import com.san.ipassmanager.R
import com.san.ipassmanager.databinding.FragmentRdCredentialBinding
import com.san.ipassmanager.room.entity.AllCredentialEntity
import com.san.ipassmanager.utils.Constants
import com.san.ipassmanager.utils.Utils
import com.san.ipassmanager.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class RDCredentialFragment : Fragment(), View.OnClickListener {

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


        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.delete -> {
                    // Handle search icon press
                    showConfirmation()
                    true
                }
                R.id.share -> {
                    // Handle more item (inside overflow menu) press
                    shareCredential(Utils.getImage(binding.cl), args.credential.name)

                    true
                }
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

            Palette.from(bmp).generate().darkVibrantSwatch?.let {

                binding.frdToolbar.materialToolbar.setBackgroundColor(
                    (it.bodyTextColor)
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


    private fun copyToClipBoard(text: String) {

        val clipboard = context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(Constants.TAG, text)
        clipboard.setPrimaryClip(clip)

        if (clip != null) {
            Toasty.info(requireContext(), "Copied to clipboard", Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(v: View?) {

        if (v is TextView) {
            copyToClipBoard(v.text.toString())
            Log.e("view", v.text.toString())
        }
    }

    private fun shareCredential(bmp: Bitmap, fileName: String) {

        activity?.let {

            try {
                val file = File(context?.cacheDir, "$fileName.png")
                val fOut = FileOutputStream(file)
                bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut)
                fOut.flush()
                fOut.close()
                file.setReadable(true, true)
                val uri = FileProvider.getUriForFile(
                    it.applicationContext,
                    BuildConfig.APPLICATION_ID + ".fileprovider", file
                )
                val intent = Intent(Intent.ACTION_SEND)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra(Intent.EXTRA_STREAM, uri)
                intent.type = "image/png"
                startActivity(Intent.createChooser(intent, "Share Credential via"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun showConfirmation() {

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.app_name))
            .setMessage(resources.getString(R.string.delete_confirmation))
            .setNegativeButton("") { dialog, which ->
                dialog.dismiss()
            }
            .setNegativeButtonIcon(ResourcesCompat.getDrawable(resources,R.drawable.ic_no,null))
            .setPositiveButtonIcon(ResourcesCompat.getDrawable(resources,R.drawable.ic_check,null))
            .setPositiveButton(resources.getString(R.string.yes)) { dialog, which ->
                dialog.dismiss()
                viewModel.delete(credential)
                findNavController().navigateUp()
            }
            .show()

    }

}

