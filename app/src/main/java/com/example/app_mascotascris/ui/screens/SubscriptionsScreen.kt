package com.example.app_mascotascris.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_mascotascris.ui.theme.PrimaryPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionsScreen(navController: NavController) {
    val plans = listOf(
        SubscriptionPlan(
            "Básico", 
            "$5", 
            "/ mes", 
            listOf("Insignia de donante", "Newsletter mensual", "Acceso a foro"),
            Color(0xFFE1BEE7)
        ),
        SubscriptionPlan(
            "Héroe", 
            "$15", 
            "/ mes", 
            listOf("Insignia de Héroe", "Acceso a eventos VIP", "10% descuento en tienda", "Certificado digital"),
            Color(0xFFCE93D8),
            isPopular = true
        ),
        SubscriptionPlan(
            "Guardián", 
            "$30", 
            "/ mes", 
            listOf("Insignia de Guardián", "Padrino de una mascota", "Kit de bienvenida físico", "Voto en asambleas"),
            Color(0xFFBA68C8)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Membresías", color = Color.White, fontWeight = FontWeight.Bold) },
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
            // Header con Gradiente
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(PrimaryPurple, Color(0xFF9C27B0))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Sé parte del cambio",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Tu suscripción mensual nos ayuda a rescatar y alimentar a cientos de mascotas cada día.",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(plans) { plan ->
                    SubscriptionCard(plan)
                }
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

data class SubscriptionPlan(
    val name: String, 
    val price: String, 
    val period: String, 
    val benefits: List<String>,
    val accentColor: Color,
    val isPopular: Boolean = false
)

@Composable
fun SubscriptionCard(plan: SubscriptionPlan) {
    Box {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = plan.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1A237E)
                        )
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = plan.price,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Black,
                                color = PrimaryPurple
                            )
                            Text(
                                text = plan.period,
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                            )
                        }
                    }
                    Surface(
                        modifier = Modifier.size(50.dp),
                        shape = CircleShape,
                        color = plan.accentColor.copy(alpha = 0.2f)
                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = PrimaryPurple,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(20.dp))

                plan.benefits.forEach { benefit ->
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = benefit,
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { /* Lógica de pago */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (plan.isPopular) PrimaryPurple else Color(0xFF1A237E)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        "Suscribirme Ahora",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }

        if (plan.isPopular) {
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 16.dp, end = 16.dp),
                color = Color(0xFFFFC107),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "MÁS POPULAR",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black
                )
            }
        }
    }
}
