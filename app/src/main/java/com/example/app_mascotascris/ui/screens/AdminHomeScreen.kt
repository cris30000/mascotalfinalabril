package com.example.app_mascotascris.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_mascotascris.navigation.Screen
import com.example.app_mascotascris.ui.theme.PrimaryPurple

// Modelo de datos para el menú
data class AdminMenuItem(val title: String, val icon: ImageVector, val route: String, val bgColor: Color)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel de Administrador", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryPurple),
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Login.route) }) {
                        Icon(Icons.Default.Logout, contentDescription = "Salir", tint = Color.White)
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate(Screen.AddPet.route) },
                containerColor = PrimaryPurple,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Nueva Mascota") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Gestión del Refugio",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = PrimaryPurple
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            val adminItems = listOf(
                AdminMenuItem("Añadir Mascota", Icons.Default.Pets, Screen.AddPet.route, Color(0xFFE1BEE7)),
                AdminMenuItem("Crear Eventos", Icons.Default.Event, Screen.Events.route, Color(0xFFF3E5F5)),
                AdminMenuItem("Reportes Adopción", Icons.Default.Assessment, Screen.Reports.route, Color(0xFFF3E5F5)),
                AdminMenuItem("Gestionar Usuarios", Icons.Default.ManageAccounts, Screen.HomeAdmin.route, Color(0xFFE1BEE7)),
                AdminMenuItem("Procesos Adopción", Icons.Default.Verified, Screen.Adoption.route, Color(0xFFE1BEE7)),
                AdminMenuItem("Gestionar Donaciones", Icons.Default.VolunteerActivism, Screen.Subscriptions.route, Color(0xFFF3E5F5))
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(adminItems) { item ->
                    AdminMenuCard(item) {
                        navController.navigate(item.route)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminMenuCard(item: AdminMenuItem, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(130.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = item.bgColor
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = PrimaryPurple,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
        }
    }
}
