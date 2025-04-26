package com.medsync.medsync.vender

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.medsync.medsync.VendaData
import com.medsync.medsync.menu.TelaMenu
import com.medsync.medsync.ui.theme.ui.theme.Blue10
import com.medsync.medsync.ui.theme.ui.theme.Blue20
import com.medsync.medsync.ui.theme.ui.theme.MedSyncTheme
import com.medsync.medsync.ui.theme.ui.theme.interBold
import com.medsync.medsync.ui.theme.ui.theme.interMedium
import com.medsync.medsync.ui.theme.ui.theme.quickSandBold
import com.medsync.medsync.ui.theme.ui.theme.robotoCondensed

@ExperimentalMaterial3Api
class dados : ComponentActivity() {
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
                    dados(Modifier.padding(innerPadding), viewModel = venderViewModel())
                }
            }// fim do theme
        }
        }
    }

@ExperimentalMaterial3Api
@Composable
fun dados(modifier: Modifier = Modifier, viewModel: venderViewModel) {

    val nomeCliente by viewModel.nomeCliente.collectAsState()
    var formaPagamento by remember { mutableStateOf("") }

    var nomeInput by remember { mutableStateOf(nomeCliente) }
    var formaSelecionada by remember { mutableStateOf(formaPagamento) }

    //    categorias
    //var formaPagamento by remember { mutableStateOf("") }
    var isExpandend by remember { mutableStateOf(false) }
    val list = listOf(
        "Pix",
        "Dinheiro",
        "Crédito",
        "Débito"
    )

    // navegação
    var context = LocalContext.current
    var intentRevisao = Intent(context, revisao::class.java)


    // cosmeticos
    val fundoCampo = Blue20
    val fonte = robotoCondensed
    val corFonte = Blue10
    val backCard = Color.White
    val green = Color.Green
    val textFont = interBold
    val textFont2 = interMedium
    val textPreco = Color.Red

    // hover do botão
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val buttonColor = if (isPressed) {
        Color.White
    } else {
        Color(0xFF4CAF50)
    }
    val textButtonColor = if (isPressed) {
        Color(0xFF4CAF50)
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

        //campo
        Column(modifier = Modifier.fillMaxWidth().weight(2f)) {

            Column(
                modifier = Modifier.fillMaxWidth().weight(1f).padding(10.dp)
                    .background(color = fundoCampo, shape = RoundedCornerShape(50.dp))
            )
            {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, top = 40.dp, bottom = 30.dp, end = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(text = "Nome do Cliente:", fontFamily = fonte, color = corFonte)
                    Spacer(modifier = Modifier.width(10.dp))

                    OutlinedTextField(
                        value = nomeInput,
                        onValueChange = {
                            nomeInput = it
                            VendaData.nomeCliente = it
                             },
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
                        placeholder = { Text("Digite aqui") },
                        modifier = Modifier
                            .background(Blue20, shape = RoundedCornerShape(10.dp))
                            .widthIn(min = 400.dp, max = 700.dp),
                        shape = RoundedCornerShape(10.dp),
                    )//Fim textField (Nome do cliente)
                }// row do nome

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, bottom = 30.dp, end = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(text = "Forma de Pagamento:", fontFamily = fonte, color = corFonte)
                    Spacer(modifier = Modifier.width(10.dp))
                    // menu dropdown
                    ExposedDropdownMenuBox(
                        expanded = isExpandend,
                        onExpandedChange = { isExpandend = !isExpandend },
                        ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .menuAnchor()
                                .background(Blue20, shape = RoundedCornerShape(10.dp)),
                            value = formaPagamento,
                            onValueChange = { },
                            readOnly = true,
                            textStyle = TextStyle(color = Blue10),
                            placeholder = { Text("Selecione", fontFamily = robotoCondensed) },
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                errorContainerColor = Color.White,
                                focusedIndicatorColor = Color.White,
                                unfocusedIndicatorColor = Color.White,
                                disabledIndicatorColor = Color.White,
                                errorIndicatorColor = Color.White
                            ),
                        )

                        ExposedDropdownMenu(
                            expanded = isExpandend,
                            onDismissRequest = { isExpandend = false },
                            modifier = Modifier
                                .background(Blue20, shape = RoundedCornerShape(10.dp))
                        ) {
                            list.forEach{text ->
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
                                        formaPagamento = text
                                        VendaData.formaPagamento = text
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }
                    }// fim do dropdown


                }// row do pagamento

                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center){
                    Button(
                        interactionSource = interactionSource,
                        colors = ButtonDefaults.buttonColors(buttonColor, contentColor = textButtonColor),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(10.dp),
                        onClick = {
                            context.startActivity(intentRevisao)
                        }
                    )
                    {
                        Text("Avançar")
                    }// fim do botão
                }


            }


            Column(modifier = Modifier.fillMaxWidth().weight(1f).background(color = Color.White)) {}
        }// fim do campo

    }// fim do background
}// fim da função
