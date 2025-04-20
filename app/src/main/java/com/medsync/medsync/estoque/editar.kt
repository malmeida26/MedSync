@file:OptIn(ExperimentalMaterial3Api::class)

package com.medsync.medsync.estoque

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
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
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medsync.R
import com.medsync.medsync.cadastroProdutos.CustomToast
import com.medsync.medsync.cadastroProdutos.produtosViewModel
import com.medsync.medsync.menu.TelaMenu
import com.medsync.medsync.ui.theme.ui.theme.Blue10
import com.medsync.medsync.ui.theme.ui.theme.Blue20
import com.medsync.medsync.ui.theme.ui.theme.GreenToast
import com.medsync.medsync.ui.theme.ui.theme.MedSyncTheme
import com.medsync.medsync.ui.theme.ui.theme.RedText
import com.medsync.medsync.ui.theme.ui.theme.interBold
import com.medsync.medsync.ui.theme.ui.theme.quickSand
import com.medsync.medsync.ui.theme.ui.theme.quickSandBold
import com.medsync.medsync.ui.theme.ui.theme.robotoCondensed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class editar : ComponentActivity() {
    @ExperimentalMaterial3Api
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

                                // TODO FAZER O BOTÃO DE VOLTAR AO MENU
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
                    Editar(Modifier.padding(innerPadding), viewModel = produtosViewModel())
                }
            }
        }
    }
}

@Composable
fun Editar(modifier: Modifier = Modifier, viewModel: produtosViewModel) {
    // dados
    var nomeProduto by remember { mutableStateOf("") }
    var nomeGenerico by remember { mutableStateOf("") }
    var apresentacao by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var precoCompra by remember { mutableStateOf("") }
    var precoVenda by remember { mutableStateOf("") }
    var qnt by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.carregarEstabelecimentoDoUsuarioLogado()
    }
    val estabelecimentoId by viewModel.estabelecimentoId.collectAsState()


    // data
    var openDatePicker by remember { mutableStateOf(false) }
    var isExpandend by remember { mutableStateOf(false) }

//    categorias
    val list = listOf(
        "analgésicos e antitérmicos",
        "antiflamatórios e antibióticos",
        "antifúngicos e antivirais",
        "anti-histamínicos e antialérgicos",
        "suplementos e vitaminas",
        "antiácidos e laxantes",
        "contraceptivos"
    )
    var categoria by remember { mutableStateOf("") }


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

    // msg
    var showToast by remember { mutableStateOf(false) }
    var showToastErro by remember { mutableStateOf(false) }

    var context = LocalContext.current


    // background
    Column(modifier
        .fillMaxSize()
        .background(Color.White)) {

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
                Text(text = "Editar", fontFamily = quickSandBold, color = Blue10)
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = null,
                    tint = Blue10,
                    modifier = Modifier.size(60.dp)
                )
            }
        }// fim do cabelaçlho

        // caixa de infos
        Column(
            modifier = Modifier
                .padding(10.dp)
                .weight(5f)
                .wrapContentSize()
                .verticalScroll(rememberScrollState(0))
                .background(
                    color = Blue20,
                    shape = RoundedCornerShape(50.dp))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Column(modifier = Modifier.padding(20.dp)) {
                //  nome produto
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(modifier = Modifier.width(200.dp)){
                        Text(
                            text = "Nome Produto",
                            fontFamily = interBold,
                            color = Blue10,
                        )
                    }// fim box que tem o nome produto
                    // ele tem que pegar até o nome, o resto é o do campo
                    OutlinedTextField(
                        value = nomeProduto,
                        onValueChange = { nomeProduto = it },
                        textStyle = TextStyle(color = Blue10),
                        placeholder = { Text("Digite aqui", fontFamily = robotoCondensed) },
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            focusedTextColor = Blue10,
                            unfocusedTextColor = Blue10,
                            disabledTextColor = Blue10,
                            errorTextColor = Blue10,
                        ),
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                            .fillMaxWidth()
                    )
                }
                // nome genérico
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(modifier = Modifier.width(200.dp)) {
                        Text(
                            text = "Nome Genérico",
                            fontFamily = interBold,
                            color = Blue10,
                            modifier = Modifier
                            // .padding(start = 15.dp)
                        )
                    }

                    OutlinedTextField(
                        value = nomeGenerico,
                        onValueChange = { nomeGenerico = it },
                        textStyle = TextStyle(color = Blue10),
                        placeholder = { Text("Digite aqui", fontFamily = robotoCondensed) },
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            focusedTextColor = Blue10,
                            unfocusedTextColor = Blue10,
                            disabledTextColor = Blue10,
                            errorTextColor = Blue10,
                        ),
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                            .weight(3f)
                    )
                }


