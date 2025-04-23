package com.medsync.medsync.estoque

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class estoqueViewModel : ViewModel()  {

    // Lista completa de produtos
    private val _produtoList = MutableStateFlow<List<Produto>>(emptyList())
    val produtoList: StateFlow<List<Produto>> = _produtoList.asStateFlow()

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

    // Filtro de drogaria
    private val _estabelecimentoId = MutableStateFlow("")
    val estabelecimentoId: StateFlow<String> = _estabelecimentoId.asStateFlow()

    // nivel de acesso
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

    fun obterEstabelecimentoDoUsuarioAtual() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            FirebaseFirestore.getInstance().collection("usuarios")
                .document(userId)
                .get()
                .addOnSuccessListener { doc ->
                    val estabelecimentoIdFirestore = doc.getString("estabelecimentoId")
                    if (estabelecimentoIdFirestore != null) {
                        _estabelecimentoId.value = estabelecimentoIdFirestore
                        filtrarProdutos() // Refiltra produtos ao obter o ID
                    }
                }
                .addOnFailureListener {
                    // Aqui você pode logar o erro ou lidar de outra forma
                }
        }
    }

    fun carregarProdutos(estabelecimentoId: String) {
        FirebaseFirestore.getInstance()
            .collection("produtos")
            .whereEqualTo("estabelecimentoId", estabelecimentoId)
            .get()
            .addOnSuccessListener { result ->
                val listaProdutos = result.map { document ->
                    Produto(
                        nomeProduto = document.getString("nomeProduto") ?: "",
                        nomeGenerico = document.getString("nomeGenerico") ?: "",
                        apresentacao = document.getString("apresentacao") ?: "",
                        precoVenda = document.getString("precoVenda") ?: "",
                        qnt = document.getLong("qnt")?.toInt() ?: 0,
                        estabelecimentoId = document.getString("estabelecimentoId") ?: "",
                        categoria = document.getString("categoria") ?: ""
                    )
                }
                _produtoList.value = listaProdutos   // Atualiza a lista completa
                filtrarProdutos()
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao carregar produtos", e)
            }
    }

    // os 2 atualizar fazem a conversão de variaveis
    fun atualizarTextoPesquisa(novoTexto: String) {
        _pesquisaTexto.value = novoTexto
        filtrarProdutos()
    }

    fun atualizarCategoriaFiltro(novaCategoria: String) {
        _categoriaFiltro.value = novaCategoria
        filtrarProdutos()
    }

    private fun filtrarProdutos() {
        val textoPesquisa = _pesquisaTexto.value.trim()
        val categoriaFiltro = _categoriaFiltro.value.trim()
        val estabelecimentoId = _estabelecimentoId.value

        // Filtro base: todos os produtos do estabelecimento atual
        val produtosDoEstabelecimento = _produtoList.value.filter { produto ->
            produto.estabelecimentoId == estabelecimentoId
        }

        // Se ambos os filtros estão vazios, retorna tudo do estabelecimento
        if (textoPesquisa.isBlank() && categoriaFiltro.isBlank()) {
            _produtosFiltrados.value = produtosDoEstabelecimento
            return
        }

        // Aplica os filtros combinados
        _produtosFiltrados.value = produtosDoEstabelecimento.filter { produto ->
            val nomeCorresponde = produto.nomeProduto.contains(textoPesquisa, ignoreCase = true)

            val categoriaProduto = produto.categoria?.trim() ?: ""
            val categoriaCorresponde = categoriaFiltro.isBlank() ||
                    categoriaProduto.equals(categoriaFiltro, ignoreCase = true)

            nomeCorresponde && categoriaCorresponde
        }
    }

    fun excluirProduto(produto: Produto, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("produtos")
            .document(produto.nomeProduto) // O ID é o nomeProduto
            .delete()
            .addOnSuccessListener {
                Log.d("Firestore", "Produto deletado com sucesso")
                onSuccess() // Chama a função de sucesso para atualizar a UI
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao deletar produto", e)
                onFailure(e) // Chama a função de erro para exibir mensagens
            }
    }

    fun incrementarQuantidade(produto: Produto) {
        val novaQuantidade = produto.qnt + 1
        FirebaseFirestore.getInstance().collection("produtos")
            .document(produto.nomeProduto)  // Certifique-se de que cada produto tenha um 'id' único
            .update("qnt", novaQuantidade)
            .addOnSuccessListener {
                // Atualizando o estado local após sucesso no Firestore
                _produtosFiltrados.value = _produtosFiltrados.value.map { item ->
                    if (item.nomeProduto == produto.nomeProduto) {
                        item.copy(qnt = novaQuantidade)  // Atualiza no estado local
                    } else {
                        item
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao atualizar a quantidade", e)
            }
    }

    fun decrementarQuantidade(produto: Produto) {
        if (produto.qnt > 0) { // Impede decremento quando a quantidade for 0 ou menor

            val novaQuantidade = produto.qnt - 1
            // Atualizando no Firestore
            FirebaseFirestore.getInstance().collection("produtos")
                .document(produto.nomeProduto)  // Supondo que cada produto tem um 'id' único
                .update("qnt", novaQuantidade)
                .addOnSuccessListener {
                    // Atualizando o estado local se necessário
                    _produtosFiltrados.value = _produtosFiltrados.value.map {
                        if (it.nomeProduto == produto.nomeProduto) {
                            it.copy(qnt = novaQuantidade) // Atualiza no estado local
                        } else {
                            it
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Erro ao atualizar a quantidade", e)
                }
        }
    }



}// fim da viewmodel



