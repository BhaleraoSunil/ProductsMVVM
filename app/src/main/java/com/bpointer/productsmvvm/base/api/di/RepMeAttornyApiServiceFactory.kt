package com.bpointer.productsmvvm.base.api.di


import com.bpointer.productsmvvm.base.api.adapters.ApiResponseAdapterFactory
import com.bpointer.productsmvvm.base.api.retrofit.ApiServiceImpl

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit


object RepMeAttornyApiServiceFactory {

    fun createApiService(
        moshi: Moshi = Moshi.Builder()
                .add(NULL_TO_EMPTY_STRING_ADAPTER)
                .build()


    ): ApiServiceImpl {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addCallAdapterFactory(ApiResponseAdapterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(client)


            .build()
            .create<ApiServiceImpl>()
    }


    object NULL_TO_EMPTY_STRING_ADAPTER {
        @FromJson
        fun fromJson(reader: JsonReader): String {
            if (reader.peek() != JsonReader.Token.NULL) {
                return reader.nextString()
            }
            reader.nextNull<Unit>()
            return ""
        }
    }
}


