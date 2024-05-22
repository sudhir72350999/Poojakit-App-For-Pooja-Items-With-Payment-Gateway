package com.androidapps.poojakit.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.androidapps.poojakit.R
import com.androidapps.poojakit.adapter.CategoryAdapter
import com.androidapps.poojakit.adapter.ProductAdapter
import com.androidapps.poojakit.model.AddProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SeeAllProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all_product)
        getProducts()
    }
    private fun getProducts(){

        val list = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products")
            .get()
            .addOnSuccessListener {
                list.clear()  // clear the list of categories
                for (doc in it.documents){
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }

                val recyclerView = findViewById<RecyclerView>(R.id.productRecyclerView)
                recyclerView.adapter = ProductAdapter(this, list)
            }
    }
}