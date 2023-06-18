package com.bpointer.productsmvvm.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bpointer.productsmvvm.base.api.dto.ProductsDTO


class SharedViewModel  : ViewModel() {



    private val favList = ArrayList<ProductsDTO.Product>()
    val favListLiveData = MutableLiveData<List<ProductsDTO.Product>>()

    val productDetailsLiveData = MutableLiveData<ProductsDTO.Product>()




    fun addFavProduct(product: ProductsDTO.Product) {
        favList.add(product)
        favListLiveData.value = favList
    }

    fun removeFavProduct(index: Int) {
        favList.removeAt(index)
        favListLiveData.value = favList
    }

    fun setSelectProduct(product: ProductsDTO.Product) {
        productDetailsLiveData.value = product
    }
}