package com.androidapps.poojakit.activities

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.androidapps.poojakit.R
import com.androidapps.poojakit.adapter.CategoryProductAdapter
import com.androidapps.poojakit.adapter.ProductAdapter
import com.androidapps.poojakit.model.AddProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.content.Intent

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        getProductss(intent.getStringExtra("cate"))
    }

    private fun getProductss(category: String?){

        val list = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products").whereEqualTo("productCategory",category)
            .get()
            .addOnSuccessListener {
                list.clear()  // clear the list of categories
                for (doc in it.documents){
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.adapter= CategoryProductAdapter(this,list)
            }
    }
}