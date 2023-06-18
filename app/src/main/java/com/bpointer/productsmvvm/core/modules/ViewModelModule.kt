package com.bpointer.productsmvvm.core.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bpointer.productsmvvm.core.SharedViewModel
import com.bpointer.productsmvvm.core.di.keys.ViewModelKey
import com.bpointer.productsmvvm.features.ProductViewModel

import com.bpointer.productsmvvm.core.modules.viewmodel.ViewModelFactory


import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    internal abstract fun productViewModel(viewModel: ProductViewModel): ViewModel



}
