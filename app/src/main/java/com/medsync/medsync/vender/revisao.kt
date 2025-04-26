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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.medsync.medsync.VendaData
import com.medsync.medsync.menu.TelaMenu
import com.medsync.medsync.ui.theme.ui.theme.Blue10
import com.medsync.medsync.ui.theme.ui.theme.Blue20
import com.medsync.medsync.ui.theme.ui.theme.Blue30
import com.medsync.medsync.ui.theme.ui.theme.MedSyncTheme
import com.medsync.medsync.ui.theme.ui.theme.interBold
import com.medsync.medsync.ui.theme.ui.theme.interMedium
import com.medsync.medsync.ui.theme.ui.theme.quickSandBold
import com.medsync.medsync.ui.theme.ui.theme.robotoCondensed

@ExperimentalMaterial3Api
class revisao : ComponentActivity() {
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
                    revisao(Modifier.padding(innerPadding), viewModel = venderViewModel())
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun revisao(modifier: Modifier = Modifier, viewModel: venderViewModel) {

    // recuperar dados
    val nomeCliente by viewModel.nomeCliente.collectAsState()
    val formaPagamento by viewModel.formaPagamento.collectAsState()


    val itensCarrinho by viewModel.itensCarrinho.collectAsState()
    val totalVenda by viewModel.totalVenda.collectAsState()

    //nav
    var context = LocalContext.current
    var intentCancelar= Intent(context, cancelar_venda::class.java)
    var intentFinalizar= Intent(context, finalizar_venda::class.java)



    // cosmeticos
    val fundoCampo = Blue20
    val fundoCampo2 = Color.White
    val fonte = robotoCondensed
    val corFonte = Blue10
    val green = Color.Green
    val fontFont = interBold
    val fontFont2 = interMedium
    val corPreco = Color.Red

    // hover do botão
    // Botão 1
    val interactionSource1 = remember { MutableInteractionSource() }
    val isPressed1 by interactionSource1.collectIsPressedAsState()
    val buttonColor1 = if (isPressed1) Color.White else Blue10
    val textButtonColor1 = if (isPressed1) Blue10 else Color.White

// Botão 2
    val interactionSource2 = remember { MutableInteractionSource() }
    val isPressed2 by interactionSource2.collectIsPressedAsState()
    val buttonColor2 = if (isPressed2) Color.White else Color.Red
    val textButtonColor2 = if (isPressed2) Color.Red else Color.White

// Botão 3
    val interactionSource3 = remember { MutableInteractionSource() }
    val isPressed3 by interactionSource3.collectIsPressedAsState()
    val buttonColor3 = if (isPressed3) Color.White else Color(0xFF4CAF50)
    val textButtonColor3 = if (isPressed3) Color(0xFF4CAF50) else Color.White

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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                        .background(color = fundoCampo2, shape = RoundedCornerShape(20.dp))
                )
                {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 15.dp))
                    {
                        Text(text = "Nome do Cliente: ", color = corFonte, fontFamily = fonte)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = VendaData.nomeCliente, fontFamily = fonte, color = Blue30)
                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 5.dp))
                    {

                        Text(text = "Produtos: ", color = corFonte, fontFamily = fonte)
                        Spacer(modifier = Modifier.width(10.dp))
                        itensCarrinho.forEach { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, top = 5.dp)
                            ) {
                                Text(
                                    text = "${item.produto.nomeProduto} (x${item.quantidade})",
                                    fontFamily = fonte,
                                    color = Blue30
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "R$ %.2f".format(item.produto.precoVenda.toInt() * item.quantidade),
                                    fontFamily = fonte,
                                    color = corPreco
                                )
                            }
                        }
                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 5.dp))
                    {
                        Text(text = "Forma de Pagamento: ", color = corFonte, fontFamily = fonte)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = VendaData.formaPagamento, fontFamily = fonte, color = Blue30)
                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 5.dp, end = 20.dp))
                    {
                        Text(text = "Total: ", color = corPreco, fontFamily = fonte)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            "R$ %.2f".format(VendaData.calcularTotal()),
                            fontFamily = fonte,
                            color = Blue30
                        )
                    }
                }// caixa de infos

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center)
                {
                    Button(
                        interactionSource = interactionSource1,
                        colors = ButtonDefaults.buttonColors(buttonColor1, contentColor = textButtonColor1),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(10.dp),
                        onClick = {
                        }
                    )
                    {
                        Text("Add Produtos")
                    }// fim do botao
                    Spacer(modifier = Modifier.width(5.dp))
                    Button(
                        interactionSource = interactionSource2,
                        colors = ButtonDefaults.buttonColors(buttonColor2, contentColor = textButtonColor2),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(10.dp),
                        onClick = {
                            context.startActivity(intentCancelar)
                        }
                    )
                    {
                        Text("Cancelar")
                    }// fim do botao
                    Spacer(modifier = Modifier.width(5.dp))
                    Button(
                        interactionSource = interactionSource3,
                        colors = ButtonDefaults.buttonColors(buttonColor3, contentColor = textButtonColor3),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(10.dp),
                        onClick = {
                            context.startActivity(intentFinalizar)
                        }
                    )
                    {
                        Text("Finalizar")
                    }// fim do botao
                }

            }// fundo importante


            Column(modifier = Modifier.fillMaxWidth().weight(1f).background(color = Color.White)) {}
        }// fim do campo

    }// fim do background

}// fim da função


