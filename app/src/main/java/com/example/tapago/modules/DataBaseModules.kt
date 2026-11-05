package com.example.tapago.modules


import androidx.room.Room
import com.example.tapago.data.AppDatabase
import com.example.tapago.data.local.repositories.LastWorkoutRepository
import com.example.tapago.data.local.repositories.LastWorkoutRepositoryImpl
import com.example.tapago.data.local.repositories.LocalPhotoRepository
import com.example.tapago.data.local.repositories.WorkoutRepository
import com.example.tapago.data.local.repositories.WorkoutRepositoryImpl
import com.example.tapago.data.remote.repositories.PhotoRepository
import com.example.tapago.ui.screens.home.HomeViewModel
import com.example.tapago.ui.screens.shared.ui.CreateSharedViewModel
import com.example.tapago.usecase.CreateWorkoutUseCase
import com.example.tapago.usecase.GetWorkoutsUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    single {
        val database = get<AppDatabase>()
        database.workoutDao()
    }
}
val repositoryModule = module {
    single<WorkoutRepository> { WorkoutRepositoryImpl(get()) }
    single<PhotoRepository> { LocalPhotoRepository(androidContext()) }
    single<LastWorkoutRepository> { LastWorkoutRepositoryImpl(androidContext()) }
}
val useCaseModule = module {
    factory { GetWorkoutsUseCase(workoutRepository = get()) }
    factory { CreateWorkoutUseCase(workoutRepository = get()) }
}
val viewModelModule = module {
    viewModel {
        HomeViewModel(getWorkoutsUseCase = get(), lastWorkoutRepository = get())
    }
    viewModel {
        CreateSharedViewModel(createWorkoutUseCase = get(), photoRepository = get())
    }
}