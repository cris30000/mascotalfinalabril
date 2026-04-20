package com.example.app_mascotascris.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_mascotascris.ui.theme.PrimaryPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionsScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Suscripciones, 1: Donación Única

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Apoyo al Refugio", color = Color.White, fontWeight = FontWeight.Bold) },
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
                .background(Color(0xFFF8F9FE))
        ) {
            // Header con Gradiente y Diseño Curvo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(PrimaryPurple, Color(0xFF6A1B9A))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(20.dp)) {
                    Surface(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = CircleShape,
                        modifier = Modifier.size(60.dp)
                    ) {
                        Icon(Icons.Default.VolunteerActivism, contentDescription = null, tint = Color.White, modifier = Modifier.padding(15.dp))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "Tu ayuda salva vidas",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        "Cada aporte se convierte en alimento y medicinas",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Selector Profesional (Tabs)
            Row(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TabButton(
                    modifier = Modifier.weight(1f),
                    text = "Membresías",
                    isSelected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                TabButton(
                    modifier = Modifier.weight(1f),
                    text = "Donar una vez",
                    isSelected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
            }

            if (selectedTab == 0) {
                SubscriptionList()
            } else {
                OneTimeDonationView()
            }
        }
    }
}

@Composable
fun TabButton(modifier: Modifier, text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .padding(4.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) PrimaryPurple else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Composable
fun SubscriptionList() {
    val plans = listOf(
        SubscriptionPlan("Amigo", "$5", "Newsletter, insignia digital", Color(0xFFF3E5F5), Icons.Default.FavoriteBorder),
        SubscriptionPlan("Héroe", "$15", "VIP Access, 10% tienda, Certificado", Color(0xFFE1BEE7), Icons.Default.Star, true),
        SubscriptionPlan("Guardián", "$30", "Padrino mascota, Kit físico, Voto", Color(0xFFCE93D8), Icons.Default.VerifiedUser)
    )

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(plans) { plan ->
            SubscriptionItem(plan)
        }
    }
}

@Composable
fun OneTimeDonationView() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Selecciona un monto", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF1A237E))
        Spacer(modifier = Modifier.height(16.dp))
        
        val amounts = listOf("$10", "$20", "$50", "$100")
        var selectedAmount by remember { mutableStateOf("$50") }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            amounts.forEach { amount ->
                AmountChip(
                    modifier = Modifier.weight(1f),
                    amount = amount,
                    isSelected = selectedAmount == amount,
                    onClick = { selectedAmount = amount }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("¿A qué se destinará tu donación?", fontWeight = FontWeight.Bold, color = PrimaryPurple)
                Spacer(modifier = Modifier.height(12.dp))
                DonationDestinyRow(Icons.Default.Restaurant, "Alimentación balanceada")
                DonationDestinyRow(Icons.Default.MedicalServices, "Atención veterinaria de urgencia")
                DonationDestinyRow(Icons.Default.CleanHands, "Productos de higiene y limpieza")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { /* PayPal/Stripe */ },
            modifier = Modifier.fillMaxWidth().height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
            shape = RoundedCornerShape(20.dp)
        ) {
            Icon(Icons.Default.Payment, contentDescription = null)
            Spacer(modifier = Modifier.width(12.dp))
            Text("Donar con tarjeta", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SubscriptionItem(plan: SubscriptionPlan) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(modifier = Modifier.size(56.dp), shape = RoundedCornerShape(16.dp), color = plan.accentColor) {
                Icon(plan.icon, contentDescription = null, tint = PrimaryPurple, modifier = Modifier.padding(14.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(plan.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    if (plan.isPopular) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(color = Color(0xFFFFC107), shape = RoundedCornerShape(4.dp)) {
                            Text("HOT", modifier = Modifier.padding(horizontal = 4.dp), fontSize = 8.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
                Text(plan.price + " / mes", color = PrimaryPurple, fontWeight = FontWeight.Bold)
                Text(plan.benefits, fontSize = 12.sp, color = Color.Gray)
            }
            IconButton(onClick = { /* Ir a pagar */ }) {
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.LightGray)
            }
        }
    }
}

@Composable
fun AmountChip(modifier: Modifier, amount: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = modifier.height(50.dp).clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) PrimaryPurple else Color.White,
        border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(amount, fontWeight = FontWeight.Bold, color = if (isSelected) Color.White else Color.Black)
        }
    }
}

@Composable
fun DonationDestinyRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, fontSize = 13.sp, color = Color.DarkGray)
    }
}

data class SubscriptionPlan(
    val name: String, val price: String, val benefits: String, 
    val accentColor: Color, val icon: ImageVector, val isPopular: Boolean = false
)
