@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lab24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.lab24.ui.theme.Lab24Theme
import androidx.navigation.compose.composable
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
    val context  = LocalContext.current
    val viewModel:TodoViewModel =  viewModel(
        factory = TodoViewModelFactory(context)
    )
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Red,
                    titleContentColor = Color.White
                ),
                title = { Text("To Do List") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("noteForm") },
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "Add"
                )
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
            composable("noteForm") {
                NoteScreen(
                    onBack = { navController.popBackStack()},
                    viewModel = viewModel
                )




            }
        }

    }
}
@Composable
fun  HomeScreen(){
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {Text("หน้าเเรก") }
}


@Composable
fun NoteScreen(onBack: () -> Unit, viewModel: TodoViewModel) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top

    ) {
        val title = rememberTextFieldState()
        var titleError by remember { mutableStateOf(false) }
        var description = rememberTextFieldState()
        var date  = rememberTextFieldState()
        var showDatePicker by remember { mutableStateOf(false) }
        val interaction  = remember { MutableInteractionSource() }
        val selectedDatemills by remember { mutableStateOf<Long?> (null) }
        LaunchedEffect(interaction) {
            interaction.interactions.collect { interaction ->
                if(interaction is PressInteraction.Release) {
                    showDatePicker = true
                }
            }
        }

        val dateFormat = remember { SimpleDateFormat("D MMM yyyy", Locale("th")) }

        OutlinedTextField(
            state = title,
            label = {Text("ชื่องาน")},
            isError = titleError,
            supportingText = {
                if (titleError) {
                    Text(
                        text = "กรุณากรอกงาน",
                        color = Color.Red
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            state = description,
            label = {Text("รายละเอียดถ้ามี")},
            lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 2),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            state = date,
            label = {Text("วันที่")},
            readOnly = true,
            interactionSource = interaction,
            trailingIcon = {
                IconButton(onClick = {showDatePicker = true}) {
                    Icon(painter = painterResource(R.drawable.home), contentDescription = "Calendar")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        if(showDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = {showDatePicker =  false},
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            mills -> date.setTextAndPlaceCursorAtEnd(
                                dateFormat.format(Date(mills))
                            )
                        }
                        showDatePicker = false

                    }) { Text("ตกลง")}
                },
                dismissButton = {
                    TextButton(onClick = {showDatePicker = false}) {Text("ยกเลิก") }
                }
            ) { DatePicker(state = datePickerState) }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            FilledTonalButton(onClick = { onBack()}) {
                Spacer(Modifier.width(10.dp))
                Button(onClick = {
                    if(title.text.isBlank()) titleError = true
                    else{
                        titleError = false
                        viewModel.insertTodo(
                            title = title.text.toString(),
                            description = description.text.toString().ifBlank { null },
                            date  = selectedDatemills?: System.currentTimeMillis()
                        )
                        onBack()
                    }
                }) {Text("บันทึก") }
            }
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







