package com.androidapps.poojakit.activities

import android.content.Intent
import android.content.Context
import android.content.SharedPreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.androidapps.poojakit.MainActivity
import com.androidapps.poojakit.R
import com.androidapps.poojakit.databinding.ActivityProductDetailsBinding
import com.androidapps.poojakit.roomdb.AppDatabase
import com.androidapps.poojakit.roomdb.ProductDao
import com.androidapps.poojakit.roomdb.ProductModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)

        getProductDetails(intent.getStringExtra("id"))
        setContentView(binding.root)

    }

    private fun getProductDetails(proId: String?) {
        Firebase.firestore.collection("products")
            .document(proId!!).get().addOnSuccessListener { documentSnapshot ->
                val list = documentSnapshot.get("productImages") as ArrayList<String>
                val productName = documentSnapshot.getString("productName")
                val productSellingPrice = documentSnapshot.getString("productSellingPrice")
                val productCoverImg = documentSnapshot.getString("productCoverImg")

                binding.productNames.text = productName
                binding.productSellingPrice.text = productSellingPrice
                binding.productDescription.text = documentSnapshot.getString("productDescription")

                // for slider
                val sliderList = ArrayList<SlideModel>()
                for (data in list) {
                    sliderList.add(SlideModel(data, ScaleTypes.CENTER_CROP))
                }

                // for add to cart
                cartAction(proId, productName, productSellingPrice, productCoverImg)
                binding.imageSlider.setImageList(sliderList)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

/*
        private fun getProductDetails(proId: String?) {
            Firebase.firestore.collection("products")
                .document(proId!!).get().addOnSuccessListener {
                    val list = it.get("productImages") as ArrayList<String>
                    binding.productNames.text = it.getString("productName")
                    binding.productSellingPrice.text = it.getString("productSellingPrice")
                    binding.productDescription.text = it.getString("productDescription")


                    // for slider
                    val sliderList = ArrayList<SlideModel>()
                    for (data in list) {
                        sliderList.add(SlideModel(data, ScaleTypes.CENTER_CROP))
                    }

                    // for add to cart // here is the main problem
                    cartAction(proId,name, productSellingPrice, it.getString("productCoverImg"))

                    binding.imageSlider.setImageList(sliderList)


                }
                .addOnFailureListener({

                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                })

        }
*/



    //        private fun cartAction(proId: String, name: String?, productSellingPrice: String?, coverImg: String?) {
    // Function implementation goes here
    private fun cartAction(
        proId: String,
        name: String?,
        productSellingPrice: String?,
        coverImg: String?
    ) {


        val productDao = AppDatabase.getInstance(this).productDao()
        if (productDao.isExit(proId) != null) {
//            binding.addToCart.text = "Go to Cart"
            binding.addToCart.text = "Buy Now"
//            binding.addToCart.text="Go to Cart and Buy Now"
        } else {
            binding.addToCart.text = "Add to Cart"
        }

        binding.addToCart.setOnClickListener {
            if (productDao.isExit(proId) != null) {
                openCart()
            } else {
                addToCart(productDao, proId, name, productSellingPrice, coverImg)
            }
        }
    }


    // for add to cart
    private fun addToCart(
        productDao: ProductDao,
        proId: String,
        name: String?,
        productSellingPrice: String?,
        coverImg: String?
    ) {
        val data = ProductModel(proId, name, coverImg, productSellingPrice)
        lifecycleScope.launch(Dispatchers.IO) {
            // now product will go in cart
            productDao.insertProduct(data)
//            binding.addToCart.text = "Go to Cart"
            binding.addToCart.text = "Buy Now"
        }
    }

    private fun openCart() {
        val preference = this.getSharedPreferences("info", MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart", true)
        editor.apply()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}