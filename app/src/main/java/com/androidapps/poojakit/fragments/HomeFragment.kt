package com.androidapps.poojakit.fragments

import android.content.Intent
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidapps.poojakit.R
import com.androidapps.poojakit.databinding.FragmentHomeBinding
import com.androidapps.poojakit.model.CategoryModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.androidapps.poojakit.adapter.CategoryAdapter
import com.androidapps.poojakit.adapter.ProductAdapter
import com.androidapps.poojakit.model.AddProductModel
import com.bumptech.glide.Glide
import android.provider.MediaStore.Images.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.androidapps.poojakit.activities.CategoryActivity
import com.androidapps.poojakit.activities.ProductDetailsActivity
import com.androidapps.poojakit.activities.RegisterActivity
import com.androidapps.poojakit.activities.SeeAllCategoryActivity
import com.androidapps.poojakit.activities.SeeAllProductActivity


class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.seeAllCategory.setOnClickListener({
            val intent = Intent(context,SeeAllCategoryActivity::class.java)
//            Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        })




        binding.seeAllProduct.setOnClickListener({
            val intent = Intent(context,SeeAllProductActivity::class.java)
//            Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        })

//        button2 is missing 2:43:01 correct later
        // when user click on button like add to cart or buy now
        /*
        binding.button2.setOnClickListener {
            val intent = Intent(requireContext(),ProductDetailsActivity::class.java)
            startActivity(intent)
        }

*/

        val preference = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        if(preference.getBoolean("isCart",false))
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)


        // Set title in the ActionBar
        getSliderImage() // for slider image
        getProducts()
        getCategory()


        return binding.root


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
                binding.categoryRecyclerView.adapter= CategoryAdapter(requireContext(),list)
            }
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
                binding.productRecyclerView.adapter= ProductAdapter(requireContext(),list)
            }
    }

    private fun getSliderImage(){
        Firebase.firestore.collection("slider").document("item")
            .get().addOnSuccessListener {
                Glide.with(requireContext()).load(it.get("img")).into(binding.sliderImage)
            }
    }



}