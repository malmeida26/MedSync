package com.medsync.medsync.estoque

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.medsync.medsync.menu.TelaMenu
import com.medsync.medsync.ui.theme.ui.theme.Blue10
import com.medsync.medsync.ui.theme.ui.theme.Blue20
import com.medsync.medsync.ui.theme.ui.theme.MedSyncTheme
import com.medsync.medsync.ui.theme.ui.theme.interBold
import com.medsync.medsync.ui.theme.ui.theme.interMedium
import com.medsync.medsync.ui.theme.ui.theme.quickSandBold
import com.medsync.medsync.ui.theme.ui.theme.robotoCondensed
import kotlinx.coroutines.delay

@ExperimentalMaterial3Api
class estoque : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedSyncTheme {

                // hover do botão
                val interactionSource = remember { MutableInteractionSource() }
                val interactionSource2 = remember { MutableInteractionSource() }

                val isPressed by interactionSource.collectIsPressedAsState() //começa com false
                val isPressed2 by interactionSource2.collectIsPressedAsState() //começa com false

                val iconColor = if(isPressed){
                    Color.White
                }else{
                    Blue10
                }
                val iconBackgroundColor = if(isPressed){
                    Blue10
                }else{
                    Blue20
                }

                val iconColor2 = if(isPressed2){
                    Color.White
                }else{
                    Blue10
                }
                val iconBackgroundColor2 = if(isPressed2){
                    Blue10
                }else{
                    Blue20
                }

