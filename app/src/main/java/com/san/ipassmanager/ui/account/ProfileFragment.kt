package com.san.ipassmanager.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.san.ipassmanager.R
import com.san.ipassmanager.databinding.FragmentProfileBinding
import com.san.ipassmanager.ui.CommonViewModel
import com.san.ipassmanager.utils.Constants
import com.san.ipassmanager.utils.SessionManager
import com.san.ipassmanager.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var sessionManager: SessionManager

    private val viewModel by viewModels<CommonViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_profile, container, false)
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getUserDetails()?.let {

            binding.ivFp.loadImage(it.photoUrl, R.drawable.ic_logo)
            binding.tvProfileName.text = it.username ?: "Not Available"
            binding.tvProfileEmail.text = it.email ?: "Not Available"
            binding.tvProfileId.text = it.id ?: "Not Available"
            binding.tvProfileIdtoken.text = it.idToken ?: "Not Available"
            binding.tvProfileServerAuthcode.text = it.serverAuthCode ?: "Not Available"
            binding.tvProfileGrantedScope.text = it.grantedScopes ?: "Not Available"
            binding.tvProfileReqScope.text = it.requestedScopes ?: "Not Available"

        }


    }


    fun updateUI() {

        when (sessionManager.accountType) {

            Constants.LOCAL -> {

            }

            Constants.ONLINE -> {


            }
        }
    }

}