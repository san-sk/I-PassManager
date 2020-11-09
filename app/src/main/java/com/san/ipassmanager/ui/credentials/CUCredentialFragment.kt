package com.san.ipassmanager.ui.credentials

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.google.android.material.transition.MaterialContainerTransform
import com.san.ipassmanager.R
import com.san.ipassmanager.databinding.FragmentCuCredentialBinding
import com.san.ipassmanager.room.entity.AllCredentialEntity
import com.san.ipassmanager.utils.Constants
import com.san.ipassmanager.utils.SessionManager
import com.san.ipassmanager.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CUCredentialFragment : Fragment() {

    private lateinit var binding: FragmentCuCredentialBinding

    private val viewModel by viewModels<CUCredentialViewModel>()

    @Inject
    lateinit var sessionManager: SessionManager


    private val args: CUCredentialFragmentArgs by navArgs()

    private val credentialTypeArrayAdapter by lazy {
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.credential_categories,
                R.layout.support_simple_spinner_dropdown_item
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        }
    }

    private var credentialEntity = AllCredentialEntity()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition = MaterialContainerTransform()

        /* val binding: FragmentCuCredentialBinding =
             DataBindingUtil.inflate(inflater, R.layout.fragment_cu_credential, container, false)
 
         binding.viewmodel = viewModel
 
         return binding.root*/
        // return inflater.inflate(R.layout.fragment_cu_credential, container, false)

        binding = FragmentCuCredentialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.let {
            it.setSupportActionBar(binding.fcuToolbar.materialToolbar)
            NavigationUI.setupActionBarWithNavController(it, findNavController())
        }

        enableDisableViews(args.credential?.credentialType.orEmpty())

        binding.credentialType.adapter = credentialTypeArrayAdapter
        binding.credentialType.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?, arg1: View,
                arg2: Int, arg3: Long
            ) {
                enableDisableViews(binding.credentialType.selectedItem.toString())
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {

            }
        }


        if (args.purpose == Constants.UPDATE) {
            args.credential?.let {
                binding.credentialType?.setSelection(credentialTypeArrayAdapter!!.getPosition(args.credential?.credentialType))
                binding.etAddName.setText(it.name)
                binding.etAddWebsiteLink.setText(it.websiteLink)
                binding.etAddEmailId.setText(it.emailId)
                binding.etDeviceName?.setText(it.deviceName)
                binding.etSsid.setText(it.ssid)
                binding.etPlatformName.setText(it.platformName)
                binding.etAddUsername.setText(it.userName)
                binding.etAddPassword.setText(it.password)
                binding.etAddMobileNo?.setText(it.mobileNo)
                binding.etAddDesc.setText(it.description)

            }
        }


        binding.mbtnFdcSubmit.setOnClickListener {

            credentialEntity.apply {
                credentialType = binding.credentialType?.selectedItem.toString()
                name = binding.etAddName.text.toString()
                emailId = binding.etAddEmailId.text.toString()
                websiteLink = binding.etAddWebsiteLink.text.toString()
                platformName = binding.etPlatformName.text.toString()
                ssid = binding.etSsid.text.toString()
                deviceName = binding.etSsid.text.toString()
                userName = binding.etAddUsername.text.toString()
                password = binding.etAddPassword.text.toString()
                mobileNo = binding.etAddMobileNo?.text.toString()
                description = binding.etAddDesc.text.toString()
            }

            when (args.purpose) {

                Constants.CREATE -> {
                    addCredentials()
                }
                Constants.UPDATE -> {
                    updateCredentials()
                }
            }


        }


    }


    private fun addCredentials() {

        viewModel.insert(credentialEntity)

        findNavController().navigateUp()
    }


    private fun updateCredentials() {

        viewModel.update(credentialEntity)
        findNavController().navigateUp()
    }


    private fun enableDisableViews(type: String) {

        when (type) {

            "All" -> {
                Utils.makeVisible(
                    listOf(
                        binding.tilAddName,
                        binding.tilAddWebsiteLink,
                        binding.tilAddEmailId,
                        binding.tilDeviceName,
                        binding.tilSsid,
                        binding.tilPlatformName,
                        binding.tilAddUsername,
                        binding.tilAddPassowrd,
                        binding.tilAddMobileNo,
                        binding.tilAddDesc
                    )
                )
            }
            "Email", "Website", "Social Media" -> {

                Utils.makeVisible(
                    listOf(
                        binding.tilAddName,
                        binding.tilAddWebsiteLink,
                        binding.tilAddEmailId,
                        binding.tilAddUsername,
                        binding.tilAddPassowrd,
                        binding.tilAddMobileNo,
                        binding.tilAddDesc
                    )
                )

                Utils.makeGone(
                    listOf(
                        binding.tilDeviceName,
                        binding.tilSsid,
                        binding.tilPlatformName,
                    )
                )

            }
            "Bank" -> {

            }
            "Network" -> {

                Utils.makeVisible(
                    listOf(
                        binding.tilAddName,
                        binding.tilDeviceName,
                        binding.tilSsid,
                        binding.tilAddPassowrd,
                        binding.tilAddDesc
                    )
                )

                Utils.makeGone(
                    listOf(
                        binding.tilAddWebsiteLink,
                        binding.tilAddEmailId,
                        binding.tilAddUsername,
                        binding.tilPlatformName,
                        binding.tilAddMobileNo,
                    )
                )
            }
            "OTT Platform" -> {
                Utils.makeVisible(
                    listOf(
                        binding.tilAddName,
                        binding.tilAddWebsiteLink,
                        binding.tilAddEmailId,
                        binding.tilPlatformName,
                        binding.tilAddUsername,
                        binding.tilAddPassowrd,
                        binding.tilAddMobileNo,
                        binding.tilAddDesc
                    )
                )

                Utils.makeGone(
                    listOf(
                        binding.tilDeviceName,
                        binding.tilSsid,
                    )
                )
            }
            "Device" -> {

                Utils.makeVisible(
                    listOf(
                        binding.tilAddName,
                        binding.tilDeviceName,
                        binding.tilAddUsername,
                        binding.tilAddPassowrd,
                        binding.tilAddDesc
                    )
                )

                Utils.makeGone(
                    listOf(
                        binding.tilSsid,
                        binding.tilAddWebsiteLink,
                        binding.tilAddEmailId,
                        binding.tilPlatformName,
                        binding.tilAddMobileNo,
                    )
                )
            }
            "Others" -> {
            }
        }

    }
}