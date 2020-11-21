package com.san.ipassmanager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.san.ipassmanager.R
import com.san.ipassmanager.databinding.RowCredentialBinding
import com.san.ipassmanager.room.entity.AllCredentialEntity
import com.san.ipassmanager.utils.loadImage
import javax.inject.Inject

class CredentialsAdapter @Inject constructor() :
    ListAdapter<AllCredentialEntity, RecyclerView.ViewHolder>(AllCredentialDiffCallback()) {

    private lateinit var binding: RowCredentialBinding

    private var credentialAdapterInterface: CredentialAdapterInterface? = null

    fun setOnCredentialClickListener(credentialAdapterInterface: CredentialAdapterInterface) {
        this.credentialAdapterInterface = credentialAdapterInterface
    }

    fun interface CredentialAdapterInterface {
        fun onCredentialClick(credential: AllCredentialEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        /*return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_credential, parent, false)
        )*/

        binding = RowCredentialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        with(binding) {

            ivCcIcon.loadImage("https://${item.websiteLink}/favicon.ico")
            tvCcName.text = item.name
            tvCcDescription.text = item.description

            root.setOnClickListener {
                credentialAdapterInterface?.onCredentialClick(item)
            }
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class AllCredentialDiffCallback : DiffUtil.ItemCallback<AllCredentialEntity>() {
        override fun areItemsTheSame(
            oldItem: AllCredentialEntity,
            newItem: AllCredentialEntity
        ): Boolean {
            return oldItem.emailId == newItem.emailId
        }

        override fun areContentsTheSame(
            oldItem: AllCredentialEntity,
            newItem: AllCredentialEntity
        ): Boolean {
            return oldItem == newItem
        }
    }


    /* override fun submitList(list: List<AllCredentialEntity>?) {
         super.submitList(list)
         if (originalList.isNullOrEmpty()) {
             originalList = list
         }
     }

     override fun getFilter(): Filter {

         return object : Filter() {

             override fun performFiltering(charSequence: CharSequence?) =
                 FilterResults().apply {
                     values = originalList?.filter {

                         if (contains(charSequence.toString(), true) == true) {
                             return@filter true

                         }
                         return@filter false
                     }

                 }

             @Suppress("UNCHECKED_CAST")
             override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                 // refresh the list with filtered data
                 submitList(filterResults.values as List<AllCredentialEntity>?)
             }

         }
     }*/

}
