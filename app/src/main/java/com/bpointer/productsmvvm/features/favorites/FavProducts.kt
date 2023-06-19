package com.bpointer.productsmvvm.features.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bpointer.productsmvvm.MainActivity
import com.bpointer.productsmvvm.MyApplication
import com.bpointer.productsmvvm.R
import com.bpointer.productsmvvm.base.api.dto.ProductsDTO
import com.bpointer.productsmvvm.core.BaseFragment
import com.bpointer.productsmvvm.core.SharedViewModel
import com.bpointer.productsmvvm.databinding.FragmentFavProductsBinding
import com.bpointer.productsmvvm.features.details.ProductDetails
import com.bpointer.productsmvvm.util.BitmapScaler
import com.bpointer.productsmvvm.util.GridSpacingItemDecoration
import com.bpointer.productsmvvm.util.OnClickListener
import com.bpointer.productsmvvm.util.showToast
import javax.inject.Inject


class FavProducts @Inject constructor(): BaseFragment() {

    private  var binding: FragmentFavProductsBinding?=null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var sharedModel: SharedViewModel

    private lateinit var productsAdapter: FavProductsAdapter
    var productList: ArrayList<ProductsDTO.Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onStart() {
        super.onStart()
        // StatusBarUtil.setLightMode(activity)

        (activity as MainActivity).setTabVisibility(View.VISIBLE)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_fav_products, container, false)

        binding = FragmentFavProductsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MyApplication.mMyComponent.inject(this)

        //initHeader()
        initView()

    }



    private fun initHeader() {
        var bPad = 0
        try {
            bPad = getNavigationBarPadding(activity as MainActivity)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        binding!!.llMain.setPadding(0, getStatusBarHeight(), 0, bPad)

    }
    private fun initView() {
        binding!!.appBar.tvTitle.text = "My Favorites"
        initProductListView()
        sharedModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        sharedModel.favListLiveData.observe(viewLifecycleOwner, Observer {
            productList.clear()
            productList.addAll(it)
            productsAdapter.notifyDataSetChanged()
        })
    }

    private fun initProductListView(){

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        productsAdapter = FavProductsAdapter(requireActivity(), productList, onClickCallback)
        binding?.favListView?.run {
            layoutManager = gridLayoutManager
            adapter =  productsAdapter

            addItemDecoration(GridSpacingItemDecoration(2, BitmapScaler.dpToPixel(8, requireActivity()), true) )
        }


    }

    private var onClickCallback = object : OnClickListener.OnClickCallback {
        override fun onClicked(view: View, position: Int, type: String) {

            when (type){
                "noFavorite"->{

                    sharedModel.removeFavProduct(position)
                }

                else ->{

                    sharedModel.setSelectProduct(productList[position])
                    val fragment = ProductDetails()
                    val bundle = Bundle()
                    //bundle.putString("profile_id", appSession.user?.user_id)

                    fragment.arguments = bundle
                    replaceFragmentWithBack(
                        R.id.container,
                        fragment,
                        "ProductDetails",
                        "FavProducts"
                    )
                }
            }
        }

    }

}