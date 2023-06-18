package com.bpointer.productsmvvm.base.api.adapters


import android.text.TextUtils
import android.util.Log
import com.bpointer.productsmvvm.base.api.*
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonEncodingException
import com.squareup.moshi.Moshi
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

internal class ApiResponseCall<T>(proxy: Call<T>) : CallDelegate<T, ApiResponse<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<ApiResponse<T>>) =
        proxy.enqueue(object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val apiResponse = create(response)
                callback.onResponse(this@ApiResponseCall, Response.success(apiResponse))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val result = when (t) {
                    is IOException ->
                        ApiNetworkError(if (call.isCanceled) "Canceled" else t.message ?: "unknown error")
                    is SocketTimeoutException ->
                        ApiTimeoutError<T>("Time out")
                    else -> ApiErrorResponse<T>(errorMessage = t.message ?: "unknown error")
                }
                callback.onResponse(this@ApiResponseCall, Response.success(result))
            }
        })

    override fun cloneImpl() = ApiResponseCall(proxy.clone())

    private fun <T> create(response: Response<T>): ApiResponse<T> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null || response.code() == 204) {
                ApiEmptyResponse
            } else {
                ApiSuccessResponse(body)
            }
        } else {
            val msg = response.errorBody()?.string()
            val errorMsg = if (TextUtils.isEmpty(msg)) {
                response.message()
            } else {
                // We try to parse the errorBody as a JSON, returns the errorBody as String if
                // it fails
                try {
                    Moshi.Builder().build().adapter(ApiError::class.java).fromJson(msg)?.message
                } catch (e: JsonEncodingException) {
                    msg
                }
            } ?: "unknown error"
            ApiErrorResponse(errorMessage = errorMsg)
        }
    }

    override fun timeout(): Timeout? {
       Log.i("","")
        return null
    }
}

@JsonClass(generateAdapter = true)
internal data class ApiError(val type: String = "", val message: String?=null)
