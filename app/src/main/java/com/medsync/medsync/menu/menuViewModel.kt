package com.medsync.medsync.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class menuViewModel: ViewModel() {

    private val _tipoUsuario = MutableStateFlow<String?>(null)
    val tipoUsuario: StateFlow<String?> = _tipoUsuario

    fun buscarTipoUsuario() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    _tipoUsuario.value = document.getString("tipo") // "admin" ou "funcionario"
                }
            }
            .addOnFailureListener {
                Log.e("Usuario", "Erro ao buscar tipo do usuário", it)
            }
    }

}