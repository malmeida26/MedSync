package com.medsync.medsync.cadastro

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class usuarioViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun cadastrarUsuario(
        nome: String,
        email: String,
        senha: String,
        numero: String,
        tipo: String, // "administrador" ou "funcionario"
        nomeEstabelecimento: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        auth.createUserWithEmailAndPassword(email, senha)
            .addOnSuccessListener { result ->
                val userId = result.user?.uid ?: return@addOnSuccessListener

                if (tipo == "administrador") {
                    // Criar novo estabelecimento
                    val novoEstabelecimento = hashMapOf("nome" to nomeEstabelecimento)
                    firestore.collection("estabelecimentos")
                        .add(novoEstabelecimento)
                        .addOnSuccessListener { docRef ->
                            val estabelecimentoId = docRef.id
                            // Criar o usuário administrador
                            val usuario = hashMapOf(
                                "nome" to nome,
                                "email" to email,
                                "numero" to numero,
                                "tipo" to tipo,
                                "nomeEstabelecimento" to nomeEstabelecimento,
                                "estabelecimentoId" to estabelecimentoId
                            )
                            firestore.collection("usuarios")
                                .document(userId)
                                .set(usuario)
                                .addOnSuccessListener { onSuccess() }
                                .addOnFailureListener { e -> onFailure(e) }
                        }
                        .addOnFailureListener { e -> onFailure(e) }

                } else if (tipo == "funcionario") {
                    // Buscar o estabelecimento pelo nome
                    firestore.collection("estabelecimentos")
                        .whereEqualTo("nome", nomeEstabelecimento)
                        .get()
                        .addOnSuccessListener { querySnapshot ->
                            if (!querySnapshot.isEmpty) {
                                val estabelecimentoId = querySnapshot.documents.first().id
                                // Criar o usuário funcionário
                                val usuario = hashMapOf(
                                    "nome" to nome,
                                    "email" to email,
                                    "tipo" to tipo,
                                    "estabelecimentoId" to estabelecimentoId
                                )
                                firestore.collection("usuarios")
                                    .document(userId)
                                    .set(usuario)
                                    .addOnSuccessListener { onSuccess() }
                                    .addOnFailureListener { e -> onFailure(e) }
                            } else {
                                onFailure(Exception("Estabelecimento não encontrado"))
                            }
                        }
                        .addOnFailureListener { e -> onFailure(e) }
                }
            }
            .addOnFailureListener { e -> onFailure(e) }
    }

}