@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lab24

import android.app.Notification
import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.then
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.lab24.ui.theme.Lab24Theme
import androidx.navigation.compose.composable
import  androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab24Theme {
               MyApp()
            }
        }
    }
}

@Composable
fun MyApp(){
    var nacontroller = rememberNavController()
    var selection by remember { mutableStateOf(0) }
    val items = arrayOf("Home","Cart","Notification")
    val icon = listOf(R.drawable.home,
        R.drawable.grocery_store,R.drawable.notification)
    Scaffold(
        topBar = {
            TopAppBar(
              colors =  TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Yellow,
                    titleContentColor = Color.Blue
                ),
                title = {Text("MyApp")},
                navigationIcon = {IconButton(onClick = {}) {
                    Icon(painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        tint = Color.Blue
                        )

                }},
                actions = {
                    IconButton(onClick = {}) {
                        Icon(painter = painterResource(R.drawable.ic_launcher_foreground),
                            contentDescription = "ball",
                            tint = Color.Green
                        )

                    }
                    IconButton(onClick = {}) {
                        Icon(painter = painterResource(R.drawable.ic_launcher_foreground),
                            contentDescription = null,
                            tint = Color.Green
                        )

                    }
                }



            )

        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.Green,
                contentColor = Color.Red
            ) {
                items.forEachIndexed { index, items ->
                    NavigationBarItem(
                        icon = {Icon(painter = painterResource(icon[index]), contentDescription = items, modifier = Modifier.size(24.dp))},
                        selected  = selection == index,
                        onClick = {selection = index
                        when(index){
                            0 -> nacontroller.navigate("Home")
                            1 -> nacontroller.navigate("route")
                            2 -> nacontroller.navigate("notification")
                        }

                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Green,
                            unselectedIconColor =  Color.Red,
                            indicatorColor =  Color.Yellow
                        )


                    )
                }
            }
        }

    )
    {
            innerPadding ->
        NavHost(
            navController = nacontroller,
            startDestination = "Home",
            modifier = Modifier.padding(innerPadding)
            ){
            composable("Home") { HomeScreen(toShowScreen = {text ->
                nacontroller.navigate("showScreen/$text")
            }) }

            composable("route") {ShoppingScreen()  }
            composable("notification") {NotificationScreen()  }
            composable("showScreen/{dataInput}") { backStackEntry ->
                val data = backStackEntry.arguments?.getString("dataInput") ?:"ไม่มีข้อมูล"
                showScreen(data)
            }
    }
    }
}

@Composable
fun HomeScreen(toShowScreen: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {

        TextField(
            state = rememberTextFieldState(),
            label = {Text("please input")}
        )

        Spacer(Modifier.height(16.dp))
        val telephone = rememberTextFieldState()

        OutlinedTextField(
            state = telephone,
            label = {Text("Please phone")},
            leadingIcon = {Icon(painter = painterResource(R.drawable.ic_launcher_foreground), contentDescription = null, modifier = Modifier.size(24.dp))},
            trailingIcon = {
                IconButton(onClick = {}) {
                    Icon(painter = painterResource(R.drawable.ic_launcher_foreground), contentDescription = "Telephone", modifier = Modifier.size(15.dp))
                }
            },
            placeholder = {Text("000-000-0000")},
            inputTransformation = InputTransformation.maxLength(10).then {
                if(!asCharSequence().isDigitsOnly()) {
                    revertAllChanges()
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),

            outputTransformation = OutputTransformation {
                if(length > 3) {
                    insert(3, text = "-")
                }
                if(length > 7) {
                    insert(7,text = "-")
                }
            },
            modifier = Modifier.fillMaxWidth()


        )

        Text("หมายเลขโทรศัพท์ของคุณคือ ${telephone.text.toString()}")
        Button(onClick = {toShowScreen(telephone.text.toString())}) {
            Text("message")
        }
        var checkbox by remember { mutableStateOf(false) }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = checkbox, onCheckedChange = {checkbox  = it}) // it จะเปลี่ยนค่าใหม่
            Text("message remember")
        }
        Text(if (checkbox) "choose data" else "not data")

        val choice =  listOf("year 1","year 2","year 3","year 4")
        var selectionChoice by remember {mutableStateOf(choice[0])}

        Row(verticalAlignment = Alignment.CenterVertically) {
            choice.forEach { option ->
                Row {
                    RadioButton(selected = selectionChoice == option, onClick = {selectionChoice = option})
                }
                Text(option)
            }
        }
        Text("Message is $selectionChoice")
    }
}

@Composable
fun HomeScreen(){
    Column() {Text("หน้าเเรก") }
}

@Composable
fun ShoppingScreen(){
    Column() {Text("หน้าตะกร้าสินค้า") }
}

@Composable
fun NotificationScreen(){
    Column() {Text("หน้าเเจ้งเตือน") }
}
@Composable
fun showScreen(dataReeye:String) {
    Column() {
         Text("เเสดงข้อความ $dataReeye")
    }
}






