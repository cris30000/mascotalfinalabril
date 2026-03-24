package com.example.app_mascotascris.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
                DrawerHeader()
                Spacer(modifier = Modifier.height(16.dp))
                NavigationDrawerItem(
                    label = { Text("Mi Perfil") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    label = { Text("Mis Adopciones") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } },
                    icon = { Icon(Icons.Default.Pets, contentDescription = null) },
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
                    onClick = { navController.navigate(Screen.Login.route) },
                    icon = { Icon(Icons.Default.Logout, contentDescription = null) },
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
                                Image(painter = painterResource(id = R.drawable.img), contentDescription = null, modifier = Modifier.padding(4.dp))
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
                        Text(text = "¡Bienvenido, Yeison! 👋", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1A237E))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = PrimaryPurple, modifier = Modifier.size(16.dp))
                            Text(text = " Popayán, Cauca", fontSize = 14.sp, color = Color.Gray)
                        }
                    }
                    Surface(modifier = Modifier.size(56.dp), shape = CircleShape, color = Color.White, shadowElevation = 4.dp, border = androidx.compose.foundation.BorderStroke(2.dp, PrimaryPurple)) {
                        Image(painter = painterResource(id = R.drawable.img), contentDescription = null, modifier = Modifier.padding(8.dp))
                    }
                }

                // Banner CAMPAÑA 2026
                Card(
                    modifier = Modifier.padding(horizontal = 20.dp).fillMaxWidth().height(180.dp),
                    shape = RoundedCornerShape(32.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize().background(Brush.linearGradient(colors = listOf(Color(0xFF9575CD), Color(0xFF7986CB))))) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Surface(color = Color.White.copy(alpha = 0.3f), shape = RoundedCornerShape(8.dp)) {
                                Text(" CAMPAÑA 2026 ", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(4.dp))
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Un hogar lleno de\nronroneos y ladridos", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold, lineHeight = 26.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { navController.navigate(Screen.Adoption.route) }, colors = ButtonDefaults.buttonColors(containerColor = Color.White), shape = RoundedCornerShape(12.dp), contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)) {
                                Text("Ver más", color = PrimaryPurple, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // Busca un nuevo amigo
                SectionHeader(title = "Busca un nuevo amigo", onSeeAll = {})
                LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    val categories = listOf(
                        CategoryItem("Todos", Icons.Default.GridView),
                        CategoryItem("Perros", Icons.Default.Pets),
                        CategoryItem("Gatos", Icons.Default.Face),
                        CategoryItem("Otros", Icons.Default.AutoAwesome),
                        CategoryItem("Suministros", Icons.Default.Storefront)
                    )
                    items(categories) { cat ->
                        CategoryCard(cat, isSelected = selectedFilter == cat.name) { viewModel.setFilter(cat.name) }
                    }
                }

                // Mascotas esperando por ti
                SectionHeader(title = "Mascotas esperando por ti", onSeeAll = {})
                LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(pets) { pet ->
                        FeaturedPetCard(pet)
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(PrimaryPurple, Color(0xFF9C27B0))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                modifier = Modifier.size(90.dp),
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 6.dp
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = "Logo Menú",
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Rescatando Mascotas", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Text("unete@rescatando.com", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
        }
    }
}

@Composable
fun SectionHeader(title: String, onSeeAll: () -> Unit) {
    Row(modifier = Modifier.padding(20.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
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
fun FeaturedPetCard(pet: PetEntity) {
    Card(modifier = Modifier.width(240.dp).height(200.dp), shape = RoundedCornerShape(24.dp), elevation = CardDefaults.cardElevation(2.dp)) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = pet.imageUrl ?: "https://images.unsplash.com/photo-1552053831-71594a27632d",
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Surface(modifier = Modifier.align(Alignment.TopEnd).padding(12.dp).size(32.dp), shape = CircleShape, color = Color.White) {
                Icon(Icons.Default.Female, contentDescription = null, tint = Color(0xFFF06292), modifier = Modifier.size(16.dp).padding(6.dp))
            }
        }
    }
}
