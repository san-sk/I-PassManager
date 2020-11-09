package com.san.ipassmanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.san.ipassmanager.databinding.FragmentOptionsBinding
import com.san.ipassmanager.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionsFragment : BottomSheetDialogFragment() {


    private lateinit var binding: FragmentOptionsBinding

    private val viewModel by viewModels<OptionsViewModel>()
    private val commonViewModel by viewModels<CommonViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /* val binding: FragmentOptionsBinding =
             DataBindingUtil.inflate(inflater, R.layout.fragment_options, container, false)

         binding.viewmodel = viewModel

         return binding.root*/
        // return inflater.inflate(R.layout.fragment_options, container, false)

        binding = FragmentOptionsBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        
        binding.tvFoBackup.setOnClickListener {

            findNavController().navigate(
                OptionsFragmentDirections.actionOptionsFragmentToBackupRestoreFragment(
                    false,
                    Constants.BACKUP
                )
            )
        }

        binding.tvFoRestore.setOnClickListener {
            findNavController().navigate(
                OptionsFragmentDirections.actionOptionsFragmentToBackupRestoreFragment(
                    true,
                    Constants.RESTORE
                )
            )
        }

        binding.tvFoProfile.setOnClickListener {

        }

        binding.tvFoSettings.setOnClickListener {

            findNavController().navigate(OptionsFragmentDirections.actionOptionsFragmentToSettingsContainerFragment())
        }


        binding.tvFoLogout.setOnClickListener {

            commonViewModel.logOut()

            findNavController().navigate(OptionsFragmentDirections.actionOptionsFragmentToLoginFragment3())

        }

    }


}