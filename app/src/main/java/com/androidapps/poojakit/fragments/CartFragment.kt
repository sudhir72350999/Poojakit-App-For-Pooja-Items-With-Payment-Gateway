package com.androidapps.poojakit.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.androidapps.poojakit.MainActivity
import com.androidapps.poojakit.R
import com.androidapps.poojakit.activities.AddressActivity
import com.androidapps.poojakit.adapter.CartAdapter
import com.androidapps.poojakit.databinding.FragmentCartBinding
import com.androidapps.poojakit.roomdb.AppDatabase
import com.androidapps.poojakit.roomdb.ProductModel

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var list: ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)

        val preference =
            requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart", false)
        editor.apply()
        // Inflate the layout for this fragment

        // for getting the item of cart
        val dao = AppDatabase.getInstance(requireContext()).productDao()
        // initialize the list
        list = ArrayList()


        dao.getAllProducts().observe(requireActivity()) {
            binding.cartRecycler.adapter = CartAdapter(requireContext(), it)

            // working for checkoutactivity to payment
            list.clear()
            for (data in it) {
                list.add(data.productId)

            }
            totalCost(it)
        }


        return binding.root
    }

    private fun totalCost(data: List<ProductModel>?) {
        var total = 0
        for (item in data!!) {
            total += item.productSellingPrice!!.toInt()

        }

//        binding.totalItemCount.text = "Total item in cart is ${data.size}"
//        binding.totalItemCost.text = "Total Cost: $total"
//        binding.checkout.text="Checkout"


//        binding.totalItemCount.visibility = View.VISIBLE
//// Set the text for totalItemCount
//        binding.totalItemCount.text = "Total item in cart is ${data.size}"
//
//// Make the totalItemCost TextView visible
//        binding.totalItemCost.visibility = View.VISIBLE
//// Set the text for totalItemCost
//        binding.totalItemCost.text = "Total Cost: $total"
//
//// Make the checkout Button visible
//        binding.checkout.visibility = View.VISIBLE
//// Set the text for checkout Button
//        binding.checkout.text = "Checkout"




        // Assuming "data" is your cart list
        if (data.isEmpty()) {
            // If the cart list is empty, hide the views and set text to indicate an empty cart
            binding.totalItemCount.visibility = View.GONE
            binding.totalItemCost.visibility = View.GONE
            binding.checkout.visibility = View.GONE
            binding.cartEmptyTextView.visibility = View.VISIBLE // Show the text indicating empty cart
            binding.cartEmptyTextView.text = "Your cart is empty"
        } else {
            // If the cart has items, show the views and set appropriate texts
            binding.totalItemCount.visibility = View.VISIBLE
            binding.totalItemCount.text = "Total item in cart is ${data.size}"

            // Calculate total cost here
//            val total = calculateTotalCost(data) // You need to implement this function

            binding.totalItemCost.visibility = View.VISIBLE
            binding.totalItemCost.text = "Total Cost: $total"

            binding.checkout.visibility = View.VISIBLE
            binding.cartEmptyTextView.visibility = View.GONE // Hide the text indicating empty cart
        }





        binding.checkout.setOnClickListener {

            val intent = Intent(context, AddressActivity::class.java)

            // push the data into the next activity
            val b = Bundle()
            b.putStringArrayList("productIds", list)
            b.putString("totalCost", total.toString())
            intent.putExtras(b)

            startActivity(intent)
        }


    }


}