// Categoria
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(modifier = Modifier.width(200.dp)) {
                        Text(
                            text = "Categorias",
                            fontFamily = interBold,
                            color = Blue10,
                        )
                    }
                    ExposedDropdownMenuBox(
                        expanded = isExpandend,
                        onExpandedChange = { isExpandend = !isExpandend },
                        modifier = Modifier.weight(3f)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .menuAnchor()
                                .background(Color.White, shape = RoundedCornerShape(10.dp)),
                            value = categoria,
                            onValueChange = {},
                            readOnly = true,
                            textStyle = TextStyle(color = Blue10),
                            placeholder = { Text("Selecione", fontFamily = robotoCondensed) },
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                errorContainerColor = Color.Transparent,
                                focusedIndicatorColor = colorResource(id = R.color.Azul40),
                                unfocusedIndicatorColor = colorResource(id = R.color.Azul40),
                                disabledIndicatorColor = colorResource(id = R.color.Azul40),
                                errorIndicatorColor = colorResource(id = R.color.Azul40),
                                focusedTextColor = Blue10,
                                unfocusedTextColor = Blue10,
                                disabledTextColor = Blue10,
                                errorTextColor = Blue10,

                                ),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandend) },
                        )

                        ExposedDropdownMenu(
                            expanded = isExpandend,
                            onDismissRequest = { isExpandend = false },
                            modifier = Modifier.background(Color.White)
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
                                        categoria = list[index]
                                        isExpandend = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }
                    }// fim do dropdown

                }
// apresnetação
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(modifier = Modifier.width(200.dp)) {
                        Text(
                            text = "Apresentação",
                            fontFamily = interBold,
                            color = Blue10,
                            modifier = Modifier
                        )
                    }
                    OutlinedTextField(
                        value = apresentacao,
                        onValueChange = { apresentacao = it },
                        textStyle = TextStyle(color = Blue10),
                        placeholder = {
                            Text(
                                "Informações adicionais",
                                fontFamily = robotoCondensed
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            focusedTextColor = Blue10,
                            unfocusedTextColor = Blue10,
                            disabledTextColor = Blue10,
                            errorTextColor = Blue10,
                        ),
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                            .weight(3f)

                    )
                }
// Data de validade
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(modifier = Modifier.width(200.dp)) {
                        Text(
                            text = "Data de validade",
                            fontFamily = interBold,
                            color = Blue10,
                        )
                    }
                    OutlinedTextField(
                        value = date,
                        onValueChange = { date = it },
                        textStyle = TextStyle(color = Blue10),
                        placeholder = { Text("dia/mês/ano", fontFamily = robotoCondensed) },
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            focusedTextColor = Blue10,
                            unfocusedTextColor = Blue10,
                            disabledTextColor = Blue10,
                            errorTextColor = Blue10,
                        ),
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                            .weight(3f),
                        interactionSource = remember {
                            MutableInteractionSource()
                        }.also {
                            LaunchedEffect(it) {
                                it.interactions.collectLatest { interaction ->
                                    if (interaction is PressInteraction.Release) {
                                        openDatePicker = true
                                    }
                                }
                            }

                        },
                        readOnly = true
                    )
                    val state = rememberDatePickerState()
                    AnimatedVisibility(openDatePicker) {
                        DatePickerDialog(
                            onDismissRequest = {
                                openDatePicker = false
                            }, confirmButton = {
                                Button(onClick = {
                                    state.selectedDateMillis?.let { millis ->
                                        val instant = Instant.fromEpochMilliseconds(millis)
                                        val localDateTime =
                                            instant.toLocalDateTime(TimeZone.currentSystemDefault())
                                        val formattedDate =
                                            "${localDateTime.dayOfMonth}/${localDateTime.monthNumber}/${localDateTime.year}"
                                        date = formattedDate
                                    }
                                    openDatePicker = false
                                }) {
                                    Text("Selecionar")
                                }
                            }
                        ) {
                            DatePicker(state = state)
                        }

                    }

                }


                // codigo de barras TODO ABRIR CAMERA E SALVAR A FOTO
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Editar Código de barras",
                        fontFamily = interBold,
                        color = Blue10,
                    )
                    Box(
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                            .wrapContentSize()
                    ) {
                        IconButton(onClick = { /* Ação ao clicar na câmera */ }) {
                            Icon(
                                imageVector = Icons.Filled.CameraAlt,
                                contentDescription = "Ícone da Câmera",
                                tint = Blue10,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }


// preço de compra
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(modifier = Modifier.wrapContentSize()) {
                        Text(
                            text = "Preço de compra",
                            fontFamily = interBold,
                            color = Blue10,
                        )// fim compra
                    }
                    Box(
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                            .wrapContentSize()
                    ) {
                        OutlinedTextField(
                            value = precoCompra,
                            onValueChange = { precoCompra = it },
                            textStyle = TextStyle(color = Blue10),
                            placeholder = { Text("R$ 00.00", fontFamily = robotoCondensed) },
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                errorContainerColor = Color.Transparent,
                                focusedTextColor = Blue10,
                                unfocusedTextColor = Blue10,
                                disabledTextColor = Blue10,
                                errorTextColor = Blue10,
                            ),
                            modifier = Modifier
                                .background(Color.White, shape = RoundedCornerShape(10.dp))
                                .widthIn(
                                    min = 80.dp,
                                    max = 150.dp
                                ) // Ajuste os valores conforme necessário
                                .padding(0.dp)
                        )// compra preço
                    }
                }

