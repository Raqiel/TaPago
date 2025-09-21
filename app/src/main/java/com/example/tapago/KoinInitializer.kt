package com.example.tapago

import com.example.tapago.modules.databaseModule
import com.example.tapago.modules.repositoryModule
import com.example.tapago.modules.useCaseModule
import com.example.tapago.modules.viewModelModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration


fun initKoin(
    appDeclaration: KoinAppDeclaration = {}
) = startKoin {
    appDeclaration()
    androidLogger()
    modules(
        listOf(
            viewModelModule,
            databaseModule,
            repositoryModule,
            useCaseModule
        )
    )
}
