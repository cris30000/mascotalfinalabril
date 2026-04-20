package com.example.app_mascotascris.ui.screens

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.app_mascotascris.data.local.entities.PetEntity
import com.example.app_mascotascris.navigation.Screen
import com.example.app_mascotascris.ui.theme.PrimaryPurple
import com.example.app_mascotascris.ui.theme.LightGray
import com.example.app_mascotascris.ui.viewmodel.PetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdoptionScreen(navController: NavController, viewModel: PetViewModel = viewModel()) {
    val pets by viewModel.pets.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val filters = listOf("Todos", "Perro", "Gato", "Conejo", "Aves")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adopciones", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryPurple)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            // Banner "¿Sabías que...?"
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(140.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF8E24AA), Color(0xFF3949AB))
                            )
                        )
                        .padding(20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "¿Sabías que...?",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Adoptar salva dos vidas: la del animal que llevas a casa y la del siguiente que puede ocupar su lugar.",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                        }
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.3f),
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }
            }

            // Sección: Pasos para adoptar
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Pasos para adoptar",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A237E)
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                val steps = listOf(
                    "Elige a tu mascota ideal de nuestra lista.",
                    "Completa el formulario de solicitud oficial.",
                    "Realizaremos una pequeña entrevista y visita.",
                    "¡Firma el acta y llévalo a su nuevo hogar!"
                )
                
                steps.forEachIndexed { index, step ->
                    Row(
                        modifier = Modifier.padding(bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.size(24.dp),
                            shape = CircleShape,
                            color = Color(0xFF1A237E)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text((index + 1).toString(), color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = step, fontSize = 14.sp, color = Color.DarkGray)
                    }
                }
            }

            // Botón "Ir al Formulario"
            Button(
                onClick = { navController.navigate(Screen.Forms.route) },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Ir al Formulario de Adopción", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sección: Mascotas Disponibles con Filtrado
            Text(
                text = "Mascotas esperando un hogar",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            LazyRow(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filters) { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { viewModel.setFilter(filter) },
                        label = { Text(filter) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryPurple,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            // Lista de Mascotas (Horizontal)
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.height(240.dp)
            ) {
                items(pets) { pet ->
                    PetAdoptionCard(pet) {
                        navController.navigate(Screen.PetDetail.createRoute(pet.id))
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun PetAdoptionCard(pet: PetEntity, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = LightGray.copy(alpha = 0.5f))
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.LightGray)
            ) {
                AsyncImage(
                    model = pet.imageUrl ?: "https://images.unsplash.com/photo-1552053831-71594a27632d",
                    contentDescription = pet.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(pet.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(pet.type, style = MaterialTheme.typography.bodySmall, color = PrimaryPurple)
                Text(pet.age, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Ver detalles", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PrimaryPurple)
            }
        }
    }
}
