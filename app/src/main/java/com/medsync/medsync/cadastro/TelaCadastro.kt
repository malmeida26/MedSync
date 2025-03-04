@file:OptIn(ExperimentalMaterial3Api::class)

package com.medsync.medsync.cadastro

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.medsync.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.medsync.medsync.MainActivity
import com.medsync.medsync.ui.theme.ui.theme.Blue10
import com.medsync.medsync.ui.theme.ui.theme.Blue20
import com.medsync.medsync.ui.theme.ui.theme.Blue30
import com.medsync.medsync.ui.theme.ui.theme.interBold
import com.medsync.medsync.ui.theme.ui.theme.interThin
import com.medsync.medsync.ui.theme.ui.theme.quickSand

class TelaCadastro : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Cadastro()

        }
    }
}

//  TODO IMPLENTAR CADASTRO COM NUMERO DE TELEFONE

@Composable
fun Cadastro() {
//    VARIAVEIS CADASTRO
    var nomeCadastro by remember { mutableStateOf("") }
    var emailCadastro by remember { mutableStateOf("") }
    var senhaCadastro by remember { mutableStateOf("") }
    var confirmarSenhaCadastro: String by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }


//    MENSAGENS
    var context = LocalContext.current

//    Firebase
    var auth = Firebase.auth

//    NAVEGAÇÃO
    var intentLogin = Intent(context, MainActivity::class.java)

//    Text Field
    var clicadoSenha by remember { mutableStateOf(false) }
    var clicadoConfirmarSenha by remember { mutableStateOf(false) }

