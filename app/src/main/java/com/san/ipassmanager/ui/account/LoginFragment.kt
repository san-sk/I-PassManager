package com.san.ipassmanager.ui.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.san.ipassmanager.R
import com.san.ipassmanager.databinding.FragmentLoginBinding
import com.san.ipassmanager.model.UserModel
import com.san.ipassmanager.services.DriveHelper
import com.san.ipassmanager.utils.*
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val RC_SIGN_IN = 1

    private var mGoogleSignInClient: GoogleSignInClient? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var gsa: GoogleSignInAccount
    private var typedPaswd: String? = null
    private var userName: String? = null

    @Inject
    lateinit var sessionManager: SessionManager

    private val loginViewModel by viewModels<LoginViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /* val binding: FragmentLoginBinding =
             DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

         binding.viewmodel = loginViewModel

         return binding.root*/
        //return inflater.inflate(R.layout.fragment_login, container, false)

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initialize
        database = Firebase.database.reference

        binding.pwdLayout.makeGone()
        binding.loginLayout.makeVisible()


        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .requestScopes(Scope(DriveScopes.DRIVE_FILE))
                .requestScopes(Scope(DriveScopes.DRIVE_APPDATA))
                .build()

        activity?.let {
            mGoogleSignInClient = GoogleSignIn.getClient(it, gso)
        }


        binding.signInButton.setOnClickListener {
            sessionManager.accountType = Constants.ONLINE
            signIn()
        }


        binding.btnSkip.setOnClickListener {
            sessionManager.accountType = Constants.LOCAL
            binding.loginLayout.makeGone()
            binding.pwdLayout.makeVisible()
        }




        binding.mbtnFlSubmit.setOnClickListener {


            if (binding.etPassword.text.toString().length == 14 &&
                binding.etUsername.text.toString().isNotEmpty()
            ) {

                typedPaswd = binding.etPassword.text.toString()
                userName = binding.etUsername.text.toString()


                if (sessionManager.accountType == Constants.ONLINE) {

                    checkUserExists(typedPaswd.orEmpty(), gsa)

                } else {
                    BRUtils.createLocalBackupFile(requireContext())
                    sessionManager.userName = userName
                    sessionManager.password = typedPaswd
                    findNavController().navigateUp()
                }

            } else {
                binding.etPassword.error = "Password must be 14 characters"
            }
        }
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)

                /*val intent = Intent(this@SignInActivity, UserProfile::class.java)
                startActivity(intent)*/

                if (account != null) {

                    gsa = account
                    firebaseAuthWithGoogle(account)

                }

            } catch (e: ApiException) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.e("TAG", "signInResult:failed code=" + e.statusCode)
            }
        }
    }

    private fun signIn() {
        val intent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    private fun signOut() {

        activity?.let {

            mGoogleSignInClient?.signOut()?.addOnCompleteListener(it,
                OnCompleteListener<Void> {
                    Toasty.info(requireContext(), "Signed Out", Toast.LENGTH_LONG).show()
                })
        }


    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        firebaseAuth = FirebaseAuth.getInstance()

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {

                binding.loginLayout.makeGone()
                binding.pwdLayout.makeVisible()

                binding.etUsername.setText(acct.displayName)

            } else {
                Toasty.error(
                    requireContext(),
                    "Google sign in with firebase failed:(",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }


    private fun registerUser(acct: GoogleSignInAccount) {

        createDriveBackupFile()

        if (sessionManager.password != null && sessionManager.driveFileId != null) {

            val usersRef: DatabaseReference = database.ref.child("users")

            val user = UserModel(
                acct.email,
                acct.displayName,
                sessionManager.password,
                sessionManager.driveFileId

            )


            usersRef.child(acct.id.toString()).setValue(user)
                .addOnSuccessListener {
                    Toasty.success(
                        requireContext(),
                        "Google sign in with firebase success:",
                        Toast.LENGTH_LONG
                    )
                        .show()

                    findNavController().navigateUp()
                }
                .addOnFailureListener {
                    Toasty.error(
                        requireContext(),
                        "Google sign in with firebase failed:",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

        }
    }


    private fun checkUserExists(pwd: String, acct: GoogleSignInAccount) {

        val usersRef: DatabaseReference = database.ref.child("users").child(acct.id.toString())

        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    val user = snapshot.getValue(UserModel::class.java)

                    if (pwd == user?.password) {
                        sessionManager.userName = user.username
                        sessionManager.password = user.password
                        sessionManager.driveFileId = user.driveFileId


                        findNavController().navigateUp()


                    } else {

                        Toasty.error(
                            requireContext(),
                            "Google sign in with firebase failed incorrect password",
                            Toast.LENGTH_LONG
                        ).show()
                    }


                } else {
                    sessionManager.userName = userName
                    sessionManager.password = typedPaswd
                    registerUser(acct)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toasty.error(
                    requireContext(),
                    " firebase database error",
                    Toast.LENGTH_LONG
                ).show()
            }

        })

    }


    private fun createDriveBackupFile() {

        val credential = GoogleAccountCredential.usingOAuth2(
            context, Collections.singleton(DriveScopes.DRIVE_FILE)
        )
        credential.selectedAccount = gsa.account


        val googleDriveService = Drive.Builder(
            AndroidHttp.newCompatibleTransport(),
            GsonFactory(),
            credential
        ).setApplicationName(context?.resources?.getString(R.string.app_name)).build()


        DriveHelper(googleDriveService).createNewFile(
            Constants.APP_NAME + ".json",
            "application/json",
        )
            .addOnSuccessListener {
                sessionManager.driveFileId = it
                Log.i("DriveFileID", it)
                Toasty.success(
                    requireContext(),
                    "Drive file created successfully",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener {
                Toasty.error(requireContext(), "Failed to create drive file", Toast.LENGTH_LONG)
                    .show()
                Log.e("DriveFileID", it.toString())
            }

    }

}




