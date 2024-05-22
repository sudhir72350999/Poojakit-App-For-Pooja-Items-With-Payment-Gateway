package com.androidapps.poojakit.adapter

import android.content.Context
import android.provider.Settings.Global
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidapps.poojakit.databinding.LayoutCartItemBinding
import com.androidapps.poojakit.roomdb.AppDatabase
import com.androidapps.poojakit.roomdb.ProductModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.content.Intent
import com.androidapps.poojakit.activities.ProductDetailsActivity


class CartAdapter(private val context: Context, private val list: List<ProductModel>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: LayoutCartItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = LayoutCartItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        Glide.with(context).load(list[position].productImage).into(holder.binding.imageView4)
        holder.binding.textView5.text = list[position].productName
        holder.binding.textView6.text = list[position].productSellingPrice



        /// when user click cart item the open detail
        holder.itemView.setOnClickListener {
            val intent =Intent(context,ProductDetailsActivity::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }

     
        // delete the cart item
/*
        val dao = AppDatabase.getInstance(context).productDao()
        holder.binding.deleteCartItem.setOnClickListener {

            GlobalScope.launch {
                Dispatchers.IO
                dao.deleteProduct(
                    ProductModel(list[position]).productId,
                    list[position].productName,
                    list[position].productImage,
                    list[position].productSellingPrice
                )

            }
        }

        */


        val dao = AppDatabase.getInstance(context).productDao()
        holder.binding.deleteCartItem.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                dao.deleteProduct(list[position])
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}


//package com.androidapps.poojakit.adapter
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.androidapps.poojakit.databinding.LayoutCartItemBinding
//import com.androidapps.poojakit.roomdb.ProductModel
//import com.bumptech.glide.Glide
//import com.androidapps.poojakit.R
//
//
//class CartAdapter(val context: Context, val list: List<ProductModel>) :
//    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
//    inner class CartViewHolder(binding: LayoutCartItemBinding) :
//        RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
//
//        val binding = LayoutCartItemBinding.inflate(LayoutInflater.from(context), parent, false)
//        return CartViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
//        Glide.with(context).load(list[position].productImage).into(holder.binding.imageView4)
//        holder.binding.textView5.text=list[position].productName
//        holder.binding.textView6.text=list[position].productSellingPrice
//    }
//
//    override fun getItemCount(): Int {
//        list.size
//    }
//}