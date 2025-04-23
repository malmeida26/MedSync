package com.medsync.medsync.vender

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.medsync.medsync.estoque.Produto
import com.medsync.medsync.menu.TelaMenu
import com.medsync.medsync.ui.theme.ui.theme.Blue10
import com.medsync.medsync.ui.theme.ui.theme.Blue20
import com.medsync.medsync.ui.theme.ui.theme.MedSyncTheme
import com.medsync.medsync.ui.theme.ui.theme.interBold
import com.medsync.medsync.ui.theme.ui.theme.interMedium
import com.medsync.medsync.ui.theme.ui.theme.quickSandBold
import com.medsync.medsync.ui.theme.ui.theme.robotoCondensed

@ExperimentalMaterial3Api
class vender : ComponentActivity() {
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

                val navController = rememberNavController()

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
                    vender(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = venderViewModel(),
                    )
                }
            }// fim do theme
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun vender(modifier: Modifier = Modifier, viewModel: venderViewModel) {

    // dados de entrada
    val pesquisaTexto by viewModel.pesquisaTexto.collectAsState()
    val produtosFiltrados by viewModel.produtosFiltrados.collectAsState()

    val produtoSelecionado2 by viewModel.produtoSelecionado.collectAsState()
    val quantidadeSelecionada by viewModel.quantidadeSelecionada.collectAsState()

    //permissão
    val tipoUsuario by viewModel.tipoUsuario.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.buscarTipoUsuario()
    }

    //localizar ambiente
    LaunchedEffect(Unit) {
        viewModel.obterEstabelecimentoDoUsuarioAtual()
    }
    val estabelecimentoId by viewModel.estabelecimentoId.collectAsState()

    if (estabelecimentoId.isNotEmpty()) {
        viewModel.carregarProdutos(estabelecimentoId)
    } else {
        Text("Estabelecimento nao encontrado")
    }

    // navegação
    var context = LocalContext.current
    var intentDados = Intent(context, dados::class.java)

    // cosmeticos
    val fundoCampo = Blue20
    val fonte = robotoCondensed
    val corFonte = Blue10
    val backCard = Color.White
    val textColor = Blue10
    val textFont = interBold
    val textFont2 = interMedium
    val textPreco = Color.Red

    // hover do botão
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val buttonColor = if (isPressed) {
        Blue20
    } else {
        Blue10
    }
    val textButtonColor = if (isPressed) {
        Blue10
    } else {
        Color.White
    }

    // background
    Column(modifier.background(Color.White).fillMaxSize())
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
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = null,
                    tint = Blue10,
                    modifier = Modifier.size(60.dp)
                )
            }
        }// fim do cabelaçlho

        //campo de venda
        Column(modifier = Modifier.fillMaxWidth().weight(2f)){

            Column(modifier = Modifier.fillMaxWidth().weight(1f).padding(10.dp).background(color = fundoCampo, shape = RoundedCornerShape(50.dp)))
            {
                // campo de pesquisa
                Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.Center)
                {
                    OutlinedTextField(
                        value = pesquisaTexto,
                        onValueChange = { novoTexto -> viewModel.atualizarTextoPesquisa(novoTexto) },
                        maxLines = 1,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            errorContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                        ),
                        textStyle = TextStyle(color = Blue10, fontFamily = fonte),
                        placeholder = { Text("Buscar Produto") },
                        modifier = Modifier
                            .background(Blue20, shape = RoundedCornerShape(10.dp))
                            .widthIn(min = 400.dp, max = 700.dp),
                        shape = RoundedCornerShape(10.dp),
                        leadingIcon = {
                            Icon(imageVector = Icons.Rounded.Search, contentDescription = null, tint = Blue10)
                        }
                    )//Fim textField1 (Pesquisa)

                }// fim campo de pesquisa


                var produtoSelecionado by remember { mutableStateOf<Produto?>(null) }
                var qntSelecionada by remember { mutableStateOf(0) }
               Column(){

                   LazyColumn(contentPadding = PaddingValues(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                       items(produtosFiltrados) {
                               produto ->
                           val qnt = remember { mutableStateOf(0) }
                           val isSelecionado = produto == produtoSelecionado2

                           Card(
                               shape = RoundedCornerShape(10.dp),
                               colors = CardDefaults.cardColors(containerColor = backCard),
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .clickable { viewModel.selecionarProduto(produto)
                                       viewModel.resetarEstadoProdutoAdicionado() }
                                   .border(
                                       width = if (isSelecionado) 2.dp else 0.dp,
                                       color = if (isSelecionado) Blue10 else Color.Transparent,
                                       shape = RoundedCornerShape(10.dp)
                                   ),
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
                                       modifier = Modifier.fillMaxWidth().padding(start = 10.dp),
                                       horizontalArrangement = Arrangement.SpaceBetween
                                   ){
                                       Row(){
                                           Box(modifier = Modifier.width(210.dp)){Text(text = "Preço: ", color = textPreco, fontFamily = textFont)}
                                           Text(text = "R$${produto.precoVenda}", color = textColor, fontFamily = textFont2)
                                       }// box do preço
                                       Row(modifier = Modifier.padding(end = 10.dp, bottom = 10.dp)){
                                           OutlinedTextField(
                                               value = qnt.value.toString(),
                                               onValueChange = { },
                                               readOnly = true,
                                               textStyle = TextStyle(color = Blue10),
                                               placeholder = {  },
                                               shape = RoundedCornerShape(10.dp),
                                               colors = TextFieldDefaults.colors(
                                                   focusedContainerColor = fundoCampo,
                                                   unfocusedContainerColor = fundoCampo,
                                                   disabledContainerColor = fundoCampo,
                                                   errorContainerColor = fundoCampo,
                                                   focusedIndicatorColor = fundoCampo,
                                                   unfocusedIndicatorColor = fundoCampo,
                                                   disabledIndicatorColor = fundoCampo,
                                                   errorIndicatorColor = fundoCampo,
                                                   focusedTextColor = fundoCampo,
                                                   unfocusedTextColor = fundoCampo,
                                                   disabledTextColor = fundoCampo,
                                                   errorTextColor = fundoCampo,
                                               ),
                                               leadingIcon = {
                                                   IconButton(onClick = {
                                                       if (qnt.value > 0) {
                                                           qnt.value--
                                                           produtoSelecionado = produto // Atualiza qual produto será afetado
                                                           qntSelecionada = qnt.value // Atualiza a quantidade escolhida
                                                       }
                                                   }) {
                                                       Icon(
                                                           imageVector = Icons.Filled.Remove,
                                                           contentDescription = "Subtrair",
                                                           tint = Blue10,

                                                       )
                                                   }
                                               },
                                               trailingIcon = {
                                                   IconButton(onClick = {
                                                       qnt.value++
                                                       produtoSelecionado = produto // Atualiza o produto selecionado
                                                       qntSelecionada = qnt.value
                                                   }) {
                                                       Icon(
                                                           imageVector = Icons.Filled.Add,
                                                           contentDescription = "Somar",
                                                           tint = Blue10,

                                                       )
                                                   }
                                               },
                                               modifier = Modifier
                                                       .background(Color.White, shape = RoundedCornerShape(10.dp))
                                               .widthIn(
                                                   min = 80.dp,
                                                   max = 150.dp
                                               ) // Ajuste os valores conforme necessário3
                                               .padding(0.dp)
                                           )
                                       }
                                   }// fim do box nome comercial e preço
                               }// estrutura o card
                           }// fim do card
                       }// fim do items
                   }// fim do lazycolumn
                   if (produtoSelecionado2 != null) {
                       val produtoAdicionado by viewModel.produtoAdicionado.collectAsState()
                       Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center,){
                           Button(
                               onClick = {
                                   produtoSelecionado2?.let { produto ->
                                       if (!produtoAdicionado) {
                                           viewModel.adicionarAoCarrinho(produto, qntSelecionada)
                                           viewModel.marcarProdutoComoAdicionado()
                                       } else {
                                       context.startActivity(intentDados)
                                   }
                                   }

                               },
                               interactionSource = interactionSource,
                               colors = ButtonDefaults.buttonColors(
                                   containerColor = if (produtoAdicionado) Color(0xFF4CAF50) else buttonColor,
                                   contentColor = textButtonColor
                               ),
                               shape = RoundedCornerShape(15.dp),
                               modifier = Modifier
                                   .wrapContentWidth()
                                   .padding(10.dp),
                           ) {
                               Text(text = if (produtoAdicionado) "Avançar" else "Adicionar",
                                   fontFamily = textFont2)
                           }
                       }
                   }// fim botão

               }




               }

            

            }// fim do card

        Column(modifier = Modifier.fillMaxWidth().weight(1f).background(color = Color.White)){}
        }// fim do background






    }// fim da função
