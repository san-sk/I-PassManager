package com.san.ipassmanager.ui.backuprestore

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.san.ipassmanager.R
import com.san.ipassmanager.databinding.FragmentBackupRestoreBinding
import com.san.ipassmanager.services.DriveHelper
import com.san.ipassmanager.ui.CommonViewModel
import com.san.ipassmanager.utils.*
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import java.io.File
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class BackupRestoreFragment : Fragment() {

    private lateinit var binding: FragmentBackupRestoreBinding

    @Inject
    lateinit var sessionManager: SessionManager

    private val viewModel by viewModels<BackupRestoreViewModel>()

    private val commonViewModel by viewModels<CommonViewModel>()

    private var gsa: GoogleSignInAccount? = null
    private lateinit var credential: GoogleAccountCredential
    private lateinit var googleDriveService: Drive

    private val args: BackupRestoreFragmentArgs by navArgs()


    private lateinit var localBackup: File

    /* @Inject
     private lateinit var brUtils: BRUtils*/


    /*  private val fragmentInterface by lazy {
          context?.let { it as FragmentInterface }
      }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /* val binding: FragmentBackupRestoreBinding =
             DataBindingUtil.inflate(inflater, R.layout.fragment_backup_restore, container, false)
 
         binding.viewmodel = viewModel
 
         return binding.root*/

        binding = FragmentBackupRestoreBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backupFilePath =
            File("${requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)}/Backup/")

        localBackup = File("$backupFilePath/${Constants.BACKUP_FILE_NAME}")


        gsa = GoogleSignIn.getLastSignedInAccount(context)

        if (gsa?.account != null) {
            binding.btnFbrDrive.makeVisible()
        } else {
            binding.btnFbrDrive.makeInVisible()
        }


        when (args.action) {

            Constants.RESTORE -> {

                binding.tvFbrTitle.text = requireContext().resources.getString(R.string.restore)
                binding.tvFbrHelperText.text = ""
                // requireContext().resources.getString(R.string.backup_found_msg)

                binding.ivFbr.loadImage(R.drawable.ic_restore, isFullSize = true)

                binding.tvFbrFromTo.text = requireContext().resources.getString(R.string.from)


            }

            Constants.BACKUP -> {

                binding.tvFbrTitle.text = requireContext().resources.getString(R.string.backup)
                binding.tvFbrHelperText.text = ""
                //  requireContext().resources.getString(R.string.no_backup_found_msg)

                binding.ivFbr.loadImage(R.drawable.ic_backup, isFullSize = true)

                binding.tvFbrFromTo.text = requireContext().resources.getString(R.string.to)

            }
        }


        binding.btnFbrDrive.setOnClickListener {

            when (args.action) {

                Constants.RESTORE -> {

                    downloadBackupFromDrive()
                }

                Constants.BACKUP -> {

                    backupCredentials(isLocal = false)

                }
            }


        }

        binding.btnFbrLocal.setOnClickListener {

            when (args.action) {

                Constants.RESTORE -> {

                    if (localBackup.exists()) {

                        restoreCredentials(localBackup.readText())
                    }
                }

                Constants.BACKUP -> {

                    backupCredentials(isLocal = true)
                }
            }


        }

        binding.btnFbrSkip.setOnClickListener {

            findNavController().navigateUp()
        }

    }


    private fun backupCredentials(isLocal: Boolean) {

        binding.clpbFbr.makeVisible()

        viewModel.allCredentials.observe(viewLifecycleOwner, Observer { credentials ->

            credentials.let {
                if (it.isNotEmpty()) {

                    val encryptedData = BRUtils.backup(sessionManager.password.orEmpty(), it)

                    encryptedData?.let { bs ->

                        if (isLocal) {

                            if (localBackup.exists()) {
                                localBackup.apply { writeText(bs) }
                            }

                            binding.clpbFbr.makeGone()
                            Toasty.success(requireContext(), "Backup success", Toast.LENGTH_LONG)
                                .show()
                            findNavController().navigate(BackupRestoreFragmentDirections.actionBackupRestoreFragmentToHomeFragment())

                        } else {

                            uploadBackupToDrive(bs)
                        }

                    }
                    Log.w(Constants.TAG, encryptedData.orEmpty())

                } else {
                    binding.clpbFbr.makeGone()
                    Toasty.error(requireContext(), "No data found", Toast.LENGTH_LONG).show()
                    Log.e(tag, "no data")

                }
            }

        })

    }


    private fun restoreCredentials(encryptedData: String?) {

        binding.clpbFbr.makeVisible()
        if (!encryptedData.isNullOrEmpty()) {

            val backupEntity = BRUtils.restore(sessionManager.password.orEmpty(), encryptedData)

            if (backupEntity != null) {
                Log.w("restore", backupEntity.toString())
                backupEntity.let {
                    commonViewModel.insertList(it)
                    binding.clpbFbr.makeGone()
                    findNavController().navigate(BackupRestoreFragmentDirections.actionBackupRestoreFragmentToHomeFragment())
                }
                Toasty.success(requireContext(), "Restore Success", Toast.LENGTH_LONG).show()
            } else {
                binding.clpbFbr.makeGone()
                Toasty.error(
                    requireContext(),
                    "Wrong password for this backup",
                    Toast.LENGTH_LONG
                ).show()
            }


        } else {
            binding.clpbFbr.makeGone()
            Toasty.warning(requireContext(), "Backup is empty", Toast.LENGTH_LONG).show()

        }


    }


    private fun driveInit() {


        credential = GoogleAccountCredential.usingOAuth2(
            context, Collections.singleton(DriveScopes.DRIVE_FILE)
        )

        credential.selectedAccount = gsa?.account

        googleDriveService = Drive.Builder(
            AndroidHttp.newCompatibleTransport(),
            GsonFactory(),
            credential
        ).setApplicationName(Constants.APP_NAME).build()


    }


    private fun uploadBackupToDrive(jsonString: String) {

        binding.clpbFbr.makeVisible()
        driveInit()

        Log.i("Drive", "backup Init")

        sessionManager.driveFileId?.let {
            DriveHelper(googleDriveService).saveFile(
                it,
                Constants.APP_NAME + ".json",
                jsonString
            ).addOnSuccessListener {
                Toasty.success(requireContext(), "Backup success", Toast.LENGTH_LONG).show()
                Log.i("Drive", "Backup success")

                findNavController().navigate(BackupRestoreFragmentDirections.actionBackupRestoreFragmentToHomeFragment())
                binding.clpbFbr.makeGone()

            }
                .addOnFailureListener {
                    Toasty.error(requireContext(), "Backup Failed", Toast.LENGTH_LONG).show()
                    Log.e("Drive", it.toString())
                    binding.clpbFbr.makeGone()
                }
        }
    }

    private fun downloadBackupFromDrive() {

        binding.clpbFbr.makeVisible()
        driveInit()
        Log.i("Drive", "download backup init")
        //  fragmentInterface?.setProgressBarVisible(true)
        sessionManager.driveFileId?.let { id ->

            DriveHelper(googleDriveService).readFile(id)
                ?.addOnSuccessListener {
                    //   fragmentInterface?.setProgressBarVisible(false)
                    it.second.let { backup ->

                        binding.clpbFbr.makeGone()

                        restoreCredentials(backup)

                    }

                    // Toasty.success(requireContext(), "Restore Success", Toast.LENGTH_LONG).show()
                    Log.i("Drive", it.second)
                    Log.i("Drive", "restore success")
                }
                ?.addOnFailureListener {
                    //   fragmentInterface?.setProgressBarVisible(false)
                    Toasty.error(requireContext(), "Restore Failed", Toast.LENGTH_LONG).show()
                    Log.e("Drive", it.toString())
                    binding.clpbFbr.makeGone()
                }

        }

    }


}