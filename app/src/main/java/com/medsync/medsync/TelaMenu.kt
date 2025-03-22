package com.medsync.medsync

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.AutoGraph
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.rounded.Whatsapp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.medsync.medsync.cadastroProdutos.CadastrarProdutos
import com.medsync.medsync.desempenho.desempenho
import com.medsync.medsync.estoque.estoque
import com.medsync.medsync.ui.theme.ui.theme.Blue10
import com.medsync.medsync.ui.theme.ui.theme.Blue20
import com.medsync.medsync.ui.theme.ui.theme.MedSyncTheme
import com.medsync.medsync.ui.theme.ui.theme.quickSandBold
import com.medsync.medsync.vender.vender
import com.medsync.medsync.whatsapp.whatsapp


class TelaMenu : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
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
                val intentVoltar = Intent(context, MainActivity::class.java)
                val intentMenu = Intent(context, TelaMenu::class.java)
                val intentPerfil = Intent(context, TelaPerfil::class.java)

                Scaffold(modifier = Modifier.fillMaxSize(), topBar={
                    TopAppBar(title = {
                        Text("")
                    },
                        actions = {
                        Row(Modifier
                            .padding(8.dp)
                            .background(Color.White)
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Absolute.SpaceBetween
                        ){

                            Box(Modifier
                                .wrapContentSize()
                                .background(iconBackgroundColor, shape = RoundedCornerShape(15.dp)), contentAlignment = Alignment.Center){
                                IconButton(onClick = { context.startActivity(intentVoltar)}, interactionSource = interactionSource) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBackIosNew,
                                        contentDescription = null,
                                        tint = iconColor,
                                        modifier = Modifier.size(44.dp)

                                    )
                                }
                            }

                            Box(Modifier.wrapContentSize()) {
                                IconButton(onClick = {  }) {
                                    Icon(
                                        imageVector = Icons.Filled.Home,
                                        contentDescription = null,
                                        tint = Blue10,
                                        modifier = Modifier.size(44.dp)
                                    )
                                }
                            }

                            Box(Modifier
                                .wrapContentSize()
                                .background(iconBackgroundColor2, shape = RoundedCornerShape(15.dp)), contentAlignment = Alignment.Center
                            ) {
                                IconButton(onClick = { context.startActivity(intentPerfil) }, interactionSource = interactionSource2 ) {
                                    Icon(
                                        imageVector = Icons.Filled.Person,
                                        contentDescription = null,
                                        tint = iconColor2,
                                        modifier = Modifier.size(44.dp),)
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
                    Menu(Modifier.padding(innerPadding))
                }


            }// fim do theme
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun Menu(modifier: Modifier = Modifier) {

    // cores dos box
    var boxColor by remember { mutableStateOf(Blue20) }
    var textColor by remember { mutableStateOf(Blue10) }
    var iconColor by remember { mutableStateOf(Blue10) }

    var boxColor1 by remember { mutableStateOf(Blue20) }
    var textColor1 by remember { mutableStateOf(Blue10) }
    var iconColor1 by remember { mutableStateOf(Blue10) }

    var boxColor2 by remember { mutableStateOf(Blue20) }
    var textColor2 by remember { mutableStateOf(Blue10) }
    var iconColor2 by remember { mutableStateOf(Blue10) }

    var boxColor4 by remember { mutableStateOf(Blue20) }
    var textColor4 by remember { mutableStateOf(Blue10) }
    var iconColor4 by remember { mutableStateOf(Blue10) }

    var boxColor5 by remember { mutableStateOf(Blue20) }
    var textColor5 by remember { mutableStateOf(Blue10) }
    var iconColor5 by remember { mutableStateOf(Blue10) }



    //NAVEGAÇÃO
    var context = LocalContext.current
     var intentProdutos = Intent(context, CadastrarProdutos::class.java)
    var intentEstoque = Intent(context, estoque::class.java)
    var intentVender = Intent(context, vender::class.java)
    var intentDesempenho = Intent(context, desempenho::class.java)
    var intentWhatsapp = Intent(context, whatsapp::class.java)

    // FONTES
    val menus = quickSandBold

    // background
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(50.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally)
    {

        // contem as opções
        Column(modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // cadastrar produto
            Box(Modifier
                .fillMaxWidth()
                .background(boxColor, shape = RoundedCornerShape(25.dp))
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { offset ->
                            boxColor = Blue10
                            textColor = Color.White
                            iconColor = Color.White
                            try {
                                awaitRelease()
                            } finally {
                                boxColor = Blue20 // Cor padrão
                                textColor = Blue10
                                iconColor = Blue10
                            }
                        },
                        onTap = {
                            context.startActivity(intentProdutos)
                        }
                    )
                }, contentAlignment = Alignment.Center)
            {
                // vai alinhar os itens dentro do box
                Row(modifier = Modifier
                    .padding(10.dp)
                    .height(50.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween)
                {
                    Text(text = "Cadastrar Produto", fontFamily = menus, color = textColor)
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(imageVector = Icons.Rounded.Edit, contentDescription = null, tint = iconColor)
                }// fim do row de alinhamento
            }// fim box

            Spacer(modifier = Modifier.padding(10.dp))
            // Estoque
            Box(Modifier
                .fillMaxWidth()
                .background(boxColor1, shape = RoundedCornerShape(25.dp))
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { offset ->
                            boxColor1 = Blue10
                            textColor1 = Color.White
                            iconColor1 = Color.White
                            try {
                                awaitRelease()
                            } finally {
                                boxColor1 = Blue20 // Cor padrão
                                textColor1 = Blue10
                                iconColor1 = Blue10
                            }
                        },
                        onTap = {
                            context.startActivity(intentEstoque)
                        }
                    )
                }, contentAlignment = Alignment.Center)
            {
                // vai alinhar os itens dentro do box
                Row(modifier = Modifier
                    .padding(10.dp)
                    .height(50.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween)
                {
                    Text(text = "Estoque", fontFamily = menus, color = textColor1)
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(imageVector = Icons.Rounded.Archive, contentDescription = null, tint = iconColor1)
                }// fim do row de alinhamento
            }// fim box

            Spacer(modifier = Modifier.padding(10.dp))
            // Vender
            Box(Modifier
                .fillMaxWidth()
                .background(boxColor2, shape = RoundedCornerShape(25.dp))
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { offset ->
                            boxColor2 = Blue10
                            textColor2 = Color.White
                            iconColor2 = Color.White
                            try {
                                awaitRelease()
                            } finally {
                                boxColor2 = Blue20 // Cor padrão
                                textColor2 = Blue10
                                iconColor2 = Blue10
                            }
                        },
                        onTap = {
                            context.startActivity(intentVender)
                        }
                    )
                }, contentAlignment = Alignment.Center)
            {
                // vai alinhar os itens dentro do box
                Row(modifier = Modifier
                    .padding(10.dp)
                    .height(50.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween)
                {
                    Text(text = "Vender", fontFamily = menus, color = textColor2)
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(imageVector = Icons.Rounded.ShoppingCart, contentDescription = null, tint = iconColor2)
                }// fim do row de alinhamento
            }// fim box

            Spacer(modifier = Modifier.padding(10.dp))
            // Desempenho
            Box(Modifier
                .fillMaxWidth()
                .background(boxColor4, shape = RoundedCornerShape(25.dp))
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { offset ->
                            boxColor4 = Blue10
                            textColor4 = Color.White
                            iconColor4 = Color.White
                            try {
                                awaitRelease()
                            } finally {
                                boxColor4 = Blue20 // Cor padrão
                                textColor4 = Blue10
                                iconColor4 = Blue10
                            }
                        },
                        onTap = {
                            context.startActivity(intentDesempenho)
                        }
                    )
                }, contentAlignment = Alignment.Center)
            {
                // vai alinhar os itens dentro do box
                Row(modifier = Modifier
                    .padding(10.dp)
                    .height(50.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween)
                {
                    Text(text = "Desempenho", fontFamily = menus, color = textColor4)
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(imageVector = Icons.Rounded.AutoGraph, contentDescription = null, tint = iconColor4)
                }// fim do row de alinhamento
            }// fim box

            Spacer(modifier = Modifier.padding(10.dp))
            // whats
            Box(Modifier
                .fillMaxWidth()
                .background(boxColor5, shape = RoundedCornerShape(25.dp))
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { offset ->
                            boxColor5 = Blue10
                            textColor5 = Color.White
                            iconColor5 = Color.White
                            try {
                                awaitRelease()
                            } finally {
                                boxColor5 = Blue20 // Cor padrão
                                textColor5 = Blue10
                                iconColor5 = Blue10
                            }
                        },
                        onTap = {
                            context.startActivity(intentWhatsapp)
                        }
                    )
                }, contentAlignment = Alignment.Center)
            {
                // vai alinhar os itens dentro do box
                Row(modifier = Modifier
                    .padding(10.dp)
                    .height(50.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween)
                {
                    Text(text = "WhatsApp", fontFamily = menus, color = textColor5)
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(imageVector = Icons.Rounded.Whatsapp, contentDescription = null, tint = iconColor5)
                }// fim do row de alinhamento
            }// fim box

            Spacer(modifier = Modifier.padding(10.dp))







        }// fim do colum opções

    }



}



