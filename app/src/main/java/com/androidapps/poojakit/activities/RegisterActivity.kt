package com.androidapps.poojakit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.androidapps.poojakit.R
import com.androidapps.poojakit.databinding.ActivityRegisterBinding
import com.androidapps.poojakit.model.UserModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding :ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button4.setOnClickListener {
          openLogin()
        }

        binding.button3.setOnClickListener {
            validateData()
        }
    }
    private fun validateData(){
        if(binding.userName.text!!.isEmpty()||binding.userPhoneNubmer.text!!.isEmpty())
            Toast.makeText(this,"Please fill all the fields", Toast.LENGTH_SHORT).show()
        else
            storedata()

    }

    private fun storedata() {
        val builder = AlertDialog.Builder(this)
            .setTitle("Loading.....")
            .setMessage("Please wait")
            .setCancelable(false)
            .create()
        builder.show()


        // for save the the nubmer and name of user sharedpreference
        val preference=this.getSharedPreferences("user", MODE_PRIVATE)
        val editor = preference.edit()

        editor.putString("number", binding.userPhoneNubmer.text.toString())
        editor.putString("name", binding.userName.text.toString())
        editor.apply()

//        val data = hashMapOf<String,Any>()
//        data["name"]=binding.userName.text.toString()
//        data["number"]=binding.userPhoneNubmer.text.toString()

        // for address
        // this two fields will be added already and all the thing will be customized
        val data = UserModel(userName=binding.userName.text.toString(), userPhoneNumber=binding.userPhoneNubmer.text.toString())

        Firebase.firestore.collection("users").document(binding.userPhoneNubmer.text.toString())
            .set(data).addOnSuccessListener {
                Toast.makeText(this,"User Registered", Toast.LENGTH_SHORT).show()
                builder.dismiss() // dismiss dialog
                openLogin()
            }
            .addOnFailureListener{
                builder.dismiss() // dismiss dialog
                Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()

            }
    }

    private fun openLogin() {
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}