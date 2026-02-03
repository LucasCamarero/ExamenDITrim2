package com.example.tictactoe

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme{
                val navController = rememberNavController()
                val vm: TicTacToeViewModel = viewModel()
                val state by vm.state.collectAsState()

                // Estructura principal de la interfaz
                Scaffold(
                    bottomBar = { BarraInferior(navController) }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(MaterialTheme.colorScheme.background),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        // definición de rutas de pantallas
                        NavHost(
                            navController = navController,
                            startDestination = "home"
                        ) {
                            composable("home") {
                                TicTacToeScreen(state, vm)
                            }
                            composable("history") {
                                HistoryScreen(state, vm)
                            }
                        }
                    }
                }
            }
        }
    }

    // Barra Inferior
    @Composable
    fun BarraInferior(navController: NavHostController) {

        // Para el intend
        val context = LocalContext.current

        Row (modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            // Compartir
            IconButton(onClick = {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "¡Mira este juego increíble de 3 en raya! Descárgalo y juega ahora."
                    )
                    putExtra(Intent.EXTRA_SUBJECT, "3 en raya")
                }
                context.startActivity(
                    Intent.createChooser(shareIntent, "Compartir juego vía")
                )
            }) {
                Icon(Icons.Default.Share, contentDescription = "compartir")
            }

            NavigationBar (containerColor = MaterialTheme.colorScheme.primary){

                // Home
                NavigationBarItem(
                    icon = { Icon(Icons.Default.PlayArrow, contentDescription = "Home") },
                    selected = navController.currentBackStackEntry?.destination?.route == "home",
                    onClick = {
                        navController.navigate("home") {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    label = { Text("Jugar",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.background,
                        selectedTextColor = MaterialTheme.colorScheme.background,
                        unselectedIconColor = MaterialTheme.colorScheme.background,
                        unselectedTextColor = MaterialTheme.colorScheme.background,
                        indicatorColor = Color.Transparent
                    )
                )

                // History
                NavigationBarItem(
                    icon = { Icon(Icons.Default.AccountCircle, contentDescription = "History") },
                    selected = navController.currentBackStackEntry?.destination?.route == "history",
                    onClick = {
                        navController.navigate("history") {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    label = { Text("Historial",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.background,
                        selectedTextColor = MaterialTheme.colorScheme.background,
                        unselectedIconColor = MaterialTheme.colorScheme.background,
                        unselectedTextColor = MaterialTheme.colorScheme.background,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}
