package com.androidapps.poojakit.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.androidapps.poojakit.databinding.LayoutCategoryItemBinding
import com.androidapps.poojakit.R
import com.androidapps.poojakit.activities.CategoryActivity
import com.androidapps.poojakit.model.CategoryModel
import com.bumptech.glide.Glide

class CategoryAdapter(var context:Context,var list : ArrayList<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    inner  class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val bindng = LayoutCategoryItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
     return  CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_category_item,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindng.textView2.text=list[position].cate
        Glide.with(context).load(list[position].img).into(holder.bindng.imageView)

        holder.itemView.setOnClickListener{
            val intent = Intent(context,CategoryActivity::class.java)
            intent.putExtra("cate",list[position].cate)
            context.startActivity(intent)
        }




    }
}