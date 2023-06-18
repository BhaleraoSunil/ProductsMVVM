package com.bpointer.productsmvvm.features

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bpointer.productsmvvm.R
import com.bpointer.productsmvvm.base.api.dto.ProductsDTO
import com.bpointer.productsmvvm.databinding.ProductItemBinding
import com.bpointer.productsmvvm.util.OnClickListener
import com.bpointer.productsmvvm.util.showToast
import com.bumptech.glide.Glide

class ProductsAdapter(
    val context: Context,
    private val list: List<ProductsDTO.Product>,
    var OnClickListener: OnClickListener.OnClickCallback
) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {



    /*class ProductView(itemView: ProductItemBinding) : RecyclerView.ViewHolder(itemView) {
        val tvBuy = itemView.tvBuy
        val tvName = itemView.txtName
        val tvPrice = itemView.txtPrice
        val ivIcon = itemView.ivIcon
    }*/

    inner class ProductViewHolder(val binding: ProductItemBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
    //    val v = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
    //    return ProductView(v)

        val binding = ProductItemBinding
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
            if (item.isInWishlist)
                binding.ivFavorite.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.baseline_favorite_24))
            else
                binding.ivFavorite.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.baseline_favorite_border_24))
            binding.ivFavorite.setOnClickListener { view ->
                if (!item.isInWishlist){
                    item.isInWishlist = true
                    notifyItemChanged(position)
                    OnClickListener(position, OnClickListener, "favorite").onClick(view)
                }else {
                    context.showToast("Already marked as favorite")
                }
            }
            binding.btnAddToCart.setOnClickListener { view ->

                if (item.isAddToCartEnable){
                    if (!item.isInTrolley) {
                        item.isInTrolley = true
                        notifyItemChanged(position)
                        OnClickListener(position, OnClickListener, "addCart").onClick(view)
                    }
                    else {
                        context.showToast("Already added in cart")
                    }
                }
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