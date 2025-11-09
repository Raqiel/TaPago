package com.example.tapago

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.tapago.ArgumentKeys.WORKOUT_KEY
import com.example.tapago.extensions.getArgObject
import com.example.tapago.extensions.getArgStringValue
import com.example.tapago.extensions.setArgObject
import com.example.tapago.models.Workout
import com.example.tapago.models.mockExercises
import com.example.tapago.ui.screens.shared.ui.create_exercises.CreateExerciseScreen
import com.example.tapago.ui.screens.shared.ui.create_workout.CreateWorkoutScreen
import com.example.tapago.ui.screens.home.HomeScreen
import com.example.tapago.ui.screens.shared.ui.CreateSharedViewModel
import com.example.tapago.ui.screens.splash.SplashScreen
import com.example.tapago.ui.screens.workout.WorkoutScreen
import org.koin.androidx.compose.koinViewModel

object Route {
    const val HOME_ROUTE = "HOME_ROUTE"
    const val SPLASH_ROUTE = "SPLASH_ROUTE"
    const val WORKOUT_ROUTE = "WORKOUT_ROUTE"
    const val CREATE_WORKOUT_ROUTE = "CREATE_WORKOUT_ROUTE"
    const val CREATE_EXERCISE_ROUTE = "CREATE_EXERCISE_ROUTE"

}

object ResultKeys {

}

object ArgumentKeys {
    const val WORKOUT_KEY = "WORKOUT_KEY"


}

sealed class Destination {
    class Home(val route: String = Route.HOME_ROUTE) : Destination()
    class Splash(val route: String = Route.SPLASH_ROUTE) : Destination()
    class Workout(val route: String = Route.WORKOUT_ROUTE, val workout: com.example.tapago.models.Workout?) : Destination()
    class CreateWorkout(val route: String = Route.CREATE_WORKOUT_ROUTE) : Destination()
    class CreateExercise(val route: String = Route.CREATE_EXERCISE_ROUTE) : Destination()
}


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destination.Splash().route
) {
    val actions = remember(navController) { MainActions(navController) }


    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            Route.HOME_ROUTE
        ) {
            HomeScreen(
                navigate = actions.navigate
            )
        }
        composable(
            Route.SPLASH_ROUTE
        ) {
            SplashScreen(navigate = actions.navigate)
        }
        composable(
            Route.WORKOUT_ROUTE
        ) {
            val workout = navController.getArgObject(
                key = WORKOUT_KEY,
                classOf = Workout::class.java
            )

            WorkoutScreen(
                workout = workout
            )
        }
        navigation(
            startDestination = Route.CREATE_WORKOUT_ROUTE,
            route = CreateFlowRoute.ROOT
        ) {

            composable(Route.CREATE_WORKOUT_ROUTE) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(CreateFlowRoute.ROOT)
                }
                val sharedViewModel: CreateSharedViewModel =
                    koinViewModel(viewModelStoreOwner = parentEntry)

                CreateWorkoutScreen(navigate = actions.navigate, viewModel = sharedViewModel )
            }

            composable(Route.CREATE_EXERCISE_ROUTE) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(CreateFlowRoute.ROOT)
                }
                val sharedViewModel: CreateSharedViewModel =
                    viewModel(viewModelStoreOwner = parentEntry)
                CreateExerciseScreen(navigate = actions.navigate, sharedViewModel)
            }
        }
    }

}


object CreateFlowRoute {
    const val ROOT = "create_flow_root"
}

class MainActions(private val navController: NavHostController) {
    val navigate: (Destination) -> Unit = { destination ->
        when (destination) {
            is Destination.Home -> {

                navController.navigate(destination.route)
            }

            is Destination.Splash -> navController.navigate(destination.route)
            is Destination.Workout -> {
                navController.setArgObject(
                    key = WORKOUT_KEY,
                    T = destination.workout
                )
                navController.navigate(destination.route)
            }
            is Destination.CreateWorkout -> navController.navigate(destination.route)
            is Destination.CreateExercise -> navController.navigate(destination.route)

        }

    }
}
