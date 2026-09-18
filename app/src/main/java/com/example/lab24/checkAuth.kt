package com.example.lab24

import android.os.Message
import androidx.compose.ui.layout.FirstBaseline
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel(){
    private  val auth:FirebaseAuth = FirstBase.auth

    val isLoggedln: Boolean
        get() = auth.currentUser != null
    val currentUser:FirebaseUser?
        get() = auth.currentUser

    private val _authState = MutableStateFlow<AuthState>(AuthState.ldle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    sealed class AuthState{
        object ldle: AuthState()
        object Loading : AuthState()
        object Success : AuthState()
        object ResetPasswordSent: AuthState()
        data class Error(val message: String): AuthState()
    }

    // ลงทะเบียนใช้งาน
    fun register(email:String,password: Int) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                auth.createUserWithEmailAndPassword(email,password).await()
                _authState.value = AuthState.Success
            } catch (e: Exception){
                _authState.value = AuthState.Error(e.message?:"เกิดข้อผิดพลาด")
            }
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _authState.value  = AuthState.Loading

            try {
                auth.sendPasswordResentEmail(email).await()
                _authState.value = AuthState.ResetPasswordSent
            }catch (e:FirebaseAuthException){
              val message = when(e.errorCode){
                  "Error_Not_found" -> "ไม่พบบัญชีผู้ใช้"
                  "Error_Email" -> "email ไม่ถูกต้อง"
                  else -> "เกิดข้อผิดพลาด ลองใหม่"
              }
            }
            _authState.value = AuthState.Error(message)
        }
    }
}