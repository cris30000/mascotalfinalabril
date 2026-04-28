package com.example.app_mascotascris.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    val tabs = listOf("Solicitud Adopción", "Rescatista", "Fundación")

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
            if (selectedTab == 0) {
                // El formulario de adopción ahora es su propio flujo paginado
                AdoptionWizard(onCancel = { navController.popBackStack() }, viewModel = viewModel)
            } else {
                // Otros formularios
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    TabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = Color.Transparent,
                        contentColor = PrimaryPurple
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                text = { Text(title, fontSize = 12.sp) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    when (selectedTab) {
                        1 -> RescuerForm()
                        2 -> FoundationForm()
                    }
                }
            }
        }
    }
}

@Composable
fun AdoptionWizard(onCancel: () -> Unit, viewModel: PetViewModel) {
    var currentStep by remember { mutableIntStateOf(1) }
    val totalSteps = 3

    // State del Formulario (Basado en los campos solicitados)
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var identificacion by remember { mutableStateOf("") }
    
    // Estos estados son para la entidad AdoptionEntity que pediste
    var fechaAdopcion by remember { mutableStateOf("") } // id	fecha_adopcion	estado	razon_rechazo...
    var estadoAdopcion by remember { mutableStateOf("Pendiente") }
    
    var tipoVivienda by remember { mutableStateOf("Casa") }
    var tienePatio by remember { mutableStateOf(false) }
    
    var razonAdopcion by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        // Progress Bar con estilo profesional
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryPurple)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Paso $currentStep de $totalSteps", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text(
                        when(currentStep) {
                            1 -> "Datos del Adoptante"
                            2 -> "Entorno del Hogar"
                            else -> "Finalizar Solicitud"
                        },
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { currentStep.toFloat() / totalSteps },
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.3f)
                )
            }
        }

        // Form Content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Crossfade(targetState = currentStep, label = "AdoptionSteps") { step ->
                when (step) {
                    1 -> StepUserPersonal(nombre, { nombre = it }, identificacion, { identificacion = it }, telefono, { telefono = it }, correo, { correo = it })
                    2 -> StepUserHome(tipoVivienda, { tipoVivienda = it }, tienePatio, { tienePatio = it })
                    3 -> StepUserFinal(razonAdopcion, { razonAdopcion = it })
                }
            }
        }

        // Footer Actions
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 16.dp,
            color = Color.White
        ) {
            Row(
                modifier = Modifier.padding(20.dp).navigationBarsPadding(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (currentStep > 1) {
                    OutlinedButton(
                        onClick = { currentStep-- },
                        modifier = Modifier.weight(1f).height(50.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Atrás")
                    }
                }
                
                Button(
                    onClick = {
                        if (currentStep < totalSteps) {
                            currentStep++
                        } else {
                            // Aquí se guardaría la solicitud final
                            // Usamos el ViewModel para persistir
                            viewModel.addAdoptionForm(
                                petName = "Mascota", 
                                applicantName = nombre,
                                phone = telefono,
                                reason = razonAdopcion,
                                housing = tipoVivienda,
                                hasOtherPets = "No"
                            )
                            onCancel()
                        }
                    },
                    modifier = Modifier.weight(2f).height(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple)
                ) {
                    Text(if (currentStep == totalSteps) "Enviar Solicitud" else "Siguiente")
                    if (currentStep < totalSteps) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.padding(start = 8.dp).size(18.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun StepUserPersonal(
    nombre: String, onNombreChange: (String) -> Unit,
    id: String, onIdChange: (String) -> Unit,
    tel: String, onTelChange: (String) -> Unit,
    mail: String, onMailChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Información Personal", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1A237E))
        Text("Completa tus datos para iniciar el proceso de adopción.", fontSize = 14.sp, color = Color.Gray)
        
        CustomTextField(value = nombre, onValueChange = onNombreChange, label = "Nombre Completo", icon = Icons.Default.Person)
        CustomTextField(value = id, onValueChange = onIdChange, label = "Cédula / Documento", icon = Icons.Default.Badge)
        CustomTextField(value = tel, onValueChange = onTelChange, label = "Teléfono móvil", icon = Icons.Default.Phone)
        CustomTextField(value = mail, onValueChange = onMailChange, label = "Correo electrónico", icon = Icons.Default.Email)
    }
}

@Composable
fun StepUserHome(
    tipo: String, onTipoChange: (String) -> Unit,
    patio: Boolean, onPatioChange: (Boolean) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Tu Entorno", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1A237E))
        Text("Queremos asegurar que la mascota tenga un espacio cómodo.", fontSize = 14.sp, color = Color.Gray)

        Text("¿Tipo de vivienda?", fontWeight = FontWeight.Bold, color = PrimaryPurple)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Casa", "Apartamento").forEach { t ->
                val isSelected = tipo == t
                Surface(
                    modifier = Modifier.weight(1f).height(50.dp).clickable { onTipoChange(t) },
                    color = if (isSelected) PrimaryPurple else Color.White,
                    shape = RoundedCornerShape(12.dp),
                    border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(t, color = if (isSelected) Color.White else Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Surface(
            onClick = { onPatioChange(!patio) },
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, if (patio) PrimaryPurple else Color.LightGray.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Park, contentDescription = null, tint = if (patio) PrimaryPurple else Color.Gray)
                Spacer(modifier = Modifier.width(16.dp))
                Text("¿Cuenta con zona verde / patio?", modifier = Modifier.weight(1f))
                Switch(checked = patio, onCheckedChange = onPatioChange, colors = SwitchDefaults.colors(checkedTrackColor = PrimaryPurple))
            }
        }
    }
}

@Composable
fun StepUserFinal(razon: String, onRazonChange: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Motivación", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1A237E))
        Text("Por último, cuéntanos por qué quieres ser parte de la vida de esta mascota.", fontSize = 14.sp, color = Color.Gray)

        CustomTextField(
            value = razon, 
            onValueChange = onRazonChange, 
            label = "¿Por qué deseas adoptar?", 
            icon = Icons.Default.QuestionAnswer,
            isMultiLine = true
        )
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EAF6)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF3F51B5))
                Spacer(modifier = Modifier.width(12.dp))
                Text("Al enviar, la fundación revisará tu perfil y te contactará.", fontSize = 12.sp, color = Color(0xFF3F51B5))
            }
        }
    }
}

@Composable
fun RescuerForm() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        FormSectionTitle(Icons.Default.HealthAndSafety, "Datos de Rescatista")
        CustomTextField(value = "", onValueChange = {}, label = "Nombre Completo", icon = Icons.Default.Person)
        CustomTextField(value = "", onValueChange = {}, label = "Zona de operación", icon = Icons.Default.Map)
        SubmitButton("Registrarme") {}
    }
}

@Composable
fun FoundationForm() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        FormSectionTitle(Icons.Default.Business, "Datos Legales")
        CustomTextField(value = "", onValueChange = {}, label = "Nombre de la Fundación", icon = Icons.Default.CorporateFare)
        CustomTextField(value = "", onValueChange = {}, label = "NIT / Registro Legal", icon = Icons.AutoMirrored.Filled.Assignment)
        SubmitButton("Afiliar Fundación") {}
    }
}

@Composable
fun FormSectionTitle(icon: ImageVector, title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = PrimaryPurple, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(title, fontWeight = FontWeight.Bold, color = Color.DarkGray)
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
        minLines = if (isMultiLine) 3 else 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PrimaryPurple,
            unfocusedBorderColor = Color.LightGray
        )
    )
}

@Composable
fun SubmitButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple)
    ) {
        Text(text, fontWeight = FontWeight.Bold)
    }
}
