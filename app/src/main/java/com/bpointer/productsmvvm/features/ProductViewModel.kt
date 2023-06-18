package com.bpointer.productsmvvm.features

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bpointer.productsmvvm.base.api.dto.ProductsDTO
import com.bpointer.productsmvvm.repository.AuthenticationRepository
import com.bpointer.productsmvvm.repository.resorce.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import javax.inject.Inject

class ProductViewModel @Inject constructor(
        private val authenticationRepository: AuthenticationRepository
) :
        ViewModel() {
    var mutableProductList = MutableLiveData<Resource<ProductsDTO>>()

    fun productList() {
        viewModelScope.launch (Dispatchers.IO){
            val resource = authenticationRepository.getProductList()
            mutableProductList.postValue(resource)
        }
    }


}
