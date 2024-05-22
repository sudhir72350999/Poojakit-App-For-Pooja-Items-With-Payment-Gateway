package com.androidapps.poojakit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import com.androidapps.poojakit.R
import com.androidapps.poojakit.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button4.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.button3.setOnClickListener {
            if (binding.userPhoneNubmer.text!!.isEmpty())
                Toast.makeText(this, "Please enter  number", Toast.LENGTH_SHORT).show()
            else
                sendOtp(binding.userPhoneNubmer.text.toString())
        }


    }

    private lateinit var builder: AlertDialog

    private fun sendOtp(number: String) {
        builder = AlertDialog.Builder(this)
            .setTitle("Loading.....")
            .setMessage("Please wait")
            .setCancelable(false)
            .create()

        builder.show()


        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber("+91$number")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

     val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

//            Log.d(TAG, "onVerificationCompleted:$credential")
//            signInWithPhoneAuthCredential(credential)
//            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
//            Log.w(TAG, "onVerificationFailed", e)
//            Toast.makeText(this, "Verification Failed"+e, Toast.LENGTH_SHORT).show()
//            Toast.makeText(this, "Something went wrong"+e.printStackTrace(), Toast.LENGTH_SHORT).show()


        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,

            ) {
            builder.dismiss()
            val intent = Intent(this@LoginActivity, OtpActivity::class.java)
            intent.putExtra("verificationId", verificationId)
            intent.putExtra("number", binding.userPhoneNubmer.text.toString())
            startActivity(intent)

        }
    }
}