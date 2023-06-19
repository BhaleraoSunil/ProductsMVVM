package com.bpointer.productsmvvm.features

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bpointer.productsmvvm.MainActivity
import com.bpointer.productsmvvm.MyApplication
import com.bpointer.productsmvvm.R
import com.bpointer.productsmvvm.base.api.dto.ProductsDTO
import com.bpointer.productsmvvm.core.BaseFragment
import com.bpointer.productsmvvm.core.SharedViewModel
import com.bpointer.productsmvvm.databinding.FragmentProductsBinding
import com.bpointer.productsmvvm.features.details.ProductDetails
import com.bpointer.productsmvvm.repository.resorce.Status
import com.bpointer.productsmvvm.util.BitmapScaler
import com.bpointer.productsmvvm.util.GridSpacingItemDecoration
import com.bpointer.productsmvvm.util.OnClickListener
import com.bpointer.productsmvvm.util.showToast
import javax.inject.Inject


class Products @Inject constructor() : BaseFragment() {

    private  var binding: FragmentProductsBinding?=null
    private lateinit var productsAdapter: ProductsAdapter
    var productList: ArrayList<ProductsDTO.Product> = ArrayList<ProductsDTO.Product>()


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val productViewModel: ProductViewModel by viewModels { viewModelFactory }
    lateinit var sharedModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_products, container, false)
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding?.root
    }
    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTabVisibility(View.VISIBLE)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MyApplication.mMyComponent.inject(this)



        initView()

    }


    private fun initView (){
        binding!!.appBar.tvTitle.text = "Products"
        initProductListView()
        sharedModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        productViewModel.mutableProductList.observe(viewLifecycleOwner) {
            hideProgressDialog()
            if (it.status == Status.SUCCESS) {
                Log.e(javaClass.name, "============================SUCCESS====" + it.data)
                it.data?.products?.let { it1 -> productList.addAll(it1.distinct())
                    productsAdapter.notifyDataSetChanged()}


            } else if (it.status == Status.ERROR) {

                Log.e(javaClass.name, "============================ERROR" + it.message)
            }
        }
        callProductsAPI()
    }
    private fun initProductListView(){

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
         productsAdapter = ProductsAdapter(requireActivity(), productList, onClickCallback)
        binding?.productsListView?.run {
            layoutManager = gridLayoutManager
            adapter =  productsAdapter

            addItemDecoration(GridSpacingItemDecoration(2, BitmapScaler.dpToPixel(8, requireActivity()), true) )
        }


    }


    private var onClickCallback = object : OnClickListener.OnClickCallback {
        override fun onClicked(view: View, position: Int, type: String) {

            when (type){
                "favorite"->{
                    sharedModel.addFavProduct(productList[position])

                }
                "addCart"->{
                    showToast("Product addred to cart")
                }
                else ->{

                    sharedModel.setSelectProduct(productList[position])
                    val fragment = ProductDetails()
                    val bundle = Bundle()

                    fragment.arguments = bundle
                    replaceFragmentWithBack(
                        R.id.container,
                        fragment,
                        "ProductDetails",
                        "Products"
                    )
                }
            }
        }

    }

    private fun callProductsAPI() {

        showProgressDialog()

        productViewModel.productList()
    }
}