package com.example.app_mascotascris

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_mascotascris.navigation.Screen
import com.example.app_mascotascris.ui.screens.*
import com.example.app_mascotascris.ui.theme.App_mascotascrisTheme
import com.example.app_mascotascris.ui.viewmodel.PetViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App_mascotascrisTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Creamos el ViewModel aquí para compartirlo si es necesario y asegurar su ciclo de vida
    val petViewModel: PetViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(onNavigateToLogin = {
                navController.navigate(Screen.Login.route)
            })
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { isAdmin ->
                    if (isAdmin) {
                        navController.navigate(Screen.HomeAdmin.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.HomeUser.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(onRegisterSuccess = {
                navController.navigate(Screen.Login.route)
            })
        }
        composable(Screen.HomeUser.route) { 
            UserHomeScreen(navController, petViewModel) 
        }
        composable(Screen.HomeAdmin.route) { 
            AdminHomeScreen(navController) 
        }
        composable(Screen.AddPet.route) { 
            AddPetScreen(navController, petViewModel) 
        }
        composable(Screen.Adoption.route) { 
            AdoptionScreen(navController, petViewModel) 
        }
        composable(
            route = Screen.PetDetail.route,
            arguments = listOf(navArgument("petId") { type = NavType.IntType })
        ) { backStackEntry ->
            val petId = backStackEntry.arguments?.getInt("petId") ?: 0
            PetDetailScreen(navController, petId, petViewModel)
        }
        composable(Screen.Rescues.route) { RescuesScreen(navController) }
        composable(Screen.Rescuers.route) { RescuersScreen(navController) }
        composable(Screen.Forms.route) { FormsScreen(navController) }
        composable(Screen.ShoppingCart.route) { CartScreen(navController) }
        composable(Screen.Subscriptions.route) { SubscriptionsScreen(navController) }
        composable(Screen.Events.route) { PlaceholderScreen(navController, "Gestión de Eventos") }
        composable(Screen.Reports.route) { PlaceholderScreen(navController, "Reportes de Gestión") }
    }
}
