package com.medsync.medsync.vender

import com.medsync.medsync.estoque.Produto

data class ItemCarrinho(
    val produto: Produto,
    var quantidade: Int
)