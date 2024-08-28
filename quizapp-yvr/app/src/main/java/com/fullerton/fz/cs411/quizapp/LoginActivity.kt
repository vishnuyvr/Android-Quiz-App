package com.fullerton.fz.cs411.quizapp

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.fullerton.fz.cs411.quizapp.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityLoginBinding
    private lateinit var googleAuth: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 20
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
//        setContentView(R.layout.activity_login)
//        supportActionBar?.hide()
        binding.button.setOnClickListener {
            Firebase.auth.createUserWithEmailAndPassword(
                binding.textInputLayoutemail.editText?.text.toString(),
                binding.textInputLayoutpassword.editText?.text.toString()
            ).addOnCompleteListener {

                if (it.isSuccessful) {
                    Toast.makeText(this, "User Created!!!", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(this, "User Not Created!!!", Toast.LENGTH_LONG).show()
                }
            }
        }

        googleAuth = findViewById(R.id.button6)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)

        googleAuth.setOnClickListener {
            googleSignIn()
        }

    }

    private fun googleSignIn() {

        val intent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount>? = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount? = try {
                    task?.getResult(ApiException::class.java)
                } catch (e: ApiException) {
                    // Handle the ApiException if needed
                    null
                }
                firebaseAuth(account?.idToken)

            }
            catch (e: Exception){
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()

            }
        }

    }

    private fun firebaseAuth(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val map = hashMapOf<String, Any>(
                        "id" to (user?.uid ?: ""),
                        "name" to (user?.displayName ?: ""),
                        "profile" to (user?.photoUrl?.toString() ?: "")
                    )
                    database.reference.child("users").child(user?.uid ?: "").setValue(map)
                    val intent = Intent(this, SelectQuizActivity::class.java) // need to change to selection screen

                    startActivity(intent)
                } else
                    Toast.makeText(this@LoginActivity, "Something went wrong", Toast.LENGTH_SHORT).show()

            }
            }

        }

