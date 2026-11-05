package com.example.tapago.modules

import com.example.tapago.data.remote.auth.AuthRepository
import com.example.tapago.data.remote.auth.AuthRepositoryImpl
import com.example.tapago.data.remote.repositories.PhotoRepository
import com.example.tapago.data.remote.repositories.PhotoRepositoryImpl
import com.example.tapago.data.remote.repositories.RemoteWorkoutRepository
import com.example.tapago.data.remote.repositories.RemoteWorkoutRepositoryImpl
import com.example.tapago.ui.screens.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.PersistentCacheSettings
import com.google.firebase.storage.FirebaseStorage
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }

    single {
        FirebaseFirestore.getInstance().apply {
            firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setLocalCacheSettings(PersistentCacheSettings.newBuilder().build())
                .build()
        }
    }

    single { FirebaseStorage.getInstance() }

    single<AuthRepository> {
        AuthRepositoryImpl(auth = get(), appContext = androidContext())
    }

    single<RemoteWorkoutRepository> {
        RemoteWorkoutRepositoryImpl(firestore = get(), authRepository = get())
    }

    single<PhotoRepository> {
        PhotoRepositoryImpl(
            storage = get(),
            authRepository = get(),
            appContext = androidContext()
        )
    }

    viewModel { LoginViewModel(authRepository = get()) }
}
