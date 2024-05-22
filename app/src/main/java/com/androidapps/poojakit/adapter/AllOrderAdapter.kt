package com.androidapps.poojakit.adapter

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.androidapps.poojakit.databinding.AllOrderItemLayoutBinding
import com.androidapps.poojakit.model.AllOrderModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.checkerframework.checker.index.qual.GTENegativeOne
import android.app.AlertDialog
import android.content.DialogInterface
import android.widget.Toast


class AllOrderAdapter(val list: ArrayList<AllOrderModel>, val context: Context) :
    RecyclerView.Adapter<AllOrderAdapter.AllOrderViewHolder>() {

    inner class AllOrderViewHolder(val binding: AllOrderItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
        val binding = AllOrderItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AllOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {
        holder.binding.productTitle.text = list[position].name
        holder.binding.productPrice.text = list[position].price

//        holder.binding.cancelButton.setOnClickListener {
//            updateStatus("Canceled", list[position].orderId!!)
//        }

// Inside your activity or fragment class
        holder.binding.cancelButton.setOnClickListener {
            // Create an AlertDialog.Builder instance
            val builder = AlertDialog.Builder(context)

            // Set the dialog title and message
            builder.setTitle("Confirmation")
                .setMessage("Are you sure you want to cancel?")

            // Add "Yes" button with a listener
            builder.setPositiveButton("Yes") { dialog, _ ->
                // Perform action when "Yes" is clicked
                updateStatus("Canceled", list[position].orderId!!)
                dialog.dismiss() // Dismiss the dialog
                Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show()
            }

            // Add "No" button with a listener
            builder.setNegativeButton("No") { dialog, _ ->
                // Perform action when "No" is clicked (Do nothing)
                dialog.dismiss() // Dismiss the dialog
            }

            // Create and show the dialog
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }











//            updateStatus("Canceled", list[position].orderId!!)

        when (list[position].status) {
            "Ordered" -> {
                holder.binding.productStatus.text ="Ordered"
            }

            "Dispatched" -> {
                holder.binding.productStatus.text ="Dispatched"

            }

            "Delivered" -> {
                holder.binding.productStatus.text ="Delivered"
            }
            "Canceled" -> {
                holder.binding.productStatus.text ="Canceled"
                holder.binding.cancelButton.isEnabled = false

            }
        }
    }


    private fun updateStatus(str: String, doc: String) {
        val data = hashMapOf<String, Any>()
        data["status"] = str
        Firebase.firestore.collection("allOrders").document(doc).update(data)
            .addOnSuccessListener {
                Toast.makeText(context, "Order Status updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Order Status failed to update: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}



/*
class AllOrderAdapter(val list: ArrayList<AllOrderModel>, val context: Context) :
    RecyclerView.Adapter<AllOrderAdapter.AllOrderViewHolder> {
    inner class AllOrderViewHolder(val binding: AllOrderItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
        return AllOrderViewHolder(
            AllOrderItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {

        holder.binding.productTitle.text = list[position].name
        holder.binding.productPrice.text = list[position].price
        // everytime used

        when (list[position].status) {
            "Ordered" -> {
                holder.binding.productStatus.text ="Ordered"
            }

            "Dispatched" -> {
                holder.binding.productStatus.text ="Dispatched"

            }

            "Delivered" -> {
                holder.binding.productStatus.text ="Delivered"
            }
            "Canceled" -> {
                holder.binding.productStatus.text ="Canceled"

            }
        }
    }


    override fun getItemCount(): Int {

        return list.size
    }
}

 */