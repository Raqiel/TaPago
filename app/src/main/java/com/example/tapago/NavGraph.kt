package com.example.tapago

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tapago.ui.screens.home.HomeScreen
import com.example.tapago.ui.screens.splash.SplashScreen
import com.example.tapago.ui.screens.workout.WorkoutScreen

object Route {
    const val HOME_ROUTE = "HOME_ROUTE"
    const val SPLASH_ROUTE = "SPLASH_ROUTE"
    const val WORKOUT_ROUTE = "WORKOUT_ROUTE"

}

object ResultKeys {

}

object ArgumentKeys {


}

sealed class Destination {
    class Home(val route: String = Route.HOME_ROUTE) : Destination()
    class Splash(val route: String = Route.SPLASH_ROUTE) : Destination()
    class Workout(val route: String = Route.WORKOUT_ROUTE) : Destination()



}


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
            WorkoutScreen()
        }

    }
}
    class MainActions(private val navController: NavHostController) {
        val navigate: (Destination) -> Unit = { destination ->
            when (destination) {
                is Destination.Home -> {

                    navController.navigate(destination.route)
                }

                is Destination.Splash ->  navController.navigate(destination.route)
                is Destination.Workout ->  navController.navigate(destination.route)

            }

        }
    }