                val context = LocalContext.current
                val intentVoltar = Intent(context, TelaMenu::class.java)

                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(title = {
                        Text("")
                    },
                        actions = {
                            Row(
                                Modifier
                                    .padding(8.dp)
                                    .background(Color.White)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Absolute.SpaceBetween
                            ) {

                                Box(
                                    Modifier
                                        .wrapContentSize()
                                        .background(
                                            iconBackgroundColor,
                                            shape = RoundedCornerShape(15.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    IconButton(onClick = { context.startActivity(intentVoltar) }, interactionSource = interactionSource) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBackIosNew,
                                            contentDescription = null,
                                            tint = iconColor,
                                            modifier = Modifier.size(44.dp)
                                        )
                                    }
                                }

                                Box(Modifier.wrapContentSize()) {
                                    Text(
                                        text = "MedSync",
                                        fontFamily = quickSandBold,
                                        fontSize = 40.sp,
                                        color = Blue10
                                    )
                                }

                                Box(
                                    Modifier
                                        .wrapContentSize(),
                                    //.background(Blue20, shape = RoundedCornerShape(15.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    IconButton(onClick = {}) {
                                        Icon(
                                            imageVector = Icons.Filled.Home,
                                            contentDescription = null,
                                            tint = Color.White,
                                        )
                                    }
                                }
                            }
                        }, colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.White,
                            navigationIconContentColor = Color.Black,
                            titleContentColor = Color.Black,
                            actionIconContentColor = Color.Black,

                            ),
                        modifier = Modifier.drawBehind {
                            val borderSize = 3.dp.toPx()
                            drawLine(
                                color = Blue10,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = borderSize
                            )
                        }
                    )

                }) { innerPadding ->
                    estoque(Modifier.padding(innerPadding), viewModel = estoqueViewModel())
                }
            }// fim do theme
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun estoque(modifier: Modifier = Modifier, viewModel: estoqueViewModel) {

    //permissão
    val tipoUsuario by viewModel.tipoUsuario.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.buscarTipoUsuario()
    }



    //iniciar funçoes

    LaunchedEffect(Unit) {
        viewModel.obterEstabelecimentoDoUsuarioAtual() // Chama diretamente sem necessidade de callback
    }

    val estabelecimentoId by viewModel.estabelecimentoId.collectAsState()

    // Assim que o estabelecimentoId for atualizado, você pode carregar os produtos
    if (estabelecimentoId.isNotEmpty()) {
        // Carregar os produtos ou fazer outra ação
       // viewModel.carregarProdutosDoEstabelecimento(estabelecimentoId)
        viewModel.carregarProdutos(estabelecimentoId)
    } else {
        Text("Estabelecimento nao encontrado")
    }



   


    // gerenciemnto de pesquisa
    val pesquisaTexto by viewModel.pesquisaTexto.collectAsState()
    val produtosFiltrados by viewModel.produtosFiltrados.collectAsState()
    val categoriaFiltro by viewModel.categoriaFiltro.collectAsState()

    var produtoSelecionado by remember { mutableStateOf<Produto?>(null) }


    //    categorias
    var categoria by remember { mutableStateOf(categoriaFiltro) }
    var isExpandend by remember { mutableStateOf(false) }
    val list = listOf(
        "limpar filtro",
        "analgésicos e antitérmicos",
        "antiflamatórios e antibióticos",
        "antifúngicos e antivirais",
        "anti-histamínicos e antialérgicos",
        "suplementos e vitaminas",
        "antiácidos e laxantes",
        "contraceptivos"
    )

    // hover do botão
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val buttonColor1 = if(isPressed){
        Blue20
    }else{
        Blue10
    }

    val textButtonColor = if(isPressed){
        Blue10
    }else{
        Color.White
    }

    // cores e fontes
    val backCard = Blue20
    val textColor = Blue10
    val textColor2 = Blue20
    val buttonColor = Blue10
    val textFont = interBold
    val textFont2 = interMedium

    // toasts
    var showToastSucesso by remember { mutableStateOf(false) }
    var showToastErro by remember { mutableStateOf(false) }
    var showToastSemAcesso by remember { mutableStateOf(false) }

    var contexto = LocalContext.current
    val intentEditar = Intent(contexto, editar::class.java)

    // background
    Column(modifier.fillMaxSize().background(Color.White))
    {
        //icone / cabeçalho
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                Modifier
                    .wrapContentSize()
                    .background(Color.White, shape = RoundedCornerShape(15.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Archive,
                    contentDescription = null,
                    tint = Blue10,
                    modifier = Modifier.size(60.dp)
                )
            }
        }// fim do cabelaçlho

            //pesquisa -> por algum motivo atua como background
            Column(modifier = Modifier.background(Color.White).weight(4f))
            {
                /*
                LaunchedEffect(Unit) {
                    viewModel.buscarTodosProdutos()
                }

                 */

                // row de opções
                Row(modifier.fillMaxWidth().background(Color.White).padding(10.dp))
                {
                    OutlinedTextField(
                        value = pesquisaTexto,
                        onValueChange = {  novoTexto ->
                            viewModel.atualizarTextoPesquisa(novoTexto) },
                        maxLines = 1,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                        ),
                        textStyle = TextStyle(color = Color.Black),
                        placeholder = { Text("Buscar") },
                        modifier = Modifier
                            .background(Blue20, shape = RoundedCornerShape(10.dp))
                            .weight(3f),
                        shape = RoundedCornerShape(10.dp),
                        leadingIcon = {
                            Icon(imageVector = Icons.Rounded.Search, contentDescription = null, tint = Blue10)
                        }
                    )//Fim textField1 (Pesquisa)

                    Spacer(modifier.width(5.dp))

                    // menu dropdown
                    ExposedDropdownMenuBox(
                        expanded = isExpandend,
                        onExpandedChange = { isExpandend = !isExpandend },

                        ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .menuAnchor()
                                .background(Blue20, shape = RoundedCornerShape(10.dp)),
                            value = categoria,
                            onValueChange = { },
                            readOnly = true,
                            textStyle = TextStyle(color = Blue10),
                            placeholder = { Text("Filtrar", fontFamily = robotoCondensed) },
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                errorContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent
                            ),
                            leadingIcon = {
                                Icon(imageVector = Icons.Rounded.FilterAlt, contentDescription = null, tint = Blue10)
                            },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandend) },
                        )

                        ExposedDropdownMenu(
                            expanded = isExpandend,
                            onDismissRequest = { isExpandend = false },
                            modifier = Modifier
                                .background(Blue20, shape = RoundedCornerShape(10.dp))
                        ) {
                            list.forEachIndexed { index, text ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = text,
                                            fontFamily = interBold,
                                            color = Blue10
                                        )
                                    },
                                    onClick = {
                                        isExpandend = false
                                        if (index == 0) {
                                            categoria = ""
                                            viewModel.atualizarCategoriaFiltro("") // limpa o filtro
                                        } else {
                                            categoria = list[index]
                                            viewModel.atualizarCategoriaFiltro(categoria)
                                        }
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }
                    }// fim do dropdown

                }// fim do row


                Column(
                    modifier = Modifier.weight(3f),
                )
                {
                        LazyColumn(contentPadding = PaddingValues(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            items(produtosFiltrados) { produto ->
                                Card(
                                    shape = RoundedCornerShape(10.dp),
                                    colors = CardDefaults.cardColors(containerColor = backCard),
                                    modifier = Modifier.fillMaxWidth()
                                        .drawBehind {
                                        val borderSize = 3.dp.toPx()
                                        // Borda superior
                                        drawLine(
                                            color = Blue10, // Cor da borda
                                            start = Offset(0f, 0f), // Início no topo esquerdo
                                            end = Offset(size.width, 0f), // Fim no topo direito
                                            strokeWidth = borderSize // Espessura da borda
                                        )
                                        // Borda inferior
                                        drawLine(
                                            color = Blue10, // Cor da borda
                                            start = Offset(0f, size.height), // Início na parte inferior esquerda
                                            end = Offset(size.width, size.height), // Fim na parte inferior direita
                                            strokeWidth = borderSize // Espessura da borda
                                        )
                                    }
                                ){
                                    Column() {
                                        Row(
                                            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 5.dp, top = 10.dp, bottom = 5.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ){
                                            Row(){
                                                Box(modifier = Modifier.width(210.dp)){Text(text = "Nome Comercial: ", color = textColor, fontFamily = textFont)}
                                                Text(text = produto.nomeProduto, color = textColor, fontFamily = textFont2)
                                            }// box dos nomes
                                            Row(){
                                                Text(text = "Preço: ", color = textColor, fontFamily = textFont)
                                                Text(text = "R$${produto.precoVenda}", color = textColor, fontFamily = textFont2)
                                            }// fim box preço
                                        }// fim do box nome comercial e preço

                                        Spacer(modifier = Modifier.width(5.dp))

                                        Row(modifier = Modifier.padding(start = 10.dp)){
                                            Box(modifier = Modifier.width(210.dp)){Text(text = "Nome Genérico: ", color = textColor, fontFamily = textFont)}
                                            Text(text = produto.nomeGenerico, color = textColor, fontFamily = textFont2)
                                        }// box dos generico

                                        Spacer(modifier = Modifier.width(5.dp))

                                        Row(modifier = Modifier.padding(start = 10.dp)){
                                            Box(modifier = Modifier.width(210.dp)){Text(text = "Apresentação: ", color = textColor, fontFamily = textFont)}
                                            Text(text = produto.apresentacao, color = textColor, fontFamily = textFont2)
                                        }// box dos apresentacao

                                        Spacer(modifier = Modifier.width(5.dp))

                                        Row(
                                            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 5.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ){
                                            Row(verticalAlignment = Alignment.CenterVertically){
                                                Box(modifier = Modifier.width(210.dp)){Text(text = "Quantidade", color = textColor, fontFamily = textFont)}
                                                Spacer(modifier = Modifier.width(5.dp))
                                                Box(
                                                    modifier = Modifier
                                                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                                                        .wrapContentSize()
                                                ) {
                                                    OutlinedTextField(
                                                        value = produto.qnt.toString(),
                                                        onValueChange = { },
                                                        readOnly = true,
                                                        textStyle = TextStyle(color = Color.White),
                                                        placeholder = { Text(text = produto.qnt.toString(), fontFamily = textFont) },
                                                        shape = RoundedCornerShape(10.dp),
                                                        colors = TextFieldDefaults.colors(
                                                            focusedContainerColor = Color.Transparent,
                                                            unfocusedContainerColor = Color.Transparent,
                                                            disabledContainerColor = Color.Transparent,
                                                            errorContainerColor = Color.Transparent,
                                                            focusedIndicatorColor = Color.Transparent,
                                                            unfocusedIndicatorColor = Color.Transparent,
                                                            disabledIndicatorColor = Color.Transparent,
                                                            errorIndicatorColor = Color.Transparent,
                                                            focusedTextColor = Color.White,
                                                            unfocusedTextColor = Color.White,
                                                            disabledTextColor = Color.White,
                                                            errorTextColor = Color.White,
                                                        ),
                                                        leadingIcon = {
                                                            IconButton(onClick = {
                                                                viewModel.decrementarQuantidade(produto)
                                                            }) {
                                                                Icon(
                                                                    imageVector = Icons.Filled.Remove,
                                                                    contentDescription = "Subtrair",
                                                                    tint = Color.White,
                                                                )
                                                            }
                                                        },
                                                        trailingIcon = {
                                                            IconButton(onClick = {viewModel.incrementarQuantidade(produto)}) {
                                                                Icon(
                                                                    imageVector = Icons.Filled.Add,
                                                                    contentDescription = "Somar",
                                                                    tint = Color.White,
                                                                )
                                                            }
                                                        },
                                                        modifier = Modifier
                                                            .background(buttonColor, shape = RoundedCornerShape(10.dp))
                                                            .wrapContentHeight()
                                                            .width(150.dp)
                                                            .padding(0.dp)
                                                    )
                                                }// fim da opção que aumenta e diminui a quantidade
                                            }// box da quantidade
                                            Row(){
                                                var isDisplayDialog by remember { mutableStateOf(false) }
                                                Button(onClick = { isDisplayDialog = true }, enabled = tipoUsuario != "funcionario", colors = ButtonDefaults.buttonColors(containerColor = buttonColor, contentColor = Color.White)) {
                                                    Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                                                        Text(text = "Editar")
                                                        Spacer(modifier = Modifier.width(2.dp))
                                                        Icon(imageVector = Icons.Filled.Edit, contentDescription = null)
                                                    }
                                                        if(isDisplayDialog){
                                                            Dialog(onDismissRequest = { isDisplayDialog = false }) {
                                                                Column(
                                                                    modifier = Modifier
                                                                        .clip(RoundedCornerShape(27.dp))
                                                                        .width(400.dp)
                                                                        .height(200.dp)
                                                                        .background(color = Color.White)
                                                                ){
                                                                    Row(
                                                                        modifier = Modifier
                                                                            .fillMaxWidth()
                                                                            .wrapContentHeight(),
                                                                        horizontalArrangement = Arrangement.End,
                                                                    ){
                                                                        IconButton(onClick = {isDisplayDialog = false}) {
                                                                            Icon(
                                                                                modifier = Modifier.size(20.dp),
                                                                                imageVector = Icons.Rounded.Close,
                                                                                contentDescription = null,
                                                                                tint = Blue10
                                                                            )
                                                                        }// fim do icon button
                                                                    }// botão de fechar
                                                                    Row(
                                                                        modifier = Modifier
                                                                            .fillMaxWidth(),
                                                                    ){
                                                                        Row(
                                                                            modifier = Modifier
                                                                                .padding(start = 20.dp,end = 10.dp)
                                                                        ){
                                                                            Text(text = "Produto:", fontFamily = textFont, color = Blue10)
                                                                        }
                                                                        Spacer(modifier = Modifier.width(15.dp))
                                                                        Row(){
                                                                            Text(text = produto.nomeProduto, fontFamily = textFont2, color = Blue10)
                                                                        }
                                                                    }// infos do produtos

                                                                    Spacer(modifier = Modifier.height(30.dp))
                                                                    Row(
                                                                        modifier = Modifier
                                                                            .fillMaxWidth()
                                                                            .padding(start = 20.dp),
                                                                        verticalAlignment = Alignment.Bottom,
                                                                        horizontalArrangement = Arrangement.Center
                                                                    ){
                                                                        Button(
                                                                            onClick={produtoSelecionado = produto},
                                                                            interactionSource = interactionSource,
                                                                            colors = ButtonDefaults.buttonColors(buttonColor1, contentColor = textButtonColor),
                                                                            ){
                                                                            Row(verticalAlignment = Alignment.CenterVertically){
                                                                                Text(text = "Editar", fontFamily = textFont2)
                                                                                Icon(
                                                                                    imageVector = Icons.Rounded.Edit,
                                                                                    contentDescription = null
                                                                                )
                                                                            }
                                                                        }
                                                                        Spacer(modifier = Modifier.width(30.dp))
                                                                        Button(
                                                                            onClick={viewModel.excluirProduto(produto,
                                                                                onSuccess = {
                                                                                        isDisplayDialog = false
                                                                                        showToastSucesso = true
                                                                                },
                                                                                onFailure = { error ->
                                                                                    showToastErro = true
                                                                                }
                                                                            )
                                                                                    },
                                                                            colors = ButtonDefaults.buttonColors(Color.Red, contentColor = Color.White),
                                                                        ){
                                                                            Row(verticalAlignment = Alignment.CenterVertically){
                                                                                Text(text = "Excluir", fontFamily = textFont2)
                                                                                Icon(
                                                                                    imageVector = Icons.Rounded.Delete,
                                                                                    contentDescription = null
                                                                                )
                                                                            }
                                                                        }

                                                                    }// botões de operação

                                                                }// fim do back do dialog
                                                            }// fim do dialog
                                                        }// fim do if

                                                }// fim do button



                                            }// fim box editar
                                        }// fim do box nome comercial e preço
                                    }// estrutura o card
                                }// fim do card

                            }// fim do items



                        }// fim lazColumn






                }// fim do column de resultados das pesquisa
            }// fim do column de pesquisa

    }// fim do background



    com.medsync.medsync.estoque.CustomToast(
        show = showToastSucesso,
        message = "Produto Excluído",
        texto = Color.White,
        icone = Color.White,
        backgroundColor = Color.Green,
        iconVec = Icons.Filled.Check
    )
    LaunchedEffect(key1 = showToastSucesso) {
        if (showToastSucesso) {
            delay(2000)
            showToastSucesso = false
        }
    }

    com.medsync.medsync.estoque.CustomToast(
        show = showToastErro,
        message = "Falha ao Excluir",
        texto = Color.Black,
        icone = Color.Black,
        backgroundColor = Color.Red,
        iconVec = Icons.Filled.Error
    )
    LaunchedEffect(key1 = showToastErro) {
        if (showToastErro) {
            delay(2000)
            showToastErro = false
        }
    }

