package com.medsync.medsync.escqueciSenha

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.medsync.medsync.MainActivity
import com.medsync.medsync.perfil.TelaPerfil
import com.medsync.medsync.escqueciSenha.ui.theme.MedSyncTheme
import com.medsync.medsync.ui.theme.ui.theme.Blue10
import com.medsync.medsync.ui.theme.ui.theme.Blue20
import com.medsync.medsync.ui.theme.ui.theme.GreenToast
import com.medsync.medsync.ui.theme.ui.theme.RedText
import com.medsync.medsync.ui.theme.ui.theme.interThin
import com.medsync.medsync.ui.theme.ui.theme.quickSandBold
import kotlinx.coroutines.delay

@ExperimentalMaterial3Api
class esqueciSenha : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedSyncTheme {

                val context = LocalContext.current
                val intentVoltar = Intent(context, MainActivity::class.java)
                val intentPerfil = Intent(context, TelaPerfil::class.java)

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
                                        .background(Blue20, shape = RoundedCornerShape(15.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    IconButton(onClick = { context.startActivity(intentVoltar) }) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBackIosNew,
                                            contentDescription = null,
                                            tint = Blue10,
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
                    esqueciSenha(Modifier.padding(innerPadding))
                }


            }// fim do theme
        }// fim do setContent
    }
}

@Composable
fun esqueciSenha(modifier: Modifier = Modifier) {

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

    val context = LocalContext.current
    val intent = (context as? ComponentActivity)?.intent // Obtém o Intent da Activity
    val email = intent?.getStringExtra("email") // Associa o valor a esse email
    var showToast by remember { mutableStateOf(false) }




    // background
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

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
                    .background(Blue20, shape = RoundedCornerShape(15.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = null,
                    tint = Blue10,
                    modifier = Modifier.size(60.dp)
                )
            }
        }// fim do cabelaçlho

        // parte principal
        Column(
            modifier = Modifier
                .weight(2f)
                .padding(50.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // informações
            Column(
                modifier = Modifier
                    .background(Blue20, shape = RoundedCornerShape(50.dp))
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Ok, Clique em Enviar e nós vamos mandar um Link para o E-mail: ",
                    fontFamily = quickSandBold,
                    color = Blue10
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = email.toString(), fontFamily = quickSandBold, color = RedText)

                Button(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 30.dp, bottom = 40.dp),
                    interactionSource = interactionSource,
                    colors = ButtonDefaults.buttonColors(
                        buttonColor,
                        contentColor = textButtonColor
                    ),
                    onClick = {
                        FirebaseAuth.getInstance().sendPasswordResetEmail(email.toString())
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    showToast = true
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Falha no envio do email! " + task.exception,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    },
                    shape = RoundedCornerShape(25.dp),
                    contentPadding = PaddingValues(
                        start = 40.dp,
                        end = 40.dp,
                        top = 10.dp,
                        bottom = 10.dp
                    )
                )
                {
                    Text(text = "Enviar", fontFamily = interThin)
                }// fim do button
            }// fim do column de infos

            CustomToast(show = showToast, message = "Link Enviado")
            LaunchedEffect(key1 = showToast) {
                if (showToast) {
                    delay(3000)
                    showToast = false
                }
            }
        }// fim do colum parte principal
    }// fim do background


    }// fim da função

    @Composable
    fun CustomToast(show: Boolean, message: String) {
        val textos = Blue10
        val icones = Blue10
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
                        .background(GreenToast, shape = RoundedCornerShape(25.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = message, color = textos, fontFamily = quickSandBold)
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Success",
                        tint = icones,
                        modifier = Modifier.size(24.dp)
                    )

                }
            }
        }
    }



