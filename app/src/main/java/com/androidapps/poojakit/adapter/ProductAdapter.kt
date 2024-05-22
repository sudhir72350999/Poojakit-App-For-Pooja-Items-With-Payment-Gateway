package com.androidapps.poojakit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidapps.poojakit.activities.ProductDetailsActivity
import com.androidapps.poojakit.databinding.LayoutProductItemBinding
import com.androidapps.poojakit.model.AddProductModel
import com.bumptech.glide.Glide

import android.content.Intent


class ProductAdapter(val context: Context, val list: ArrayList<AddProductModel>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: LayoutProductItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter.ProductViewHolder {
        val binding = LayoutProductItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {

        val data = list[position]
        Glide.with(context).load(data.productCoverImg).into(holder.binding.imageView2)
        holder.binding.textView.text = data.productName
        holder.binding.textView1.text = data.productCategory
        holder.binding.textView2.text = data.productMrp
        holder.binding.button.text = data.productSellingPrice


        holder.itemView.setOnClickListener({
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("id", list[position].productId)
            context.startActivity(intent)
        })

        holder.binding.button2.setOnClickListener ({
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("id", list[position].productId)
            context.startActivity(intent)

        })

    }

    override fun getItemCount(): Int {
        return list.size
    }

}
