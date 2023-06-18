package com.bpointer.productsmvvm.base.api.retrofit



import com.bpointer.productsmvvm.base.api.ApiResponse
import com.bpointer.productsmvvm.base.api.dto.ProductsDTO
import retrofit2.http.*

interface ApiServiceImpl {

   @GET("v3/2f06b453-8375-43cf-861a-06e95a951328")
    suspend fun productList(): ApiResponse<ProductsDTO>


    /*
       @Multipart
       @POST(BaseInterface.WS_SIGNUP)
       suspend fun signUp(
           @Part profile_photo: MultipartBody.Part,
           @PartMap hashMap: Map<String, String>
       ): ApiResponse<UserDto>



       @FormUrlEncoded
       @Headers("Accept: application/json", "Content-Type: application/x-www-form-urlencoded")
       @POST(BaseInterface.WS_EDIT_BASIC_INFO)
       suspend fun editBasicInfo(
           @Header("Authorization") token: String,
           @FieldMap hashMap: Map<String, String>
       ): ApiResponse<CommonPostApiDto>*/



}
