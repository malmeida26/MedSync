package com.medsync.medsync.estoque

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class estoqueViewModel : ViewModel()  {

    private val _produtoList = MutableStateFlow<List<Produto>>(emptyList())
    var produtoList = _produtoList.asStateFlow()
    private val db = FirebaseFirestore.getInstance()

    var pesquisaTexto = mutableStateOf("")  // Guarda o ID digitado pelo usuário
    private val _produto = mutableStateOf<Produto?>(null)
    val produto: State<Produto?> = _produto


    init {
        getProdutos()
    }

    fun getProdutos(){
        db.collection("produtos")
            .addSnapshotListener{ value, error ->
                if(error != null){
                    return@addSnapshotListener
                }

                if(value != null){
                    _produtoList.value = value.toObjects()
                }

            }

    }

    fun buscarProdutoPorId(id: String) {
        if (id.isBlank()) {
            _produto.value = null
            return
        }

        db.collection("produtos").document(id).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    _produto.value = document.toObject(Produto::class.java)
                } else {
                    _produto.value = null
                    Log.e("Firestore", "Produto não encontrado")
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao buscar produto", e)
            }
    }

    private val _listaProdutos = mutableStateOf<List<Produto>>(emptyList())
    val listaProdutos: State<List<Produto>> = _listaProdutos

    fun buscarTodosProdutos() {
        FirebaseFirestore.getInstance().collection("produtos").get()
            .addOnSuccessListener { result ->
                val lista = mutableListOf<Produto>()
                for (document in result) {
                    document.toObject(Produto::class.java)?.let { produto ->
                        lista.add(produto)
                    }
                }
                _listaProdutos.value = lista // Atualiza a lista de produtos
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao buscar lista de produtos", e)
            }
    }// fim de buscar produtos

    fun incrementarQuantidade(produto: Produto) {
        val novaQuantidade = produto.qnt + 1
        FirebaseFirestore.getInstance().collection("produtos")
            .document(produto.nomeProduto) // Usando nomeProduto como ID
            .update("qnt", novaQuantidade)
            .addOnSuccessListener {
                Log.d("Firestore", "Quantidade incrementada com sucesso")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao incrementar quantidade", e)
            }
    }

    fun decrementarQuantidade(produto: Produto) {
        val novaQuantidade = if (produto.qnt > 0) produto.qnt - 1 else 0
        FirebaseFirestore.getInstance().collection("produtos")
            .document(produto.nomeProduto) // Usando nomeProduto como ID
            .update("qnt", novaQuantidade)
            .addOnSuccessListener {
                Log.d("Firestore", "Quantidade decrementada com sucesso")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao decrementar quantidade", e)
            }
    }

    init {
        escutarProdutosTempoReal() // Iniciar o listener quando a ViewModel for criada
    }

    private fun escutarProdutosTempoReal() {
        db.collection("produtos")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.e("Firestore", "Erro ao escutar o Firestore", e)
                    return@addSnapshotListener
                }
                val listaAtualizada = mutableListOf<Produto>()
                snapshots?.forEach { document ->
                    document.toObject(Produto::class.java).let { produto ->
                        // Adiciona o ID do documento para poder atualizar depois
                        listaAtualizada.add(produto)
                    }
                }

                _listaProdutos.value = listaAtualizada
            }
    }

}// fim da viewmodel



