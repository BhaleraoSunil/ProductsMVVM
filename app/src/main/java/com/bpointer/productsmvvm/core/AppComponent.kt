package com.bpointer.productsmvvm.core

import com.bpointer.productsmvvm.MainActivity
import com.bpointer.productsmvvm.core.modules.AppModule
import com.bpointer.productsmvvm.features.Products
import com.bpointer.productsmvvm.features.details.ProductDetails
import com.bpointer.productsmvvm.features.favorites.FavProducts

import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(products: Products)
    fun inject(favProducts: FavProducts)
    fun inject(products: ProductDetails)
}
