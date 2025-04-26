package com.medsync.medsync.vender

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.medsync.medsync.estoque.Produto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class venderViewModel {

    data class ItemCarrinho(
        val produto: Produto,
        val quantidade: Int
    )

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

    // Quantidade
    private val _quantidade = MutableStateFlow(0)
    val quantidade: StateFlow<Int> = _quantidade.asStateFlow()

    // nivel de acesso
    private val _tipoUsuario = MutableStateFlow<String?>(null)
    val tipoUsuario: StateFlow<String?> = _tipoUsuario

    // Filtro de drogaria
    private val _estabelecimentoId = MutableStateFlow("")
    val estabelecimentoId: StateFlow<String> = _estabelecimentoId.asStateFlow()

    // Carrinho
    private val _itensCarrinho = MutableStateFlow<List<ItemCarrinho>>(emptyList())
    val itensCarrinho: StateFlow<List<ItemCarrinho>> = _itensCarrinho

    // Seletor
    private val _produtoSelecionado = MutableStateFlow<Produto?>(null)
    val produtoSelecionado: StateFlow<Produto?> = _produtoSelecionado.asStateFlow()

    // Quantia
    private val _quantidadeSelecionada = MutableStateFlow(0)
    val quantidadeSelecionada: StateFlow<Int> = _quantidadeSelecionada.asStateFlow()

    // verificador do carrinho
    private val _produtoAdicionado = MutableStateFlow(false)
    val produtoAdicionado: StateFlow<Boolean> = _produtoAdicionado

    private val _totalVenda = MutableStateFlow(0.0)
    val totalVenda: StateFlow<Double> = _totalVenda


    init {
        itensCarrinho
            .onEach { lista ->
                val total = lista.sumOf { it.produto.precoVenda.toDouble() * it.quantidade }
                _totalVenda.value = total
            }
            .launchIn(scope = CoroutineScope(Dispatchers.Default))
    }


// funções identificadoras
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

    //
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
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao carregar produtos", e)
            }
    }

    // funções de filtro
    fun atualizarTextoPesquisa(novoTexto: String) {
        _pesquisaTexto.value = novoTexto
        filtrarProdutos()
    }

    private fun filtrarProdutos() {
        val textoPesquisa = _pesquisaTexto.value.trim()
        val estabelecimentoId = _estabelecimentoId.value

        // Filtro base: todos os produtos do estabelecimento atual
        val produtosDoEstabelecimento = _produtoList.value.filter { produto ->
            produto.estabelecimentoId == estabelecimentoId
        }

        if (textoPesquisa.isBlank()) {
            _produtosFiltrados.value = emptyList()
            return
        }

        _produtosFiltrados.value = produtosDoEstabelecimento.filter { produto ->
            val nomeCorresponde = produto.nomeProduto.contains(textoPesquisa, ignoreCase = true)

            nomeCorresponde
        }
    }

    fun selecionarProduto(produto: Produto?, quantidade: Int = 0) {
        _produtoSelecionado.value = produto
        _quantidadeSelecionada.value = quantidade
    }

    // funções de operação
    fun adicionarAoCarrinho(produto: Produto, quantidade: Int) {
        val listaAtualizada = _itensCarrinho.value.toMutableList()

        val existente = listaAtualizada.find { it.produto.id == produto.id }
        if (existente != null) {
            // Se o produto já está no carrinho, atualiza a quantidade
            val novaQuantidade = existente.quantidade + quantidade
            listaAtualizada[listaAtualizada.indexOf(existente)] = ItemCarrinho(produto, novaQuantidade)
        } else {
            listaAtualizada.add(ItemCarrinho(produto, quantidade))
        }

        _itensCarrinho.value = listaAtualizada
    }

    fun marcarProdutoComoAdicionado() {
        _produtoAdicionado.value = true
    }

    fun resetarEstadoProdutoAdicionado() {
        _produtoAdicionado.value = false
    }

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

    // Armazenar nome do cliente
    private val _nomeCliente = MutableStateFlow("")
    val nomeCliente: StateFlow<String> = _nomeCliente

    fun setNomeCliente(nome: String) {
        _nomeCliente.value = nome
    }

    // Armazenar forma de pagamento
    private val _formaPagamento = MutableStateFlow("")
    val formaPagamento: StateFlow<String> = _formaPagamento

    fun setFormaPagamento(forma: String) {
        _formaPagamento.value = forma
    }








    private val _listaProdutos = mutableStateOf<List<Produto>>(emptyList())

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