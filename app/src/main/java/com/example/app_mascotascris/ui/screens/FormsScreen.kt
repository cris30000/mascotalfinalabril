package com.example.app_mascotascris.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.FactCheck
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.app_mascotascris.ui.theme.PrimaryPurple
import com.example.app_mascotascris.ui.viewmodel.PetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormsScreen(navController: NavController, viewModel: PetViewModel = viewModel()) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Adopción", "Rescatista", "Fundación")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Formularios Oficiales", color = Color.White, fontWeight = FontWeight.Bold) },
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
            // Header llamativo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(Brush.verticalGradient(listOf(PrimaryPurple, Color(0xFF6A1B9A)))),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = CircleShape,
                        modifier = Modifier.size(50.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Assignment, contentDescription = null, tint = Color.White, modifier = Modifier.padding(12.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Únete a nuestra misión",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        "Completa los datos para continuar",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 13.sp
                    )
                }
            }

            // Selector de Tabs moderno
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = PrimaryPurple,
                divider = {},
                indicator = { tabPositions ->
                    if (selectedTab < tabPositions.size) {
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = PrimaryPurple,
                            height = 3.dp
                        )
                    }
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                title,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 14.sp
                            )
                        }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                when (selectedTab) {
                    0 -> AdoptionForm(viewModel) { navController.popBackStack() }
                    1 -> RescuerForm()
                    2 -> FoundationForm()
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun AdoptionForm(viewModel: PetViewModel, onSuccess: () -> Unit) {
    var petName by remember { mutableStateOf("") }
    var applicantName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("") }
    var housing by remember { mutableStateOf("") }
    var hasOtherPets by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        FormSectionTitle(Icons.Default.Pets, "Datos de la Mascota")
        CustomTextField(value = petName, onValueChange = { petName = it }, label = "Nombre de la Mascota", icon = Icons.Default.Pets)

        FormSectionTitle(Icons.Default.Person, "Información Personal")
        CustomTextField(value = applicantName, onValueChange = { applicantName = it }, label = "Tu Nombre Completo", icon = Icons.Default.Badge)
        CustomTextField(value = phone, onValueChange = { phone = it }, label = "Teléfono de contacto", icon = Icons.Default.Phone)
        
        FormSectionTitle(Icons.Default.Home, "Entorno y Experiencia")
        CustomTextField(value = reason, onValueChange = { reason = it }, label = "¿Por qué desea adoptar?", icon = Icons.Default.QuestionAnswer, isMultiLine = true)
        CustomTextField(value = housing, onValueChange = { housing = it }, label = "Tipo de vivienda (Casa/Apto)", icon = Icons.Default.Apartment)
        CustomTextField(value = hasOtherPets, onValueChange = { hasOtherPets = it }, label = "¿Tiene otras mascotas?", icon = Icons.Default.Pets)
        
        SubmitButton("Enviar Solicitud de Adopción") {
            if (petName.isNotBlank() && applicantName.isNotBlank() && phone.isNotBlank()) {
                viewModel.addAdoptionForm(petName, applicantName, phone, reason, housing, hasOtherPets)
                onSuccess()
            }
        }
    }
}

@Composable
fun RescuerForm() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        FormSectionTitle(Icons.Default.HealthAndSafety, "Datos de Rescatista")
        CustomTextField(value = "", onValueChange = {}, label = "Nombre Completo", icon = Icons.Default.Person)
        CustomTextField(value = "", onValueChange = {}, label = "Cédula / ID", icon = Icons.Default.ContactPage)
        CustomTextField(value = "", onValueChange = {}, label = "Zona de operación (Barrio/Ciudad)", icon = Icons.Default.Map)
        CustomTextField(value = "", onValueChange = {}, label = "Experiencia previa", icon = Icons.Default.History, isMultiLine = true)
        
        SubmitButton("Registrarme como Rescatista") {}
    }
}

@Composable
fun FoundationForm() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        FormSectionTitle(Icons.Default.Business, "Datos Legales")
        CustomTextField(value = "", onValueChange = {}, label = "Nombre de la Fundación", icon = Icons.Default.CorporateFare)
        CustomTextField(value = "", onValueChange = {}, label = "NIT / Registro Legal", icon = Icons.AutoMirrored.Filled.FactCheck)
        CustomTextField(value = "", onValueChange = {}, label = "Dirección Física", icon = Icons.Default.LocationOn)
        CustomTextField(value = "", onValueChange = {}, label = "Capacidad Máxima (Mascotas)", icon = Icons.Default.Groups)
        
        SubmitButton("Afiliar mi Fundación") {}
    }
}

@Composable
fun FormSectionTitle(icon: ImageVector, title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Icon(icon, contentDescription = null, tint = PrimaryPurple, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(title, fontWeight = FontWeight.Bold, color = Color.DarkGray, fontSize = 16.sp)
    }
}

@Composable
fun CustomTextField(value: String, onValueChange: (String) -> Unit, label: String, icon: ImageVector, isMultiLine: Boolean = false) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null, tint = PrimaryPurple.copy(alpha = 0.6f)) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PrimaryPurple,
            unfocusedBorderColor = Color.LightGray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        minLines = if (isMultiLine) 3 else 1
    )
}

@Composable
fun SubmitButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(top = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple)
    ) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}
