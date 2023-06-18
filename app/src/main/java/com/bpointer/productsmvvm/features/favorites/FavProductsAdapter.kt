package com.bpointer.productsmvvm.features.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bpointer.productsmvvm.R
import com.bpointer.productsmvvm.base.api.dto.ProductsDTO
import com.bpointer.productsmvvm.databinding.FavProductItemBinding
import com.bpointer.productsmvvm.databinding.ProductItemBinding
import com.bpointer.productsmvvm.util.OnClickListener
import com.bpointer.productsmvvm.util.showToast
import com.bumptech.glide.Glide

class FavProductsAdapter(
    val context: Context,
    private val list: List<ProductsDTO.Product>,
    var OnClickListener: OnClickListener.OnClickCallback
) : RecyclerView.Adapter<FavProductsAdapter.ProductViewHolder>() {





    inner class ProductViewHolder(val binding: FavProductItemBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
    //    val v = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
    //    return ProductView(v)

        val binding = FavProductItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        with(holder){
            val item = list[position]
            Glide.with(binding.ivProduct)
                .load(item.imageURL)
               // .placeholder(R.drawable.default_image)
                .into(binding.ivProduct)

            binding.tvName.text = item.title
            binding.tvInfo.text = item.brand
            binding.tvPrice.text = "$${item.price[0].value}"

            binding.ivFavorite.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.baseline_favorite_24))

            binding.ivFavorite.setOnClickListener { view ->
                //item.isInWishlist = false
                //notifyItemChanged(position)
                OnClickListener(position, OnClickListener, "noFavorite").onClick(view)
            }

            itemView.setOnClickListener { view ->
                OnClickListener(position, OnClickListener, "").onClick(view)
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}