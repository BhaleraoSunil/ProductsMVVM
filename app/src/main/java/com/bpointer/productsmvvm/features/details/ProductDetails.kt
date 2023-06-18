package com.bpointer.productsmvvm.features.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bpointer.productsmvvm.MainActivity
import com.bpointer.productsmvvm.MyApplication
import com.bpointer.productsmvvm.R
import com.bpointer.productsmvvm.base.api.dto.ProductsDTO
import com.bpointer.productsmvvm.core.BaseFragment
import com.bpointer.productsmvvm.core.SharedViewModel
import com.bpointer.productsmvvm.databinding.FragmentProductDetailsBinding
import com.bpointer.productsmvvm.util.OnClickListener
import com.bpointer.productsmvvm.util.show
import com.bpointer.productsmvvm.util.showToast
import com.bumptech.glide.Glide

class ProductDetails : BaseFragment() {

    private  var binding: FragmentProductDetailsBinding?=null

    lateinit var sharedModel: SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_product_details, container, false)

        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MyApplication.mMyComponent.inject(this)

        initView()

    }

    private fun initView() {
        binding!!.appBar.tvTitle.text = "Details"
        binding!!.appBar.ivBack.show()
        binding!!.appBar.ivFavorite.show()
        sharedModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        sharedModel.productDetailsLiveData.observe(viewLifecycleOwner, Observer {

            setProductDetails(it)
        })
    }


    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTabVisibility(View.GONE)
    }
    override fun onStop() {
        super.onStop()
        (activity as MainActivity).setTabVisibility(View.VISIBLE)
    }


    private fun setProductDetails(product: ProductsDTO.Product) {
        Glide.with(binding!!.ivProduct)
            .load(product.imageURL)
            .into(binding!!.ivProduct)
        binding!!.tvName.text = product.title
        binding!!.tvRating.text = product.ratingCount.toString()
        binding!!.tvPrice.text = "$${product.price[0].value}"


        setFavoriteIcon(product.isInWishlist)

        binding!!.appBar.ivFavorite.setOnClickListener { view ->
            if (!product.isInWishlist){
                product.isInWishlist = true
                sharedModel.addFavProduct(product)
                binding!!.appBar.ivFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_24))

            }else {
                showToast("Already marked as favorite")
            }
        }
        binding!!.appBar.ivBack.setOnClickListener { view ->
            activity?.onBackPressed()
        }

    }

    private fun setFavoriteIcon(inWishlist: Boolean) {
        if (inWishlist)
            binding!!.appBar.ivFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_24))
        else
            binding!!.appBar.ivFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_border_24))

    }
}