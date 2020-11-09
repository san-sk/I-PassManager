package com.san.ipassmanager.ui.credentials

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.san.ipassmanager.adapter.CredentialsAdapter
import com.san.ipassmanager.databinding.FragmentSearchBinding
import com.san.ipassmanager.utils.makeInVisible
import com.san.ipassmanager.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.row_credential.view.*
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val viewModel by viewModels<SearchViewModel>()

    @Inject
    lateinit var credentialsAdapter: CredentialsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /* val binding: FragmentSearchBinding =
             DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

         binding.viewmodel = viewModel

         return binding.root*/
        // return inflater.inflate(R.layout.fragment_search, container, false)

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.let {
            it.setSupportActionBar(binding.fsToolbar.materialToolbar)
            NavigationUI.setupActionBarWithNavController(it, findNavController())
        }

        binding.rvFsCredentials.adapter = credentialsAdapter
        searchCredential("")

        binding.svFs.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.clpbFs.show()
                searchCredential(p0.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.clpbFs.show()
                searchCredential(newText.orEmpty())
                return true
            }

        })


        credentialsAdapter.setOnCredentialClickListener {

            val extras =
                FragmentNavigatorExtras(binding.rvFsCredentials.rootView.iv_cc_icon to "credential_sec")
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToRDCredentialFragment(
                    it
                ), extras
            )
        }

    }


    private fun searchCredential(queryString: String) {

        credentialsAdapter.submitList(null)
        viewModel.searchCredential("%${queryString}%")
            .observe(viewLifecycleOwner, Observer { credentials ->

                binding.clpbFs.hide()
                credentials?.let {
                    if (it.isNotEmpty()) {
                        binding.tvSfError.makeInVisible()
                        credentialsAdapter.submitList(it)
                    } else {
                        binding.tvSfError.makeVisible()
                        binding.tvSfError.text = "No Records Found"
                    }
                }

            })

    }
}