package com.androidapps.poojakit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.androidapps.poojakit.R
import com.androidapps.poojakit.adapter.AllOrderAdapter
import com.androidapps.poojakit.databinding.FragmentMoreBinding
import com.androidapps.poojakit.model.AllOrderModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MoreFragment : Fragment() {


    private lateinit var binding: FragmentMoreBinding
    private lateinit var list: ArrayList<AllOrderModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        list = ArrayList()
        val preference =
            requireContext().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

        Firebase.firestore.collection("allOrders")
            .whereEqualTo("userId", preference.getString("number", "")!!)
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it) {
                    val data = doc.toObject(AllOrderModel::class.java)
                    list.add(data)
                }
                binding.recyclerView.adapter = AllOrderAdapter(list, requireContext())

            }

        return binding.root
    }


}