package com.example.lab24

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun EditOrderScreen(orderID: String,onBack:()-> Unit,modifier: Modifier = Modifier){
    val radioOption = listOf("S","M","L")
    var selectedOption by remember { mutableStateOf("M") }
    var qty by remember { mutableStateOf(1) }
    var note = rememberTextFieldState()

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp)

    ) {
        Text("เเก้ไขออเดอร์", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Text("ขนาด:")
        Row(modifier = Modifier.fillMaxWidth()) {
            radioOption.forEach { option ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedOption == option,
                        onClick = {selectedOption = option}
                    )
                    Text(option)
                    Spacer(Modifier.width(20.dp))
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Text("จำนวน:")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {if(qty > 1) qty--}) {
                Icon(painter = painterResource(R.drawable.))
            }
            Text(qty.toString(), fontSize = 18.sp)
            IconButton(onClick = {qty++}) {
                Icon(painter = painterResource(R.drawable.))
            }
        }

        Spacer(Modifier.height(16.dp))
        Text("รายละเอียดเพิมเติม:")
        OutlinedTextField(
            state = note,
            label = {Text("เช่นหวานน้อย")},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(
                onClick = {onBack()},
                modifier = Modifier.weight(1f)
            ) { Text("ยกเลิก")}
            Button(
                onClick = {
                    onBack()
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6D9E51)
                )
                ) {Text("แก้ไข", color = Color.White)
                }
        }


    }
}