package com.example.lab24

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun ProductScreen(viewModel: ProductViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val currentState = state) {
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Error -> Text("เกิดข้อผิดพลาด: ${currentState.message}")
            is UiState.SuccessList -> ProductList(
                products = currentState.products,
                onProductClick = { viewModel.fetchProductById(it) }
            )
            is UiState.SuccessSingle -> {
                BackHandler { viewModel.backToList() }
                ProductDetailView(
                    product = currentState.product,
                    onBack = { viewModel.backToList() }
                )
            }
        }
    }
}

@Composable
fun ProductList(products: List<Product>, onProductClick: (Int) -> Unit = {}) {
    LazyColumn( modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products, key = { it.id }) { product ->
            Card( modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                onClick = { onProductClick(product.id) }
            ) {
                Row( modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(model = product.image, contentDescription = null,
                        modifier = Modifier.size(70.dp), contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = product.title, maxLines = 2)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "$${product.price}")
                    }
                }
            }
        }
    }
}

@Composable
fun ProductDetailView( product: Product, onBack: () -> Unit ) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(onClick = onBack) { Text("<- Back") }
        Box(
            modifier = Modifier.fillMaxWidth().height(260.dp).clip(RoundedCornerShape(8.dp))
                .background(Color.White), contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = product.thumbnail, contentDescription = product.title,
                modifier = Modifier.fillMaxSize().padding(8.dp),
                contentScale = ContentScale.Fit
            )
        }
        Text(text = product.title)
        Text(text = "หมวดหมู่: ${product.category}")
        Text(
            text = "$${product.price}"
        )
    }
}