//    Coluna Base. Meu deus n mexe nesse
    Column(modifier = Modifier.fillMaxSize()) {

        // margem de cima
        Column(
            modifier = Modifier
                .fillMaxWidth()
                 .weight(1f)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {  }

// principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(6f)
                .background(color = Color.White)
        ) {

// colum alinhador
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally


            ) {
                // logo, formualrios e texto
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier.padding(bottom = 25.dp)
                    )
                    //                Nome
                    OutlinedTextField(
                        value = nomeCadastro,
                        onValueChange = { nomeCadastro = it },
                        maxLines = 1,
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
                        placeholder = { Text(text = "Insira seu nome completo") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Blue20, shape = RoundedCornerShape(15.dp)),
                        shape = RoundedCornerShape(15.dp),
                        leadingIcon = {
                            Icon( imageVector = Icons.Rounded.Person, contentDescription = null, tint = Blue10, modifier = Modifier.size(30.dp) )
                        }
                    )
                    Spacer(modifier = Modifier.height(15.dp))
//            Email
                    OutlinedTextField(
                        value = emailCadastro,
                        onValueChange = { emailCadastro = it },
                        maxLines = 1,
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
                        placeholder = { Text(text = "Insira seu email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Blue20, shape = RoundedCornerShape(15.dp)),
                        shape = RoundedCornerShape(15.dp),
                        leadingIcon = {
                            Icon( imageVector = Icons.Rounded.Email, contentDescription = null, tint = Blue10, modifier = Modifier.size(30.dp) )
                        }
                    )

                    Spacer(modifier = Modifier.height(15.dp))
//                Número
                    OutlinedTextField(
                        value = numero,
                        onValueChange = { numero = it },
                        maxLines = 1,
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
                        placeholder = { Text(text = "Insira seu número") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Blue20, shape = RoundedCornerShape(15.dp)),
                        shape = RoundedCornerShape(15.dp),
                        leadingIcon = {
                            Icon( imageVector = Icons.Rounded.Phone, contentDescription = null, tint = Blue10, modifier = Modifier.size(30.dp) )
                        }
                    )

                    Spacer(modifier = Modifier.height(15.dp))
//              Senha
                    OutlinedTextField(
                        value = senhaCadastro,
                        onValueChange = { senhaCadastro = it },
                        maxLines = 1,
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
                        placeholder = { Text(text = "Digite sua senha") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { focusState ->
                                clicadoSenha = focusState.isFocused

                                if (!focusState.isFocused && senhaCadastro.isEmpty()) {
                                }
                            }
                            .onFocusChanged { focusState ->
                                clicadoSenha = focusState.isFocused
                            }
                            .background(Blue20, shape = RoundedCornerShape(15.dp)),
                        visualTransformation = if (clicadoSenha) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { clicadoSenha = !clicadoSenha }
                            ) {
                                Icon(
                                    imageVector = if (clicadoSenha) {
                                        Icons.Rounded.Visibility
                                    } else {
                                        Icons.Rounded.VisibilityOff
                                    },
                                    contentDescription = "Esconder e Mostrar Senha",
                                    tint = Blue10, modifier = Modifier.size(30.dp)
                                )
                            }
                        },
                        shape = RoundedCornerShape(15.dp),
                        leadingIcon = {
                            Icon( imageVector = Icons.Rounded.Lock, contentDescription = null, tint = Blue10, modifier = Modifier.size(30.dp) )
                        }
                    )

                    Spacer(modifier = Modifier.height(15.dp))
//            Confirmar Senha
                    OutlinedTextField(
                        value = confirmarSenhaCadastro,
                        onValueChange = { confirmarSenhaCadastro = it },
                        maxLines = 1,
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
                        placeholder = { Text(text = "Confirme sua senha") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { focusState ->
                                clicadoConfirmarSenha = focusState.isFocused

                                if (!focusState.isFocused && confirmarSenhaCadastro.isEmpty()) {

                                }
                            }
                            .onFocusChanged { focusState ->
                                clicadoConfirmarSenha = focusState.isFocused
                            }
                            .background(Blue20, shape = RoundedCornerShape(15.dp)),
                        visualTransformation = if (clicadoConfirmarSenha) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { clicadoSenha = !clicadoSenha }
                            ) {
                                Icon(
                                    imageVector = if (clicadoSenha) {
                                        Icons.Rounded.Visibility
                                    } else {
                                        Icons.Rounded.VisibilityOff
                                    },
                                    contentDescription = "Esconder e Mostrar Senha",
                                    tint = Blue10, modifier = Modifier.size(30.dp)
                                )
                            }
                        },
                        shape = RoundedCornerShape(15.dp),
                        leadingIcon = {
                            Icon( imageVector = Icons.Rounded.CheckCircle, contentDescription = null, tint = Blue10, modifier = Modifier.size(30.dp) )
                        }
                    )

                    Text(
                        fontFamily = interThin,
                        color = Blue30,
                        text = "Todos os campos são obrigatórios"
                    ) }

                // botão de cadastrar
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom, modifier = Modifier.background(Color.White).fillMaxHeight().weight(2f)) {
                    Button(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(Blue10, shape = RoundedCornerShape(50.dp)),
                        colors = ButtonDefaults.buttonColors(Blue10),
                        shape = RoundedCornerShape(400.dp),
                        onClick = {
                            if (nomeCadastro.isEmpty() || emailCadastro.isEmpty() || senhaCadastro.isEmpty() || confirmarSenhaCadastro.isEmpty() || numero.isEmpty()) {
                                Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_SHORT)
                                    .show()
                            } else {

                                if (senhaCadastro == confirmarSenhaCadastro) {
                                    Firebase.auth.createUserWithEmailAndPassword(
                                        emailCadastro,
                                        senhaCadastro
                                    )

                                    Toast.makeText(
                                        context,
                                        "Cadastro finalizado! Logue novamente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    context.startActivity(intentLogin)
                                } else {
                                    Toast.makeText(context, "Senhas não conferem!", Toast.LENGTH_SHORT)
                                        .show()
                                }

                            }

                        },
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),

                    ) {
                        Text(text = "Cadastrar", fontFamily = quickSand)
                    }
                }

            }// fim do alinhador
        }// fim do colum princiapl


        // margem de baixo
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "Deseja voltar?",
                fontFamily = interBold,
                color = Blue10,
                modifier = Modifier
                    .clickable { context.startActivity(intentLogin) }
            )
        }



    }// fim do background
}// fim da função