//                    preço de venda

                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(modifier = Modifier.width(200.dp)) {
                        Text(
                            text = "Preço de venda",
                            fontFamily = interBold,
                            color = Blue10,
                        )// preço venda
                    }
                    Box(
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                            .wrapContentSize()
                    ) {
                        OutlinedTextField(
                            value = precoVenda,
                            onValueChange = { precoVenda = it },
                            textStyle = TextStyle(color = Blue10),
                            placeholder = { Text("R$ 00.00", fontFamily = robotoCondensed) },
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                errorContainerColor = Color.Transparent,
                                focusedTextColor = Blue10,
                                unfocusedTextColor = Blue10,
                                disabledTextColor = Blue10,
                                errorTextColor = Blue10,
                            ),
                            modifier = Modifier
                                .background(Color.White, shape = RoundedCornerShape(10.dp))
                                .widthIn(
                                    min = 80.dp,
                                    max = 150.dp
                                ) // Ajuste os valores conforme necessário
                                .padding(0.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(modifier = Modifier.width(200.dp)) {
                        Text(
                            text = "Quantidade",
                            fontFamily = interBold,
                            color = Blue10,
                        )// fim quantidade
                    }
                    Box(
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                            .wrapContentSize()
                    ) {
                        OutlinedTextField(
                            value = qnt.toString(),
                            onValueChange = { },
                            textStyle = TextStyle(color = Blue10),
                            placeholder = { Text(text = "0", fontFamily = robotoCondensed) },
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
                                focusedTextColor = Blue10,
                                unfocusedTextColor = Blue10,
                                disabledTextColor = Blue10,
                                errorTextColor = Blue10,
                            ),
                            leadingIcon = {
                                IconButton(onClick = {
                                    if (qnt > 0) {
                                        qnt--
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Remove,
                                        contentDescription = "Subtrair",
                                        tint = Blue10,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            },
                            trailingIcon = {
                                IconButton(onClick = { qnt++ }) {
                                    Icon(
                                        imageVector = Icons.Filled.Add,
                                        contentDescription = "Somar",
                                        tint = Blue10,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            },
                            modifier = Modifier
                                .background(Color.White, shape = RoundedCornerShape(10.dp))
                                .widthIn(
                                    min = 80.dp,
                                    max = 150.dp
                                ) // Ajuste os valores conforme necessário
                                .padding(0.dp)
                        )
                    }// fim da opção que aumenta e diminui a quantidade
                }

// botao
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        interactionSource = interactionSource,
                        colors = ButtonDefaults.buttonColors(
                            buttonColor,
                            contentColor = textButtonColor
                        ),
                        modifier = Modifier.padding(10.dp),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            if (nomeProduto.isEmpty() || nomeGenerico.isEmpty() || apresentacao.isEmpty() || date.isEmpty() || precoCompra.isEmpty() || precoVenda.isEmpty() || qnt == 0) {
                                showToastErro = true
                            } else {
                                viewModel.produtoDB(
                                    nome = nomeProduto, // O nome do produto (string)
                                    nomeGenerico = nomeGenerico, // O nome genérico do produto (string)
                                    apresentacao = apresentacao, // A apresentação do produto (string)
                                    dataValidade = date, // A data de validade do produto (string)
                                    precoCompra = precoCompra, // O preço de compra do produto (string)
                                    precoVenda = precoVenda, // O preço de venda do produto (string)
                                    categoria = categoria, // A categoria do produto (string)
                                    estabelecimentoId = estabelecimentoId!!,
                                    qnt = qnt,
                                )
                                showToast = true

                                nomeProduto = ""
                                nomeGenerico = ""
                                apresentacao = ""
                                date = ""
                                precoCompra = ""
                                precoVenda = ""
                                qnt = 0
                                categoria = ""

                            }
                        }
                    ) {
                        Text("Editar", fontFamily = quickSand, fontSize = 24.sp)
                    }


                }
            }


        }// fim da caixa que tem os campos

        // toast's
        CustomToast(
            show = showToast,
            message = "Produto Editado",
            texto = Blue10,
            icone = Blue10,
            backgroundColor = GreenToast,
            tipo = true
        )
        LaunchedEffect(key1 = showToast) {
            if (showToast) {
                delay(2000)
                showToast = false
            }
        }

        CustomToast(show = showToastErro, message = "Preencha todos os campos!", texto = Color.Black, icone = Color.Black, backgroundColor = RedText, tipo = false)
        LaunchedEffect(key1 = showToastErro) {
            if (showToastErro) {
                delay(2000)
                showToastErro= false
            }
        }

    }// fim do background

}