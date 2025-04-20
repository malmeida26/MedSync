package com.medsync.medsync.desempenho

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
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.AutoGraph
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medsync.R
import com.medsync.medsync.menu.TelaMenu
import com.medsync.medsync.ui.theme.ui.theme.Black72
import com.medsync.medsync.ui.theme.ui.theme.Blue10
import com.medsync.medsync.ui.theme.ui.theme.Blue20
import com.medsync.medsync.ui.theme.ui.theme.MedSyncTheme
import com.medsync.medsync.ui.theme.ui.theme.interBold
import com.medsync.medsync.ui.theme.ui.theme.interMedium
import com.medsync.medsync.ui.theme.ui.theme.quickSandBold
import com.medsync.medsync.ui.theme.ui.theme.robotoCondensed

@ExperimentalMaterial3Api
class desempenho : ComponentActivity() {
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
                    desempenhoTela(Modifier.padding(innerPadding))
                }


            }// fim do theme

        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun desempenhoTela(modifier: Modifier = Modifier) {

    var isExpandend by remember { mutableStateOf(false) }

//    categorias
    val list = listOf(
        "2025"
    )
    var filtro by remember { mutableStateOf("") }

    // comesticos
    val fonteF = robotoCondensed
    val corF = Blue10
    val fonteTopico = interBold
    val fonteDescr = interMedium
    val corTopico = Blue10
    val corDescr = Black72
    val fundoCaixas = Blue20
    val arredCaixas = 10.dp
    val corIcone = Blue10

    // background
    Column(modifier
        .fillMaxSize()
        .background(Color.White))
    {
        //filtro
        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(10.dp), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.Start)
        {
            ExposedDropdownMenuBox(
                expanded = isExpandend,
                onExpandedChange = { isExpandend = !isExpandend },
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor()
                        .background(Color.White, shape = RoundedCornerShape(10.dp)),
                    value = filtro,
                    onValueChange = {},
                    readOnly = true,
                    textStyle = TextStyle(color = Blue10),
                    placeholder = { Text("Filtro/Ano", fontFamily = robotoCondensed) },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Blue20,
                        unfocusedContainerColor = Blue20,
                        disabledContainerColor = Blue20,
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
                    leadingIcon = { Icon(imageVector = Icons.Filled.FilterAlt, contentDescription = null, tint = Blue10) },
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
                                filtro = list[index]
                                isExpandend = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }// fim do dropdown


        }// fim do filtro

        //datas
        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(4f)
            .background(Color.White)
            .padding(10.dp)
            .verticalScroll(
                rememberScrollState(0)
            ))
        {
            Row(modifier = Modifier
                .background(Color.White)
                .padding(5.dp)){

                Box(modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .weight(1f)
                    .background(fundoCaixas, shape = RoundedCornerShape(arredCaixas)))
                {
                    Row(modifier = Modifier.padding(start = 3.dp)){
                        Column()
                        {
                            Row(modifier = Modifier
                                .wrapContentWidth()
                                .padding(bottom = 10.dp)){ Text(text = "Desempenho Mensal", fontFamily = fonteTopico, color = corTopico) }
                            Row(){Text(text = "Mês: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "Janeiro", fontFamily = fonteDescr, color = corDescr)}
                            Row(){Text(text = "Ano: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "2025", fontFamily = fonteDescr, color = corDescr)}
                        }// coluna de infos
                        Column(modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxSize(),verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Icon(imageVector = Icons.Rounded.AutoGraph, contentDescription = null, tint = corIcone)
                        }// coluna do icone
                    }
                }// fim caixa 1

                Spacer(modifier = Modifier.width(5.dp))

                Box(modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .weight(1f)
                    .background(fundoCaixas, shape = RoundedCornerShape(arredCaixas)))
                {
                    Row(modifier = Modifier.padding(start = 3.dp)){
                        Column()
                        {
                            Row(modifier = Modifier
                                .wrapContentWidth()
                                .padding(bottom = 10.dp)){ Text(text = "Desempenho Mensal", fontFamily = fonteTopico, color = corTopico) }
                            Row(){Text(text = "Mês: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "Fevereiro", fontFamily = fonteDescr, color = corDescr)}
                            Row(){Text(text = "Ano: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "2025", fontFamily = fonteDescr, color = corDescr)}
                        }// coluna de infos
                        Column(modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxSize(),verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Icon(imageVector = Icons.Rounded.AutoGraph, contentDescription = null, tint = corIcone)
                        }// coluna do icone
                    }
                }// fim caixa 1
            }// fim do row de dupla-caixa

            Row(modifier = Modifier
                .background(Color.White)
                .padding(5.dp)){

                Box(modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .weight(1f)
                    .background(fundoCaixas, shape = RoundedCornerShape(arredCaixas)))
                {
                    Row(modifier = Modifier.padding(start = 3.dp)){
                        Column()
                        {
                            Row(modifier = Modifier
                                .wrapContentWidth()
                                .padding(bottom = 10.dp)){ Text(text = "Desempenho Mensal", fontFamily = fonteTopico, color = corTopico) }
                            Row(){Text(text = "Mês: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "Março", fontFamily = fonteDescr, color = corDescr)}
                            Row(){Text(text = "Ano: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "2025", fontFamily = fonteDescr, color = corDescr)}
                        }// coluna de infos
                        Column(modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxSize(),verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Icon(imageVector = Icons.Rounded.AutoGraph, contentDescription = null, tint = corIcone)
                        }// coluna do icone
                    }
                }// fim caixa 1

                Spacer(modifier = Modifier.width(5.dp))

                Box(modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .weight(1f)
                    .background(fundoCaixas, shape = RoundedCornerShape(arredCaixas)))
                {
                    Row(modifier = Modifier.padding(start = 3.dp)){
                        Column()
                        {
                            Row(modifier = Modifier
                                .wrapContentWidth()
                                .padding(bottom = 10.dp)){ Text(text = "Desempenho Mensal", fontFamily = fonteTopico, color = corTopico) }
                            Row(){Text(text = "Mês: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "Abril", fontFamily = fonteDescr, color = corDescr)}
                            Row(){Text(text = "Ano: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "2025", fontFamily = fonteDescr, color = corDescr)}
                        }// coluna de infos
                        Column(modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxSize(),verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Icon(imageVector = Icons.Rounded.AutoGraph, contentDescription = null, tint = corIcone)
                        }// coluna do icone
                    }
                }// fim caixa 1
            }// fim do row de dupla-caixa

            Row(modifier = Modifier
                .background(Color.White)
                .padding(5.dp)){

                Box(modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .weight(1f)
                    .background(fundoCaixas, shape = RoundedCornerShape(arredCaixas)))
                {
                    Row(modifier = Modifier.padding(start = 3.dp)){
                        Column()
                        {
                            Row(modifier = Modifier
                                .wrapContentWidth()
                                .padding(bottom = 10.dp)){ Text(text = "Desempenho Mensal", fontFamily = fonteTopico, color = corTopico) }
                            Row(){Text(text = "Mês: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "Maio", fontFamily = fonteDescr, color = corDescr)}
                            Row(){Text(text = "Ano: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "2025", fontFamily = fonteDescr, color = corDescr)}
                        }// coluna de infos
                        Column(modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxSize(),verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Icon(imageVector = Icons.Rounded.AutoGraph, contentDescription = null, tint = corIcone)
                        }// coluna do icone
                    }
                }// fim caixa 1

                Spacer(modifier = Modifier.width(5.dp))

                Box(modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .weight(1f)
                    .background(fundoCaixas, shape = RoundedCornerShape(arredCaixas)))
                {
                    Row(modifier = Modifier.padding(start = 3.dp)){
                        Column()
                        {
                            Row(modifier = Modifier
                                .wrapContentWidth()
                                .padding(bottom = 10.dp)){ Text(text = "Desempenho Mensal", fontFamily = fonteTopico, color = corTopico) }
                            Row(){Text(text = "Mês: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "Junho", fontFamily = fonteDescr, color = corDescr)}
                            Row(){Text(text = "Ano: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "2025", fontFamily = fonteDescr, color = corDescr)}
                        }// coluna de infos
                        Column(modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxSize(),verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Icon(imageVector = Icons.Rounded.AutoGraph, contentDescription = null, tint = corIcone)
                        }// coluna do icone
                    }
                }// fim caixa 1
            }// fim do row de dupla-caixa

            Row(modifier = Modifier
                .background(Color.White)
                .padding(5.dp)){

                Box(modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .weight(1f)
                    .background(fundoCaixas, shape = RoundedCornerShape(arredCaixas)))
                {
                    Row(modifier = Modifier.padding(start = 3.dp)){
                        Column()
                        {
                            Row(modifier = Modifier
                                .wrapContentWidth()
                                .padding(bottom = 10.dp)){ Text(text = "Desempenho Mensal", fontFamily = fonteTopico, color = corTopico) }
                            Row(){Text(text = "Mês: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "Julho", fontFamily = fonteDescr, color = corDescr)}
                            Row(){Text(text = "Ano: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "2025", fontFamily = fonteDescr, color = corDescr)}
                        }// coluna de infos
                        Column(modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxSize(),verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Icon(imageVector = Icons.Rounded.AutoGraph, contentDescription = null, tint = corIcone)
                        }// coluna do icone
                    }
                }// fim caixa 1

                Spacer(modifier = Modifier.width(5.dp))

                Box(modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .weight(1f)
                    .background(fundoCaixas, shape = RoundedCornerShape(arredCaixas)))
                {
                    Row(modifier = Modifier.padding(start = 3.dp)){
                        Column()
                        {
                            Row(modifier = Modifier
                                .wrapContentWidth()
                                .padding(bottom = 10.dp)){ Text(text = "Desempenho Mensal", fontFamily = fonteTopico, color = corTopico) }
                            Row(){Text(text = "Mês: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "Agosto", fontFamily = fonteDescr, color = corDescr)}
                            Row(){Text(text = "Ano: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "2025", fontFamily = fonteDescr, color = corDescr)}
                        }// coluna de infos
                        Column(modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxSize(),verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Icon(imageVector = Icons.Rounded.AutoGraph, contentDescription = null, tint = corIcone)
                        }// coluna do icone
                    }
                }// fim caixa 1
            }// fim do row de dupla-caixa

            Row(modifier = Modifier
                .background(Color.White)
                .padding(5.dp)){

                Box(modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .weight(1f)
                    .background(fundoCaixas, shape = RoundedCornerShape(arredCaixas)))
                {
                    Row(modifier = Modifier.padding(start = 3.dp)){
                        Column()
                        {
                            Row(modifier = Modifier
                                .wrapContentWidth()
                                .padding(bottom = 10.dp)){ Text(text = "Desempenho Mensal", fontFamily = fonteTopico, color = corTopico) }
                            Row(){Text(text = "Mês: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "Setembro", fontFamily = fonteDescr, color = corDescr)}
                            Row(){Text(text = "Ano: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "2025", fontFamily = fonteDescr, color = corDescr)}
                        }// coluna de infos
                        Column(modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxSize(),verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Icon(imageVector = Icons.Rounded.AutoGraph, contentDescription = null, tint = corIcone)
                        }// coluna do icone
                    }
                }// fim caixa 1

                Spacer(modifier = Modifier.width(5.dp))

                Box(modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .weight(1f)
                    .background(fundoCaixas, shape = RoundedCornerShape(arredCaixas)))
                {
                    Row(modifier = Modifier.padding(start = 3.dp)){
                        Column()
                        {
                            Row(modifier = Modifier
                                .wrapContentWidth()
                                .padding(bottom = 10.dp)){ Text(text = "Desempenho Mensal", fontFamily = fonteTopico, color = corTopico) }
                            Row(){Text(text = "Mês: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "Outubro", fontFamily = fonteDescr, color = corDescr)}
                            Row(){Text(text = "Ano: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "2025", fontFamily = fonteDescr, color = corDescr)}
                        }// coluna de infos
                        Column(modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxSize(),verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Icon(imageVector = Icons.Rounded.AutoGraph, contentDescription = null, tint = corIcone)
                        }// coluna do icone
                    }
                }// fim caixa 1
            }// fim do row de dupla-caixa

            Row(modifier = Modifier
                .background(Color.White)
                .padding(5.dp)){

                Box(modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .weight(1f)
                    .background(fundoCaixas, shape = RoundedCornerShape(arredCaixas)))
                {
                    Row(modifier = Modifier.padding(start = 3.dp)){
                        Column()
                        {
                            Row(modifier = Modifier
                                .wrapContentWidth()
                                .padding(bottom = 10.dp)){ Text(text = "Desempenho Mensal", fontFamily = fonteTopico, color = corTopico) }
                            Row(){Text(text = "Mês: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "Novembro", fontFamily = fonteDescr, color = corDescr)}
                            Row(){Text(text = "Ano: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "2025", fontFamily = fonteDescr, color = corDescr)}
                        }// coluna de infos
                        Column(modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxSize(),verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Icon(imageVector = Icons.Rounded.AutoGraph, contentDescription = null, tint = corIcone)
                        }// coluna do icone
                    }
                }// fim caixa 1

                Spacer(modifier = Modifier.width(5.dp))

                Box(modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .weight(1f)
                    .background(fundoCaixas, shape = RoundedCornerShape(arredCaixas)))
                {
                    Row(modifier = Modifier.padding(start = 3.dp)){
                        Column()
                        {
                            Row(modifier = Modifier
                                .wrapContentWidth()
                                .padding(bottom = 10.dp)){ Text(text = "Desempenho Mensal", fontFamily = fonteTopico, color = corTopico) }
                            Row(){Text(text = "Mês: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "Dezembro", fontFamily = fonteDescr, color = corDescr)}
                            Row(){Text(text = "Ano: ", fontFamily = fonteTopico, color = corTopico)
                                Text(text = "2025", fontFamily = fonteDescr, color = corDescr)}
                        }// coluna de infos
                        Column(modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxSize(),verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Icon(imageVector = Icons.Rounded.AutoGraph, contentDescription = null, tint = corIcone)
                        }// coluna do icone
                    }
                }// fim caixa 1
            }// fim do row de dupla-caixa


        }// fim das datas

    }// fim do background

}



