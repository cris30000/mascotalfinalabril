package com.example.app_mascotascris.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.app_mascotascris.ui.theme.PrimaryPurple
import com.example.app_mascotascris.ui.viewmodel.PetViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetScreen(navController: NavController, viewModel: PetViewModel = viewModel()) {
    var currentStep by remember { mutableIntStateOf(1) }
    val totalSteps = 4

    // Form States
    var nombre by remember { mutableStateOf("") }
    var especie by remember { mutableStateOf("Perro") }
    var edad by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("Macho") }
    var estado by remember { mutableStateOf("Disponible") }
    
    var lugarRescate by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var condicionesEspeciales by remember { mutableStateOf("") }
    var fechaIngreso by remember { mutableStateOf(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())) }
    
    var necesitaHogarTemporal by remember { mutableStateOf(false) }
    var aptoConNinos by remember { mutableStateOf(true) }
    var aptoConOtros by remember { mutableStateOf(true) }
    
    var fotoPrincipalUri by remember { mutableStateOf<Uri?>(null) }
    var galeriaUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    val launcherPrincipal = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        fotoPrincipalUri = uri
    }
    
    val launcherGaleria = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        galeriaUris = uris
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Nueva Mascota", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = { 
                        if (currentStep > 1) currentStep-- else navController.popBackStack() 
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = PrimaryPurple
                )
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp,
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .navigationBarsPadding(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Progress Indicator
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Paso $currentStep de $totalSteps",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { currentStep.toFloat() / totalSteps },
                            modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                            color = PrimaryPurple,
                            trackColor = PrimaryPurple.copy(alpha = 0.1f)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(24.dp))

                    Button(
                        onClick = {
                            if (currentStep < totalSteps) {
                                currentStep++
                            } else {
                                // Save
                                viewModel.addPet(
                                    nombre = nombre,
                                    especie = especie,
                                    edad = edad,
                                    genero = genero,
                                    estado = estado,
                                    lugarRescate = lugarRescate,
                                    descripcion = descripcion,
                                    condiciones = condicionesEspeciales,
                                    fotoPrincipal = fotoPrincipalUri?.toString(),
                                    galeria = galeriaUris.joinToString(","),
                                    hogarTemporal = necesitaHogarTemporal,
                                    conNinos = aptoConNinos,
                                    conOtros = aptoConOtros,
                                    fechaIngreso = fechaIngreso,
                                    fundacionId = 1 // Default
                                )
                                navController.popBackStack()
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                        modifier = Modifier.height(50.dp)
                    ) {
                        Text(if (currentStep == totalSteps) "Finalizar" else "Siguiente")
                        if (currentStep < totalSteps) {
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.padding(start = 8.dp).size(18.dp))
                        }
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFFBFBFF))
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Crossfade(targetState = currentStep, label = "FormSteps") { step ->
                when (step) {
                    1 -> StepBasicInfo(
                        nombre, { nombre = it },
                        especie, { especie = it },
                        edad, { edad = it },
                        genero, { genero = it },
                        estado, { estado = it }
                    )
                    2 -> StepRescueInfo(
                        lugarRescate, { lugarRescate = it },
                        fechaIngreso, { fechaIngreso = it },
                        descripcion, { descripcion = it },
                        condicionesEspeciales, { condicionesEspeciales = it }
                    )
                    3 -> StepCompatibility(
                        necesitaHogarTemporal, { necesitaHogarTemporal = it },
                        aptoConNinos, { aptoConNinos = it },
                        aptoConOtros, { aptoConOtros = it }
                    )
                    4 -> StepMedia(
                        fotoPrincipalUri, { launcherPrincipal.launch("image/*") },
                        galeriaUris, { launcherGaleria.launch("image/*") }
                    )
                }
            }
        }
    }
}

