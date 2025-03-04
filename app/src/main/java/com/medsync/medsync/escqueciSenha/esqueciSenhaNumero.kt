package com.medsync.medsync.escqueciSenha

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.medsync.R
import com.medsync.medsync.MainActivity
import com.medsync.medsync.escqueciSenha.ui.theme.MedSyncTheme
import com.medsync.medsync.ui.theme.ui.theme.Blue10
import com.medsync.medsync.ui.theme.ui.theme.Blue20
import com.medsync.medsync.ui.theme.ui.theme.interBold
import com.medsync.medsync.ui.theme.ui.theme.interThin
import com.medsync.medsync.ui.theme.ui.theme.quickSand

@ExperimentalMaterial3Api
class esqueciSenhaNumero : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                MedSyncTheme {
                    val context = LocalContext.current
                    val intentVoltar = Intent(context, MainActivity::class.java)

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

                                    Box(Modifier.wrapContentSize()) {
                                        IconButton(onClick = { context.startActivity(intentVoltar) }) {
                                            Icon(
                                                imageVector = Icons.Filled.ArrowBackIosNew,
                                                contentDescription = null,
                                                tint = Color.Black
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.weight(1f))
                                    Box(Modifier.wrapContentSize()) {
                                        Text(
                                            "Esqueci Senha",
                                            textAlign = TextAlign.Center,
                                            fontFamily = quickSand
                                        )
                                    }

                                    Spacer(modifier = Modifier.weight(1f))


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

                        val context = LocalContext.current
                        val intentClique = Intent(context, MainActivity::class.java)

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color.White)
                                .verticalScroll(ScrollState(0))
                                .padding(innerPadding)
                        ) {


//        Imagem
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(5.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.cadeadosla),
                                    contentDescription = null,
                                    modifier = Modifier.size(200.dp)
                                )
                            }
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(5.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(Blue20, RoundedCornerShape(15.dp))
                                        .width(500.dp)
                                        .height(180.dp)
                                        .padding(10.dp), contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text("Enviamos um link para o Número:", fontFamily = interThin, color = Blue10)
                                        Spacer(modifier = Modifier.padding(20.dp))
                                        Text(
                                            "xxxx-xxxx-xxx",
                                            fontFamily = interBold,
                                            color = Blue10,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(bottom = 40.dp)
                            ) {
                                Text(
                                    "Clique aqui",
                                    fontFamily = interBold,
                                    color = Blue10,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.clickable { context.startActivity(intentClique) })


                            }

                        }
                    }
                }



        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun SenhaNumero(modifier: Modifier = Modifier) {


}


