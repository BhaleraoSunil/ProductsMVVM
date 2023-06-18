package com.bpointer.productsmvvm.core.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [ViewModelModule::class, ApiServiceModule::class]
)
class AppModule(var application: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return application
    }
}