@Composable
fun StepBasicInfo(
    nombre: String, onNombreChange: (String) -> Unit,
    especie: String, onEspecieChange: (String) -> Unit,
    edad: String, onEdadChange: (String) -> Unit,
    genero: String, onGeneroChange: (String) -> Unit,
    estado: String, onEstadoChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        FormHeader(title = "Información Básica", subtitle = "Cuéntanos quién es el nuevo integrante.")
        
        CustomTextField(value = nombre, onValueChange = onNombreChange, label = "Nombre de la mascota", icon = Icons.Default.Pets)
        
        Text("Especie", fontWeight = FontWeight.Bold, color = PrimaryPurple, fontSize = 14.sp)
        val especies = listOf("Perro", "Gato", "Conejo", "Ave", "Otro")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(especies) { e ->
                FilterChip(
                    selected = especie == e,
                    onClick = { onEspecieChange(e) },
                    label = { Text(e) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PrimaryPurple,
                        selectedLabelColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                CustomTextField(value = edad, onValueChange = onEdadChange, label = "Edad aprox.", icon = Icons.Default.Cake)
            }
            Box(modifier = Modifier.weight(1f)) {
                Column {
                    Text("Género", fontWeight = FontWeight.Bold, color = PrimaryPurple, fontSize = 14.sp, modifier = Modifier.padding(bottom = 8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        listOf("Macho", "Hembra").forEach { g ->
                            val isSelected = genero == g
                            Surface(
                                modifier = Modifier.weight(1f).height(45.dp).clickable { onGeneroChange(g) },
                                color = if (isSelected) PrimaryPurple else Color.White,
                                shape = RoundedCornerShape(12.dp),
                                border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(g, color = if (isSelected) Color.White else Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }

        Text("Estado actual", fontWeight = FontWeight.Bold, color = PrimaryPurple, fontSize = 14.sp)
        val estados = listOf("Disponible", "En Rescate", "Adoptado", "Urgente")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(estados) { est ->
                FilterChip(
                    selected = estado == est,
                    onClick = { onEstadoChange(est) },
                    label = { Text(est) },
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepRescueInfo(
    lugar: String, onLugarChange: (String) -> Unit,
    fecha: String, onFechaChange: (String) -> Unit,
    descripcion: String, onDescripcionChange: (String) -> Unit,
    condiciones: String, onCondicionesChange: (String) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        onFechaChange(sdf.format(Date(millis)))
                    }
                    showDatePicker = false
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        FormHeader(title = "Rescate e Historia", subtitle = "Detalles sobre cómo llegó a nosotros.")
        
        CustomTextField(value = lugar, onValueChange = onLugarChange, label = "Lugar de rescate", icon = Icons.Default.LocationOn)
        
        // Campo de fecha clicable para abrir el calendario
        Box(modifier = Modifier.fillMaxWidth().clickable { showDatePicker = true }) {
            CustomTextField(
                value = fecha, 
                onValueChange = { }, // Solo lectura, cambia vía datepicker
                label = "Fecha de ingreso", 
                icon = Icons.Default.CalendarToday,
                enabled = false // Deshabilitamos escritura manual
            )
            // Overlay invisible para capturar el click ya que el TextField disabled puede no capturarlo bien
            Box(modifier = Modifier.matchParentSize().clickable { showDatePicker = true })
        }

        CustomTextField(
            value = descripcion, 
            onValueChange = onDescripcionChange, 
            label = "Descripción / Historia", 
            icon = Icons.Default.Description,
            singleLine = false,
            minLines = 3
        )

        CustomTextField(
            value = condiciones, 
            onValueChange = onCondicionesChange, 
            label = "Condiciones especiales (Salud, dieta...)", 
            icon = Icons.Default.MedicalServices,
            singleLine = false,
            minLines = 2
        )
    }
}

@Composable
fun StepCompatibility(
    hogar: Boolean, onHogarChange: (Boolean) -> Unit,
    ninos: Boolean, onNinosChange: (Boolean) -> Unit,
    otros: Boolean, onOtrosChange: (Boolean) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        FormHeader(title = "Convivencia y Necesidades", subtitle = "¿Cómo se relaciona con los demás?")

        CompatibilitySwitch(
            title = "¿Necesita hogar temporal?", 
            subtitle = "Marcar si requiere tránsito urgente.", 
            checked = hogar, 
            onCheckedChange = onHogarChange,
            icon = Icons.Default.Home
        )
        
        CompatibilitySwitch(
            title = "¿Es apto con niños?", 
            subtitle = "Ideal para familias con pequeños.", 
            checked = ninos, 
            onCheckedChange = onNinosChange,
            icon = Icons.Default.ChildCare
        )

        CompatibilitySwitch(
            title = "¿Es apto con otros animales?", 
            subtitle = "Perros, gatos u otras especies.", 
            checked = otros, 
            onCheckedChange = onOtrosChange,
            icon = Icons.Default.Groups
        )
    }
}

@Composable
fun StepMedia(
    principal: Uri?, onPickPrincipal: () -> Unit,
    galeria: List<Uri>, onPickGaleria: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        FormHeader(title = "Fotos de la Mascota", subtitle = "Una imagen vale más que mil palabras.")

        Text("Foto Principal", fontWeight = FontWeight.Bold, color = PrimaryPurple)
        Card(
            modifier = Modifier.fillMaxWidth().height(200.dp).clickable { onPickPrincipal() },
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            if (principal != null) {
                AsyncImage(model = principal, contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.AddAPhoto, contentDescription = null, modifier = Modifier.size(40.dp), tint = PrimaryPurple)
                        Text("Subir foto de portada", color = PrimaryPurple, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Galería de fotos", fontWeight = FontWeight.Bold, color = PrimaryPurple)
            TextButton(onClick = onPickGaleria) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Agregar más")
            }
        }

        if (galeria.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth().height(120.dp).border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)).clickable { onPickGaleria() },
                contentAlignment = Alignment.Center
            ) {
                Text("No hay fotos en la galería", color = Color.Gray, fontSize = 14.sp)
            }
        } else {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(galeria) { uri ->
                    AsyncImage(
                        model = uri, 
                        contentDescription = null, 
                        modifier = Modifier.size(120.dp).clip(RoundedCornerShape(16.dp)), 
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
fun FormHeader(title: String, subtitle: String) {
    Column {
        Text(title, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1A237E))
        Text(subtitle, fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun CustomTextField(
    value: String, 
    onValueChange: (String) -> Unit, 
    label: String, 
    icon: ImageVector,
    singleLine: Boolean = true,
    minLines: Int = 1,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null, tint = PrimaryPurple, modifier = Modifier.size(20.dp)) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PrimaryPurple,
            unfocusedBorderColor = Color.LightGray,
            focusedLabelColor = PrimaryPurple,
            disabledBorderColor = Color.LightGray,
            disabledLabelColor = Color.Gray,
            disabledTextColor = Color.Black
        ),
        singleLine = singleLine,
        minLines = minLines,
        enabled = enabled
    )
}

@Composable
fun CompatibilitySwitch(
    title: String, 
    subtitle: String, 
    checked: Boolean, 
    onCheckedChange: (Boolean) -> Unit,
    icon: ImageVector
) {
    Surface(
        onClick = { onCheckedChange(!checked) },
        color = Color.White,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (checked) PrimaryPurple else Color.LightGray.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = if (checked) PrimaryPurple.copy(alpha = 0.1f) else Color(0xFFF5F5F5),
                shape = CircleShape,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(icon, contentDescription = null, tint = if (checked) PrimaryPurple else Color.Gray, modifier = Modifier.padding(10.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = if (checked) PrimaryPurple else Color.Black)
                Text(subtitle, fontSize = 11.sp, color = Color.Gray)
            }
            Switch(
                checked = checked, 
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = PrimaryPurple)
            )
        }
    }
}
