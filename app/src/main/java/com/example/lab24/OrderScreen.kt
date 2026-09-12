package com.example.lab24

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OrderScreen(onOrderClick:() -> Unit,modifier: Modifier = Modifier) {
    val radioOptions = listOf("S","M","L")
    var note = rememberTextFieldState()
    var qty by remember { mutableStateOf(1) }
    var  selectionOption by remember { mutableStateOf(radioOptions[0]) }

    Column(
        modifier = modifier.fillMaxWidth().padding(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
        Spacer(Modifier.height(15.dp))
        Text("ชานมข้าวห้อม", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Rice Milk Tea")
        Spacer(Modifier.height(15.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text("ขนาด")
            Spacer(Modifier.width(width = 10.dp))
            radioOptions.forEach { option ->
                Row {
                    RadioButton(
                        selected = (selectionOption == option),
                        onClick = {selectionOption = option}
                    )
                    Spacer(Modifier.width(width = 5.dp))
                    Text(text = option)
                    Spacer(Modifier.width(width = 30.dp))
                }
            }
        }

        Spacer(Modifier.height(15.dp))
        Text("รายละเอียดเพิ่มเติม:")
        OutlinedTextField(
            state = note,
            label = {Text("เช่น หวานน้อย,เพิ่มช็อต")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(15.dp))
        Text("จำนวน")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = {if(qty > 1) qty--}) {
                Icon(painter = painterResource(R.drawable.))
            }
            Text(qty.toString(), fontSize = 18.sp)
            IconButton(onClick = {qty++}) {
                Icon(painter = painterResource(R.drawable.))
            }
        }

        Spacer(Modifier.height(15.dp))

        Button(onClick = {
            onOrderClick()
        },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFA823236),
                contentColor = Color.White
            )
        ) {
            Text("ใส่ตะกร้า")
        }

    }
}