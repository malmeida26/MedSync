package com.medsync.medsync

import com.medsync.medsync.vender.ItemCarrinho

object VendaData {
    var nomeCliente: String = ""
    var formaPagamento: String = ""
    val itensCarrinho = mutableListOf<ItemCarrinho>() // já deve existir essa classe

    fun calcularTotal(): Double {
        return itensCarrinho.sumOf { it.produto.precoVenda.toDouble() * it.quantidade }
    }

    fun limpar() {
        nomeCliente = ""
        formaPagamento = ""
        itensCarrinho.clear()
    }
}