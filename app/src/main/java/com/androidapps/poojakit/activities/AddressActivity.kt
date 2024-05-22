package com.androidapps.poojakit.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.androidapps.poojakit.R
import com.androidapps.poojakit.databinding.ActivityAddressBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressBinding
    private lateinit var  preference:SharedPreferences
    private lateinit var totalCost : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)

        setContentView(binding.root)
        preference= this.getSharedPreferences("user", MODE_PRIVATE)
        totalCost=intent.getStringExtra("totalCost")!!


        loadUserInfo()

        binding.proceed.setOnClickListener {
            validateData(
                binding.userNumber.text.toString(),
                binding.userName.text.toString(),
                binding.userPincode.text.toString(),
                binding.userCity.text.toString(),
                binding.userState.text.toString(),
                binding.userVillage.text.toString()

            )
        }
    }

    private fun validateData(
        number: String,
        name: String,
        pincode: String,
        city: String,
        state: String,
        village: String
    ) {
        if (number.isEmpty() || name.isEmpty() || city.isEmpty() || state.isEmpty() || village.isEmpty() || pincode.isEmpty()) {
            Toast.makeText(this, "Please fill al the field", Toast.LENGTH_SHORT).show()
        } else {
//            storeData(name,number,pincode,city,state,village) // we can remove the number and name when we update then change this parameter
            storeData(pincode, city, state, village)
        }

    }

    // we can remove the number and name when we update then change this parameter
//    private fun storeData(name:String,number:String,pincode:String,city: String,state: String,village: String){
    private fun storeData(pincode: String, city: String, state: String, village: String) {
//        val map = mapOf<String, Any>()
        val map = mutableMapOf<String, Any>()
        map["village"] = village
        map["state"] = state
        map["city"] = city
        map["pincode"] = pincode

        Firebase.firestore.collection("users")
            .document(preference.getString("number", "")!!)
            .update(map).addOnSuccessListener {

                // for payment
                val b =Bundle()
                b.putStringArrayList("productIds",intent.getStringArrayListExtra("productIds"))
                b.putString("totalCost",totalCost)

                val intent =Intent(this,CheckOutActivity::class.java)
                intent.putExtras(b)
                // when address update then open checkout activity
                startActivity(intent)


            }
            .addOnFailureListener {
                Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }

    private fun loadUserInfo()
    {
        Firebase.firestore.collection("users").
        document(preference.getString("number", "")!!)
            .get().addOnSuccessListener {
                binding.userName.setText(it.getString("userName"))
                binding.userNumber.setText(it.getString("userPhoneNumber"))
                binding.userVillage.setText(it.getString("village"))
                binding.userCity.setText(it.getString("city"))
                binding.userPincode.setText(it.getString("pincode"))
                binding.userState.setText(it.getString("state"))
            }
            .addOnFailureListener{
                Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()

            }
    }

}