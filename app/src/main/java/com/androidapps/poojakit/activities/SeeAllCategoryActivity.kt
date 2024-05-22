package com.androidapps.poojakit.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.androidapps.poojakit.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.androidapps.poojakit.adapter.CategoryAdapter
import com.androidapps.poojakit.model.CategoryModel

class SeeAllCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all_category)


        getCategory()
//        binding.root

    }
    private fun getCategory(){
        val list = ArrayList<CategoryModel>()
        Firebase.firestore.collection("categories")
            .get()
            .addOnSuccessListener {
                list.clear()  // clear the list of categories
                for (doc in it.documents){
                    val data = doc.toObject(CategoryModel::class.java)
                    list.add(data!!)
                }
                val recyclerView = findViewById<RecyclerView>(R.id.categoryRecyclerView)
                recyclerView.adapter = CategoryAdapter(this, list)

            }
    }


}


/*

package com.androidapps.poojakit.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.androidapps.poojakit.R
import com.androidapps.poojakit.adapter.SeeAllCategoryAdapter
import com.androidapps.poojakit.model.AddProductModel
import com.androidapps.poojakit.model.CategoryModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.content.Intent

class SeeAllCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all_category)

        getProductsss(intent.getStringExtra("cate"))


    }

    private fun getProductsss(category: String?){

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
                recyclerView.adapter= SeeAllCategoryAdapter(this,list)
            }
    }
}


*/







//package com.androidapps.poojakit.activities
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import androidx.recyclerview.widget.RecyclerView
//import com.androidapps.poojakit.R
//import com.androidapps.poojakit.adapter.CategoryProductAdapter
//import com.androidapps.poojakit.model.AddProductModel
//
//
//
//import com.androidapps.poojakit.adapter.ProductAdapter
//
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase
//import android.content.Intent
//import com.androidapps.poojakit.adapter.SeeAllCategoryAdapter
//
//class SeeAllCategoryActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_see_all_category)
//        getProductss(intent.getStringExtra("cate"))
//
//    }
//    private fun getProductss(category: String?){
//
//        val list = ArrayList<AddProductModel>()
//        Firebase.firestore.collection("products").whereEqualTo("productCategory",category)
//            .get()
//            .addOnSuccessListener {
//                list.clear()  // clear the list of categories
//                for (doc in it.documents){
//                    val data = doc.toObject(AddProductModel::class.java)
//                    list.add(data!!)
//                }
//                val recyclerView = findViewById<RecyclerView>(R.id.categoryRecyclerView)
//                recyclerView.adapter= SeeAllCategoryAdapter(this,list)
//            }
//    }
//
//}