/*
    if (produtoSelecionado != null) {
        Editar(
            produto = produtoSelecionado!!,
            estoqueViewModel = estoqueViewModel,
            onVoltar = { produtoSelecionado = null } // Fecha a tela ao concluir
        )
    }

 */

}// fim da função

@Composable
fun CustomToast(show: Boolean, message: String, texto: Color, icone: Color, backgroundColor: Color, iconVec: ImageVector) {
    AnimatedVisibility(
        visible = show,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .background(backgroundColor, shape = RoundedCornerShape(25.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = message, color = texto, fontFamily = quickSandBold)
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = iconVec,
                    contentDescription = "Success",
                    tint = icone,
                    modifier = Modifier.size(24.dp)
                )

            }
        }
    }
}



/*TODO terminar a busca */
@Composable
fun ProdutoItem(produto: Produto) {
    if (produto != null) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Nome: ${produto?.nomeProduto}", fontWeight = FontWeight.Bold)
                Text(text = "Genérico: ${produto?.nomeGenerico}")
                Text(text = "Apresentação: ${produto?.apresentacao}")
                Text(text = "Preço de Venda: R$${produto?.precoVenda}")
                Text(text = "Quantidade: ${produto?.qnt}")
            }
        }
    } else {
        Text(text = "Nenhum produto encontrado", color = Color.Gray)
    }
}




