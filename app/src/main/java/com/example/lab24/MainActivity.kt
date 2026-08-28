@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lab24

import android.os.Bundle
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.lab24.ui.theme.Lab24Theme
import androidx.navigation.compose.composable
import androidx.compose.runtime.State
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun MyApp() {
    /*val shareViewModels: ShareViewModels = viewModel()
    val navcontaller = rememberNavController()
    var selectitem by remember { mutableStateOf(0) }
    val items = listOf("home","cart","notifications")
    val icons = listOf(
        R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground
    )



    Scaffold(
        topBar = {
            @kotlin.OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White

                ),
                title = {Text("Myapplication")},
                navigationIcon = {IconButton(onClick = {}) {
                    Icon(painter = painterResource(R.drawable.boy),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(painter = painterResource(R.drawable.ic_launcher_foreground),
                            contentDescription = null,
                            tint = Color.White


                        )
                    }

                    IconButton(onClick = {}) {
                        Icon(painter = painterResource(R.drawable.ic_launcher_foreground),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)




                        )
                    }
                }





            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.Green,
                contentColor = Color.Blue
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {Icon(painter = painterResource(icons[index]),contentDescription = item)},
                        selected = selectitem == index,
                        onClick = {selectitem = index
                            when(index){
                                0 -> navcontaller.navigate("Home")
                                1 -> navcontaller.navigate("route")
                                2 -> navcontaller.navigate("notification")

                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Green,
                            unselectedIconColor = Color.Gray,
                            indicatorColor = Color.Red //สัีขอบ



                        )
                    )
                }
            }
        }
    ) {
            innerPadding ->
        NavHost(
            navController = navcontaller,
            startDestination = "Home",
            modifier = Modifier.padding(innerPadding)
        ){
            composable("Home") {
                HomeScreen(
                    toShowScreen = {
                            text ->
                        navcontaller.navigate("showScreen/$text")
                    },
                    onSignup = { user ->
                        shareViewModels.setUser(user)
                        navcontaller.navigate("profilescreen")
                    }
                )
            }
            composable ("route"){ ShoppingScreen() }
            composable ("notification"){ NotificationScreen() }
            composable ("showScreen/{dataInput}"){ backStackEntry ->
                val data = backStackEntry.arguments?.getString("dataInput")?:"ไม่มีข้อมูล"
                showScreen(data)

            }
            composable ("profilescreen"){ProfileScreen(shareViewModels) }
        }
    }*/


        val navController = rememberNavController()
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Red,
                        titleContentColor = Color.White
                    ),
                    title = {Text("To Do List")}
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {navController.navigate("noteForm")},
                    shape = CircleShape
                ) {
                    Icon(painter = painterResource(R.drawable.ic_launcher_foreground), contentDescription = "Add")
                }
            },
            modifier = Modifier.fillMaxSize()
        ) {innerPaddding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPaddding)
            ) {
                composable("home") { HomeScreen() }
                composable("noteForm") { NoteScreen(
                    onBack = {navController.popBackStack()}
                ) }

            }
        }

}


/*
@Composable
fun HomeScreen(toShowScreen: (String) -> Unit, onSignup: (UserModel) -> Unit){

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("สมัครสมาชิก", modifier = Modifier.padding(15.dp), fontSize = 24.sp)
        Spacer(Modifier.height(16.dp))
        var name = rememberTextFieldState()
        OutlinedTextField(
            state = name,
            label = {Text("ชื่อ-นามสกุล")},
            leadingIcon = {Icon(painter = painterResource(R.drawable.ic_launcher_foreground),contentDescription = null, modifier = Modifier.size(24.dp))},
            trailingIcon = {
                IconButton(onClick = {}) {
                    Icon(painter = painterResource(R.drawable.ic_launcher_foreground),contentDescription = "Telephone", modifier = Modifier.size(15.dp))
                }

            },
            placeholder = {Text( "ชื่อ-นามสกุล")},


            modifier = Modifier.fillMaxWidth()





        )
        Spacer(Modifier.height(16.dp))
        var address = rememberTextFieldState()

        OutlinedTextField(
            state = address,
            label = {Text("ที่อยู่")},
            leadingIcon = {Icon(painter = painterResource(R.drawable.ic_launcher_foreground),contentDescription = null, modifier = Modifier.size(24.dp))},
            trailingIcon = {
                IconButton(onClick = {}) {
                    Icon(painter = painterResource(R.drawable.ic_launcher_foreground),contentDescription = "Telephone", modifier = Modifier.size(15.dp))
                }

            },
            placeholder = {Text( "ที่อยู่")},


            modifier = Modifier.fillMaxWidth()





        )



        val choice = listOf("ชาย","หญิง","อื่นๆ")
        var selectionChoice  by remember { mutableStateOf(choice[0]) }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(5.dp)){
            choice.forEach { option ->
                Row{
                    RadioButton(selected = selectionChoice == option, onClick = {selectionChoice = option})
                }
                Text(option)

            }



        }




        Spacer(Modifier.height(16.dp))
        var email = rememberTextFieldState()

        OutlinedTextField(
            state = email,
            label = {Text("email")},
            leadingIcon = {Icon(painter = painterResource(R.drawable.ic_launcher_foreground),contentDescription = null, modifier = Modifier.size(24.dp))},
            trailingIcon = {
                IconButton(onClick = {}) {
                    Icon(painter = painterResource(R.drawable.ic_launcher_foreground),contentDescription = "Telephone", modifier = Modifier.size(15.dp))
                }

            },
            placeholder = {Text( "email")},


            modifier = Modifier.fillMaxWidth()





        )

        TextField(
            state = rememberTextFieldState(),
            label = {Text("please input")}
        )



        FilledTonalButton(
            onClick = {
                val user = UserModel(
                    name.text.toString(),
                    address.text.toString(),
                    selectionChoice,
                    email.text.toString()
                )

                onSignup(user)
            },
            modifier = Modifier.padding(25.dp)
        ) {
            Text("สมัครสมาชิก")
        }





    }

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
fun showScreen(dataReeye:String){
    Column() {
        Text("เเสดงข้อความ $dataReeye")
    }
}
@Composable
fun ProfileScreen(shareViewModels: ShareViewModels){
    val user by shareViewModels.users
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("ข้อมุลการสมัคร", fontSize = 20.sp)
        Text("ชื่อ: ${user!!.name}")
        Text("address : ${user!!.address}")
        Text("gender: ${user!!.gender}")
        Text("email: ${user!!.email}")
    }
}
data class UserModel(
    val name: String,
    val address: String,
    val  gender : String,
    val email : String
)

class ShareViewModels : ViewModel() {
    private val user = mutableStateOf<UserModel?>(null)
    val users: State<UserModel?> = user

    fun setUser(newUser: UserModel){
        user.value = newUser
    }

}
*/







