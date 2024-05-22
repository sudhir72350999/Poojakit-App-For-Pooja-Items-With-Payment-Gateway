package com.androidapps.poojakit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.androidapps.poojakit.MainActivity
import com.androidapps.poojakit.R
import com.androidapps.poojakit.databinding.ActivityOtpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.verifyOtp.setOnClickListener {
            if (binding.userOtp.text!!.isEmpty())
                Toast.makeText(this, "Please Provide the Otp sent to number", Toast.LENGTH_SHORT)
                    .show()
            else {
                verifyuser(binding.userOtp.text!!.toString())
            }

        }
    }

    private fun verifyuser(otp: String) {
        val credential = PhoneAuthProvider.getCredential(
            intent.getStringExtra("verificationId")!!, otp
        )
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // for save the the nubmer and name of user sharedpreference
                    val preference=this.getSharedPreferences("user", MODE_PRIVATE)
                    val editor = preference.edit()

                    editor.putString("number", intent.getStringExtra("number"))
//                    editor.putString("name", binding.userName.text.toString())
                    editor.apply()



                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "signInWithCredential:success")

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    val user = task.result?.user
                } else {

                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    // Sign in failed, display a message and update the UI
//                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                        // The verification code entered was invalid
//                    }


                    // Update UI
                }
            }
    }
}