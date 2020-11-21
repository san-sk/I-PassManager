package com.san.ipassmanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.chip.Chip
import com.san.ipassmanager.R
import com.san.ipassmanager.ui.adapter.CredentialsAdapter
import com.san.ipassmanager.databinding.FragmentHomeBinding
import com.san.ipassmanager.retrofit.Resource
import com.san.ipassmanager.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.row_credential.view.*
import javax.inject.Inject
import kotlin.random.Random


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var sessionManager: SessionManager

    private val homeViewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var credentialsAdapter: CredentialsAdapter

    private var gsa: GoogleSignInAccount? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        gsa = GoogleSignIn.getLastSignedInAccount(context)

        if (sessionManager.password != null) {

            when (sessionManager.accountType) {

                Constants.ONLINE -> {
                    if (sessionManager.driveFileId == null) {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                    }
                }

                Constants.LOCAL -> {
                    sessionManager.userName = "User"
                }

                else -> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                }
            }
        } else {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
        }

        /*  val binding: FragmentHomeBinding =
              DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

          binding.viewmodel = homeViewModel

          return binding.root*/

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.let {
            it.setSupportActionBar(binding.fhToolbar.materialToolbar)
            setupActionBarWithNavController(it, findNavController())
        }

        binding.tvHfUserName.text = "Welcome\n  ${gsa?.displayName ?: sessionManager.userName}"
      //  test()

        val categoryList = resources.getStringArray(R.array.credential_categories).toList()
        categoryList.forEach {
            addChipToGroup(it)
        }


        binding.rvFhCredentials.adapter = credentialsAdapter
        loadCredentials()

        binding.bottomAppBar.setNavigationOnClickListener {
            // Handle navigation icon press

            /*driveInit()
            if (credentialsAdapter.currentList.isNotEmpty()) {

                uploadBackup(backup(credentialsAdapter.currentList)!!)
            }*/

            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToOptionsFragment())

        }

        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    // Handle search icon press
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
                    true
                }
                /* R.id.more -> {
                     // Handle more item (inside overflow menu) press
                     true
                 }*/
                else -> false
            }
        }

        binding.fabFh.setOnClickListener {

            val extras = FragmentNavigatorExtras(binding.fabFh to "shared_element_container")

            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAddCredentialFragment(
                    null,
                    Constants.CREATE
                ), extras
            )

        }

        credentialsAdapter.setOnCredentialClickListener {

            val extras =
                FragmentNavigatorExtras(binding.rvFhCredentials.rootView.iv_cc_icon to "credential_sec")
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToViewCredentialFragment(
                    it
                ), extras
            )
        }


    }


    private fun loadCredentials() {
        homeViewModel.allCredentials.observe(viewLifecycleOwner, Observer { credentials ->

            credentials?.let {
                if (it.isNotEmpty()) {
                    binding.tvHfError.makeInVisible()
                    credentialsAdapter.submitList(it)
                } else {
                    binding.tvHfError.makeVisible()
                    binding.tvHfError.text = "No Records Found"
                }
            }


        })
    }

    private fun loadTypedCredentials(type: String) {

        credentialsAdapter.submitList(null)

        homeViewModel.getTypeOfCredentials(type)
            .observe(viewLifecycleOwner, Observer { credentials ->

                credentials?.let {

                    if (it.isNotEmpty()) {
                        binding.tvHfError.makeInVisible()
                        credentialsAdapter.submitList(it)
                    } else {
                        binding.tvHfError.makeVisible()
                        binding.tvHfError.text = "No Records Found"
                    }

                }


            })
    }


    private fun addChipToGroup(category: String) {


        val existingChip =
            binding.cgHf.findViewWithTag<Chip>(category)
        if (existingChip == null) {


            val chip = Chip(context)

            chip.id = Random.nextInt()
            chip.tag = category
            chip.text = category
            chip.isClickable = true
            //chip.isCheckable = true
            chip.isCloseIconVisible = false

            context?.let {
                chip.setTextColor(
                    ContextCompat.getColor(
                        it, R.color.colorWhite
                    )
                )
            }


            chip.setChipBackgroundColorResource(R.color.secondaryLightColor)

            chip.setCloseIconTintResource(R.color.colorError)

            binding.cgHf.addView(chip)

            chip.setOnClickListener {
                chip.text?.let {

                    if (it == "All") {
                        loadCredentials()
                    } else {
                        loadTypedCredentials(it.toString())
                    }
                }

            }


        }

    }


    fun test() {


        homeViewModel.fetchTestResponse2().observe(viewLifecycleOwner, Observer { res ->

            when (res.status) {

                Resource.Status.LOADING -> {

                    Toast.makeText(context, "call loading", Toast.LENGTH_SHORT).show()
                }

                Resource.Status.SUCCESS -> {

                    res.data?.let {
                        Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
                    }

                }

                Resource.Status.ERROR -> {
                    res.errorBody?.msg.let {
                        Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
                    }

                }
            }
        })
    }


}