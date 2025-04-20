package com.medsync.medsync.cadastroProdutos

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class produtosViewModel: ViewModel() {


    fun produtoDB(
        nome: String,
        nomeGenerico: String,
        apresentacao: String,
        dataValidade: String,
        precoCompra: String,
        precoVenda: String,
        categoria: String,
        estabelecimentoId: String,
        qnt: Int
    ) {
        // Obter uma instância do Firestore
        val db = FirebaseFirestore.getInstance()

        // Criar um mapa com os dados do produto
        val produto = hashMapOf(
            "nomeProduto" to nome,
            "nomeGenerico" to nomeGenerico,
            "apresentacao" to apresentacao,
            "dataValidade" to dataValidade,
            "precoCompra" to precoCompra,
            "precoVenda" to precoVenda,
            "qnt" to qnt,
            "categoria" to categoria,
            "estabelecimentoId" to estabelecimentoId,
        )

        // Adicionar um novo documento com um ID gerado automaticamente
        db.collection("produtos")
            .document(nome) // Aqui usamos o nomeProduto como ID do documento
            .set(produto)
            .addOnSuccessListener {
                Log.d("Firestore", "Produto cadastrado com sucesso")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao cadastrar produto", e)
            }

    }// fim da função do BD



    private val _estabelecimentoId = MutableStateFlow<String?>(null)
    val estabelecimentoId: StateFlow<String?> = _estabelecimentoId

    fun carregarEstabelecimentoDoUsuarioLogado() {
        val email = FirebaseAuth.getInstance().currentUser?.email ?: return

        FirebaseFirestore.getInstance().collection("usuarios")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val usuario = querySnapshot.documents.first()
                    val estId = usuario.getString("estabelecimentoId")
                    _estabelecimentoId.value = estId
                }
            }
    }


}