package com.example.app_mascotascris.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.app_mascotascris.R
import com.example.app_mascotascris.data.local.entities.PetEntity
import com.example.app_mascotascris.navigation.Screen
import com.example.app_mascotascris.ui.theme.PrimaryPurple
import com.example.app_mascotascris.ui.viewmodel.PetViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHomeScreen(navController: NavController, viewModel: PetViewModel = viewModel()) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val pets by viewModel.pets.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = Color.White) {
                DrawerHeader(onProfileClick = {
                    scope.launch {
                        drawerState.close()
                        navController.navigate(Screen.Profile.route)
                    }
                })
                Spacer(modifier = Modifier.height(16.dp))
                NavigationDrawerItem(
                    label = { Text("Mi Perfil") },
                    selected = false,
                    onClick = { 
                        scope.launch { 
                            drawerState.close() 
                            navController.navigate(Screen.Profile.route)
                        } 
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    label = { Text("Adopciones") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate(Screen.Adoption.route)
                        }
                    },
                    icon = { Icon(Icons.Default.Pets, contentDescription = null) },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    label = { Text("Registrar Mascota") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate(Screen.AddPet.route)
                        }
                    },
                    icon = { Icon(Icons.Default.Add, contentDescription = null) },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    label = { Text("Donaciones y Membresías") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate(Screen.Subscriptions.route)
                        }
                    },
                    icon = { Icon(Icons.Default.VolunteerActivism, contentDescription = null) },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    label = { Text("Configuración") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } },
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp, horizontal = 28.dp))
                NavigationDrawerItem(
                    label = { Text("Cerrar Sesión") },
                    selected = false,
                    onClick = { 
                        scope.launch { 
                            drawerState.close()
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0) // Limpia el historial para seguridad
                            }
                        }
                    },
                    icon = { Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null) },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(modifier = Modifier.size(32.dp), shape = CircleShape, color = Color.White) {
                                Image(
                                    painter = painterResource(id = R.drawable.img), 
                                    contentDescription = null, 
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Rescatando Mascotas", color = PrimaryPurple, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú", tint = PrimaryPurple)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
            },
            bottomBar = {
                NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        label = { Text("Inicio") },
                        selected = true,
                        onClick = { }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Pets, contentDescription = null) },
                        label = { Text("Adopción") },
                        selected = false,
                        onClick = { navController.navigate(Screen.Adoption.route) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Assignment, contentDescription = null) },
                        label = { Text("Formularios") },
                        selected = false,
                        onClick = { navController.navigate(Screen.Forms.route) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = null) },
                        label = { Text("Perfil") },
                        selected = false,
                        onClick = { navController.navigate(Screen.Profile.route) }
                    )
                }
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { navController.navigate(Screen.AddPet.route) },
                    containerColor = PrimaryPurple,
                    contentColor = Color.White,
                    icon = { Icon(Icons.Default.Add, contentDescription = null) },
                    text = { Text("Registrar") }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(Color(0xFFF8F9FE))
                    .verticalScroll(rememberScrollState())
            ) {
                // Bienvenido Yeison
                Row(
                    modifier = Modifier.padding(20.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "¡Bienvenido, Amigo! 👋", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1A237E))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = PrimaryPurple, modifier = Modifier.size(16.dp))
                            Text(text = " Popayán, Cauca", fontSize = 14.sp, color = Color.Gray)
                        }
                    }
                    Surface(
                        modifier = Modifier.size(50.dp).clickable { navController.navigate(Screen.Profile.route) }, 
                        shape = CircleShape, 
                        color = Color.White, 
                        shadowElevation = 4.dp, 
                        border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryPurple)
                    ) {
                        Image(painter = painterResource(id = R.drawable.img), contentDescription = null, modifier = Modifier.padding(6.dp))
                    }
                }

                // Sección de Campaña con Mascota Flotante
                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .height(220.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(150.dp),
                        shape = RoundedCornerShape(32.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize().background(Brush.linearGradient(colors = listOf(Color(0xFF8E24AA), Color(0xFF5E35B1))))) {
                            Column(modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 120.dp)) {
                                Surface(color = Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp)) {
                                    Text(" CAMPAÑA 2026 ", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(4.dp))
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Un hogar lleno de\nladridos y amor", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, lineHeight = 22.sp)
                                Spacer(modifier = Modifier.height(12.dp))
                                Button(
                                    onClick = { navController.navigate(Screen.Adoption.route) }, 
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.White), 
                                    shape = RoundedCornerShape(12.dp), 
                                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Text("Ver más", color = PrimaryPurple, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }

                    // LA MASCOTA LLAMATIVA - IMPORTANTE: welcome_pet DEBE existir en drawable
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = (-10).dp, y = 0.dp)
                            .size(160.dp),
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 12.dp,
                        border = androidx.compose.foundation.BorderStroke(4.dp, Color.White)
                    ) {
                        // Intenta cargar welcome_pet, si no existe usa una patita por defecto para evitar crash
                        Image(
                            painter = painterResource(id = R.drawable.welcome_pet),
                            contentDescription = "Mascota Principal",
                            modifier = Modifier.fillMaxSize().clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                SectionHeader(title = "Busca un nuevo amigo", onSeeAll = { navController.navigate(Screen.Adoption.route) })
                LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    val categories = listOf(
                        CategoryItem("Todos", Icons.Default.GridView),
                        CategoryItem("Perro", Icons.Default.Pets),
                        CategoryItem("Gato", Icons.Default.Face),
                        CategoryItem("Conejo", Icons.Default.AutoAwesome),
                        CategoryItem("Suministros", Icons.Default.Storefront)
                    )
                    items(categories) { cat ->
                        CategoryCard(cat, isSelected = (selectedFilter == cat.name)) { viewModel.setFilter(cat.name) }
                    }
                }

                SectionHeader(title = "Mascotas esperando por ti", onSeeAll = { navController.navigate(Screen.Adoption.route) })
                LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(pets) { pet ->
                        FeaturedPetCard(pet) {
                            navController.navigate(Screen.PetDetail.createRoute(pet.id))
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Nueva Sección de Donación Rápida en el Home
                Card(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .clickable { navController.navigate(Screen.Subscriptions.route) },
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE1BEE7).copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(color = PrimaryPurple, shape = CircleShape, modifier = Modifier.size(40.dp)) {
                            Icon(Icons.Default.VolunteerActivism, contentDescription = null, tint = Color.White, modifier = Modifier.padding(10.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Apoya nuestra causa", fontWeight = FontWeight.Bold, color = Color.Black)
                            Text("Membresías y donaciones", fontSize = 12.sp, color = Color.Gray)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = PrimaryPurple)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun DrawerHeader(onProfileClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Brush.horizontalGradient(colors = listOf(PrimaryPurple, Color(0xFF9C27B0))))
            .clickable { onProfileClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(modifier = Modifier.size(80.dp), shape = CircleShape, color = Color.White, shadowElevation = 6.dp) {
                Image(painter = painterResource(id = R.drawable.img), contentDescription = "Logo Menú", modifier = Modifier.padding(12.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text("Rescatando Mascotas", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("unete@rescatando.com", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
        }
    }
}

@Composable
fun SectionHeader(title: String, onSeeAll: () -> Unit) {
    Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        TextButton(onClick = onSeeAll) { Text("Ver todas", color = PrimaryPurple, fontWeight = FontWeight.Bold) }
    }
}

data class CategoryItem(val name: String, val icon: ImageVector)

@Composable
fun CategoryCard(cat: CategoryItem, isSelected: Boolean, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            onClick = onClick,
            modifier = Modifier.size(70.dp),
            shape = RoundedCornerShape(20.dp),
            color = if (isSelected) PrimaryPurple else Color.White,
            shadowElevation = 2.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(cat.icon, contentDescription = null, tint = if (isSelected) Color.White else PrimaryPurple, modifier = Modifier.size(28.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = cat.name, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (isSelected) PrimaryPurple else Color.Gray)
    }
}

@Composable
fun FeaturedPetCard(pet: PetEntity, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.width(220.dp).height(240.dp), 
        shape = RoundedCornerShape(24.dp), 
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(160.dp)) {
                AsyncImage(
                    model = pet.foto_principal ?: "https://images.unsplash.com/photo-1552053831-71594a27632d",
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Surface(modifier = Modifier.align(Alignment.TopEnd).padding(12.dp).size(30.dp), shape = CircleShape, color = Color.White) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = PrimaryPurple, modifier = Modifier.size(14.dp).padding(6.dp))
                }
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = pet.nombre_mascota, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = PrimaryPurple, modifier = Modifier.size(12.dp))
                    Text(text = " ${pet.lugar_rescate}", fontSize = 11.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ver detalles", 
                    color = PrimaryPurple, 
                    fontSize = 12.sp, 
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}
