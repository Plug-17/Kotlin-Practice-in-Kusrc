package com.example.lab24
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Double,
    val thumbnail: String
)

data class ProductsResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int,
)

interface ApiService {
    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): Product
    @GET("products")
    suspend fun getProducts(): ProductsResponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://dummyjson.com/"
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

sealed interface UiState {
    data object Loading : UiState
    data class SuccessList(val products: List<Product>) : UiState
    data class SuccessSingle(val product: Product) : UiState
    data class Error(val message: String) : UiState
}

class ProductViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    private var cachedProducts: List<Product> = emptyList()
    init { fetchProducts() }
    fun fetchProducts() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val result = RetrofitInstance.api.getProducts().products?:emptyList()
                cachedProducts = result
                _uiState.value = UiState.SuccessList(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown Error")
            }
        }
    }

    fun fetchProductById(id: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val result = RetrofitInstance.api.getProductById(id)
                _uiState.value = UiState.SuccessSingle(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown Error")
            }
        }
    }
    fun backToList() {
        if (cachedProducts.isNotEmpty()) {
            _uiState.value = UiState.SuccessList(cachedProducts)
        } else { fetchProducts() }
    }
}







