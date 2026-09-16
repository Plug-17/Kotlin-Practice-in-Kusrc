@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lab24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.lab24.ui.theme.Lab24Theme
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Lab24Theme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier){

        val navController = rememberNavController()
        var selectedItem by remember { mutableStateOf(0) }
        val items = listOf("Home", "History")
        val iconsmenu = listOf(R.drawable.ic_launcher_foreground, R.drawable.rocket)
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF6D9E51),
                        titleContentColor = Color(0xFFFEFFD3)
                    ),
                    title = { Text("Shop App") },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(painter = painterResource(R.drawable.grocery_store),
                                contentDescription = null, tint = Color.White)
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = Color(0xFF6D9E51),
                    contentColor = Color(0xFFFEFFD3)
                ) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = {
                                Icon(painter = painterResource(iconsmenu[index]),
                                    contentDescription = item) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index
                                when(index) {
                                    0 -> navController.navigate("home")
                                    1 -> navController.navigate("history")
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                unselectedIconColor = Color.White,
                                indicatorColor = Color(0xFFCDB885)
                            )
                        )
                    }
                }
            },
            floatingActionButton = {
                if(currentRoute == "home" || currentRoute == "history") {
                    FloatingActionButton(
                        onClick = { navController.navigate("order") },
                        containerColor = Color(0xFF6D9E51)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.plus),
                            contentDescription = "สั่งเพิ่ม",
                            tint = Color.White
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home") { HomeScreen() }
                composable("order") { OrderScreen(
                    onOrderClick = { navController.navigate("history") }
                ) }
                composable("history") { HistoryScreen(
                    onEditClick = { orderId ->
                        navController.navigate("edit_order/$orderId")
                    }
                ) }
                composable("edit_order/{orderid}",
                    arguments = listOf(navArgument("orderid") { type = NavType.StringType })) {
                        stackEntry -> val orderid = stackEntry.arguments?.getString("orderid") ?: ""
                    EditOrderScreen(orderID = orderid, onBack = { navController.popBackStack() } )
                }
            }
        }
    }





@Composable
    fun HomeScreen(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("หน้าแรก")
            }
        }
    }






