package com.bpointer.productsmvvm.repository


import com.bpointer.productsmvvm.base.api.*
import com.bpointer.productsmvvm.base.api.dto.ProductsDTO
import com.bpointer.productsmvvm.base.api.retrofit.ApiServiceImpl
import com.bpointer.productsmvvm.repository.resorce.Resource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthenticationRepository @Inject constructor(private val remoteDataSourceProvider: ApiServiceImpl) {


    suspend fun getProductList(): Resource<ProductsDTO> {
        return when (val apiResponse =
            remoteDataSourceProvider.productList()) {
            is ApiSuccessResponse -> {
                Resource.success(apiResponse.body)
            }
            is ApiTimeoutError -> Resource.error(null, apiResponse.errorMessage)
            is ApiNetworkError -> Resource.error(null, apiResponse.errorMessage)
            is ApiEmptyResponse -> Resource.error(null, "")
            is ApiErrorResponse -> {
                Resource.error(null, apiResponse.errorMessage)
            }
        }
    }


}
