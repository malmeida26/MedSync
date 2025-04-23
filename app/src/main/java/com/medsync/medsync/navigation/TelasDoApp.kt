package com.medsync.medsync.navigation

sealed class Tela(val rota: String) {
    object TelaLogin : Tela("tela_login")
    object TelaVendas : Tela("tela_vendas")
    object TelaCadastrarUsuario : Tela("tela_cadastrar_usuario")
    object TelaClientePagamento : Tela("tela_cliente_pagamento")
    object TelaEditarProduto : Tela("tela_editar_produto")
    object TelaCadastroProduto : Tela("tela_cadastro_produto")
    object TelaMenuOf : Tela("tela_menu")
    object TelaEstoque : Tela("tela_estoque")
    object TelaDesempenho : Tela("tela_desempenho")
    object TelaWhatsApp : Tela("tela_zap")
    object TelaPerfil : Tela("tela_menu")
    object TelaEsqueciSenha : Tela("esqueciSenha/{email}") {
        fun criarRotaComArgs(email: String) = "esqueciSenha/$email"
    }
    // Adicione outras telas conforme necessário
}
