package com.example.app_mascotascris.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register")
    object ChangePassword : Screen("change_password")
    object HomeUser : Screen("home_user")
    object HomeAdmin : Screen("home_admin")
    object AddPet : Screen("add_pet")
    object ManageUsers : Screen("manage_users")
    object Adoption : Screen("adoption")
    object Rescues : Screen("rescues")
    object Rescuers : Screen("rescuers")
    object Forms : Screen("forms")
    object ShoppingCart : Screen("shopping_cart")
    object Subscriptions : Screen("subscriptions")
    object Reports : Screen("reports")
    object Events : Screen("events")
    object Profile : Screen("profile")
    object PetDetail : Screen("pet_detail/{petId}") {
        fun createRoute(petId: Int) = "pet_detail/$petId"
    }
}
