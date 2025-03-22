package com.medsync.medsync.estoque

data class Produto(
    val id: String = "",
    val nomeProduto: String = "",
    val nomeGenerico: String = "",
    val apresentacao: String = "",
    val qnt: Int = 0,
    val precoVenda: String = "",
    val categoria: String = "",
    val precoCompra: String = "",
    val dataValidade: String = "",
)



