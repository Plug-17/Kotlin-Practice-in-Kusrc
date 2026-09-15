package com.example.lab24

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.remote.creation.dsl.first
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.OnPlacedModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


@Composable
fun HistoryScreen(
    onEditClick: (String) -> Unit,
    modifier:  Modifier = Modifier) {
    val orders = listOf(
       "001" to Triple("M", 2, "หวาน 25 %"),
        "002" to Triple("S", 3, "เพิ่มไข่มุก"),
        "003" to Triple("L", 2, "-")
    )

    var itemToDelete by remember {
        mutableStateOf<Pair<String, Triple<String, Int, String?>>?>(null)
    }

    var openedItemId by remember { mutableStateOf<String?>(null) }
    Column(
        modifier = modifier.fillMaxWidth().padding(16.dp)
    ) {
        Text("ประวัติการสั่งซื้อ", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))

        if (orders.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("ยังไม่มีการสั่งซื้อ", color = Color.Green)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(orders, key = { it.first }) { order ->
                    val dismissState =
                        rememberSwipeToDismissBoxState(confirmValueChange = { value ->
                            when (value) {
                                SwipeToDismissBoxValue.EndToStart -> {
                                    openedItemId = order.first
                                }

                                SwipeToDismissBoxValue.Settled -> {
                                    if (openedItemId == order.first) openedItemId = null
                                }

                                else -> {}
                            }
                            true
                        })

                    LaunchedEffect(openedItemId) {
                        if (openedItemId != order.first && dismissState.currentValue != SwipeToDismissBoxValue.Settled) {
                            dismissState.snapTo(SwipeToDismissBoxValue.Settled)
                        }
                    }

                    // swipe ปุ่มเเก้ไข/ลบ
                    val scope = rememberCoroutineScope()
                    SwipeToDismissBox(
                        state = dismissState,
                        enableDismissFromStartToEnd = true,
                        enableDismissFromEndToStart = true,
                        backgroundContent = {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(0xFFF5F5F5))
                                    .padding(end = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(
                                    onClick = {
                                        scope.launch { dismissState.snapTo(SwipeToDismissBoxValue.Settled) }
                                        onEditClick(order.first)
                                    },
                                    modifier = Modifier
                                        .background(
                                            Color(0xFFFFC107),
                                            RoundedCornerShape(8.dp)
                                        )
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.),
                                        contentDescription = "Edit",
                                        tint = Color.White
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                IconButton(
                                    onClick = {
                                        scope.launch { dismissState.snapTo(SwipeToDismissBoxValue.Settled) }
                                        itemToDelete = order
                                    },
                                    modifier = Modifier
                                        .background(
                                            Color(0xFFF44336),
                                            RoundedCornerShape(8.dp)
                                        )
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.),
                                        contentDescription = "Delete",
                                        tint = Color.White
                                    )
                                }
                            }
                        },
                        content = { OrderCard(order = order) }


                    )
                }
            }

            itemToDelete?.let { order ->
                AlertDialog(
                    onDismissRequest = {itemToDelete = null},
                    title =  {Text("ยืนยันการลบ")},
                    text = {Text("เเน่ใจว่าต้องการลบรายการนี้")},
                    confirmButton = {
                        TextButton(
                            onClick = {itemToDelete = null}
                        ) { Text("ลบ")}
                    },
                    dismissButton = {
                        TextButton(onClick = {itemToDelete = null}) { Text("ยกเลิก")}
                    }
                )
            }
        }
    }
}

@Composable
fun OrderCard(order: Pair<String, Triple<String, Int, String?>>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("ขนาด: ${order.second.first}", fontWeight = FontWeight.Bold)
                Text("จำนวน: ${order.second.second}")
                Text("หมายเหตุ: ${order.second.third ?: "-"}", color = Color.Gray)
            }
        }
    }
}
