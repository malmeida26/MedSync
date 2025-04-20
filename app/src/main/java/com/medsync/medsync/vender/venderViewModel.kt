package com.medsync.medsync.vender

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.firestore.FirebaseFirestore
import com.medsync.medsync.estoque.Produto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class venderViewModel {

    // Lista completa de produtos
    private val _produtoList = MutableStateFlow<List<Produto>>(emptyList())

    // Inicializa o banco
    private val db = FirebaseFirestore.getInstance()

    // Estado do texto da pesquisa
    private val _pesquisaTexto = MutableStateFlow("")
    val pesquisaTexto: StateFlow<String> = _pesquisaTexto.asStateFlow()

    // Lista filtrada para exibição
    private val _produtosFiltrados = MutableStateFlow<List<Produto>>(emptyList())
    val produtosFiltrados: StateFlow<List<Produto>> = _produtosFiltrados.asStateFlow()

    // Filtro de categoria
    private val _categoriaFiltro = MutableStateFlow("")
    val categoriaFiltro: StateFlow<String> = _categoriaFiltro.asStateFlow()

    // Quantidade
    private val _quantidade = MutableStateFlow(0)
    val quantidade: StateFlow<Int> = _quantidade.asStateFlow()

    fun atualizarQuantidade(produto: Produto, subtrQuantidade: Int){
        val novaQuantidade =  produto.qnt - subtrQuantidade
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
        getProdutos()
    }

    fun getProdutos() {
        db.collection("produtos")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (value != null) {
                    val lista = value.toObjects(Produto::class.java)
                    _produtoList.value = lista
                    //_produtosFiltrados.value = lista // Inicialmente exibe todos
                }
            }
    }

    fun atualizarPesquisa(novoTexto: String) {
        _pesquisaTexto.value = novoTexto

        _produtosFiltrados.value = if (novoTexto.isBlank()) {
            emptyList()
        } else {
            _produtoList.value.filter { produto ->
                produto.nomeProduto.contains(novoTexto, ignoreCase = true)
            }
        }
    }




    private val _listaProdutos = mutableStateOf<List<Produto>>(emptyList())

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
}// fim da função