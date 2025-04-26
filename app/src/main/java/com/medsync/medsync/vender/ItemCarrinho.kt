package com.medsync.medsync.vender

import com.medsync.medsync.estoque.Produto

data class VendaInfo(
    val cliente: String = "",
    val formaPagamento: String = "",
    val itens: List<ItemCarrinho> = emptyList()
)

data class ItemCarrinho(
    val produto: Produto,
    var quantidade: Int
)