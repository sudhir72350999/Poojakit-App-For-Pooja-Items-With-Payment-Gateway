package com.androidapps.poojakit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.androidapps.poojakit.MainActivity
import com.androidapps.poojakit.R
import com.androidapps.poojakit.roomdb.AppDatabase
import com.androidapps.poojakit.roomdb.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class CheckOutActivity : AppCompatActivity(), PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)
        val co = Checkout()
        // apart from setting it in AndroidManifest.xml, keyId can also be set
        // programmatically during runtime
//        co.setKeyID("rzp_live_XXXXXXXXXXXXXX") // change api key
        co.setKeyID("rzp_test_Nf4kqSze6QkGcH") // this is my key

        val price = intent.getStringExtra("totalCost")


        try {
            val options = JSONObject()
            options.put("name", "Poojakit")
            options.put("description", "Pookakit Application")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
//            options.put("order_id", "order_DBJOWzybf0sJbb"); // we can add or remove
//            options.put("amount", "50000")//pass amount in currency subunits
            options.put("amount", (price!!.toInt() * 100))//pass amount in currency subunits
//            options.put("amount", (price!!.toString().toInt() * 100).toString())


//            val retryObj = new JSONObject();
//            retryObj.put("enabled", true);  // we can remove this message
//            retryObj.put("max_count", 4);
//            options.put("retry", retryObj);

//            val prefill = JSONObject()
            options.put(
                "prefill.email",
                "sudhirpraj06@gmail.com"
            ) // we can change the number and email
            options.put("prefill.contact", "7235099961")

//            options.put("prefill",prefill)
            co.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }

    }


    /*
        private fun fetchdata(productId: String?) {

            // for delete the cart item after buying
            val dao = AppDatabase.getInstance(this).productDao()

            Firebase.firestore.collection("products")
                .document(productId!!).get().addOnSuccessListener {
                    lifecycleScope.launch(Dispatchers.IO) {
                        dao.deleteProduct(ProductModel(productId))

                    }
                }
            saveData(it.getString("productName"), it.getString("productSellingPrice"), productId)
        }

        */


    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Success ", Toast.LENGTH_LONG).show()
        // now send to the home page activity
        uploadData()

    }


    private fun uploadData() {
        val id = intent.getStringArrayListExtra("productIds")
        for (currentId in id!!) {
            fetchData(currentId)

        }

    }


    private fun fetchData(productId: String?) {
        // for delete the cart item after buying
        val dao = AppDatabase.getInstance(this).productDao()

        Firebase.firestore.collection("products")
            .document(productId!!).get().addOnSuccessListener {
//                    documentSnapshot ->

                lifecycleScope.launch(Dispatchers.IO) {
                    dao.deleteProduct(ProductModel(productId))
                }

                saveData(
                    it.getString("productName"),
                    it.getString("productSellingPrice"), productId
                )


                // Retrieve the product name and selling price from the documentSnapshot
//                val productName = documentSnapshot.getString("productName")
//                val productSellingPrice = documentSnapshot.getString("productSellingPrice")

                // Call saveData with the retrieved values

            }
    }

    private fun saveData(name: String?, price: String?, productId: String) {
        val preference = this.getSharedPreferences("user", MODE_PRIVATE)
        val data = hashMapOf<String, Any>()
        data["name"] = name!!
        data["price"] = price!!
        data["productId"] = productId
        data["status"] = "Ordered"
        data["userId"] = preference.getString("number", "")!!

        val firestore = Firebase.firestore.collection("allOrders")
        val key = firestore.document().id
        data["orderId"] = key


        firestore.document(key).set(data).addOnSuccessListener {

            Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
            .addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }


//        val firestore = Firestore.getInstance

    }


    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Failed ", Toast.LENGTH_LONG).show()


    }


}



