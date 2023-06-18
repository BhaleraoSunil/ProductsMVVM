package com.bpointer.productsmvvm.core.modules



import com.bpointer.productsmvvm.base.api.di.RepMeAttornyApiServiceFactory
import com.bpointer.productsmvvm.base.api.retrofit.ApiServiceImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiServiceModule {

    @Provides
    fun provideSykedApiService(): ApiServiceImpl {
       return RepMeAttornyApiServiceFactory.createApiService()
    }
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()
}
