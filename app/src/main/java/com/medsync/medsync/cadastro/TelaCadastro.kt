package com.medsync.medsync.cadastro

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.medsync.medsync.MainActivity

class TelaCadastro : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Cadastro()

        }
    }
}

@Composable
fun Cadastro() {
//    VARIAVEIS CADASTRO
    var nomeCadastro by remember { mutableStateOf("") }
    var sobrenomeCadastro by remember { mutableStateOf("") }
    var emailCadastro by remember { mutableStateOf("") }
    var senhaCadastro by remember { mutableStateOf("") }
    var confirmarSenhaCadastro: String by remember { mutableStateOf("") }

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
//        Esse row tem o cabeçalho e o nome "cadastro"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .background(color = Color.Green)
                .padding(30.dp), // <- cor de fundo. Alterem depois
            verticalAlignment = Alignment.Bottom

        ) {
//            Aqui ta o texto. Cor, fonte e tamanho precisam ser colocados
            Text(
                text = "Cadastro",
                textAlign = TextAlign.Start,
                fontSize = 50.sp,
                maxLines = 1
            )
        }

//        Esse row tem o formulario de cadastro
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = Color.Red), // <- cor de fundo. Alterem depois
        ) {
//            Nome
            Column(
                modifier = Modifier
                    .weight(5f)
                    .height(150.dp)
                    .background(color = Color.Yellow), // <- cor de fundo. Alterem depois
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                OutlinedTextField(
                    value = nomeCadastro,
                    onValueChange = { nomeCadastro = it },
                    maxLines = 1,
                    placeholder = { Text(text = "Nome") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .padding(start = 30.dp),
                    shape = RoundedCornerShape(40.dp),
                )
            }
//             Sobrenome
            Column(
                modifier = Modifier
                    .weight(5f)
                    .height(150.dp)
                    .background(color = Color.Black)
                    .padding(20.dp),
                verticalArrangement = Arrangement.Bottom,
            ) {
                OutlinedTextField(
                    value = sobrenomeCadastro,
                    onValueChange = { sobrenomeCadastro = it },
                    maxLines = 1,
                    placeholder = { Text(text = "Digite seu sobrenome") },
                    modifier = Modifier
                        .fillMaxWidth()
                        //.padding(10.dp)
                        .padding(end = 30.dp),
                    shape = RoundedCornerShape(40.dp),
                )
            }
        } // FIM DO ROW QUE CONTEM NOME E SOBRENOME


//      ESSE COLUMN TEM OS CAMPOS DE CADASTRO EMAIL, SENHA E CONFIRMAR SENHA
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = Color.Magenta),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
//            Email
            OutlinedTextField(
                value = emailCadastro,
                onValueChange = { emailCadastro = it },
                maxLines = 1,
                placeholder = { Text(text = "Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .padding(start = 30.dp)
                    .padding(end = 30.dp),
                shape = RoundedCornerShape(40.dp),
            )

//              Senha
            OutlinedTextField(
                value = senhaCadastro,
                onValueChange = { senhaCadastro = it },
                maxLines = 1,
                placeholder = { Text(text = "Senha") },
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
                            contentDescription = "Esconder e Mostrar Senha"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .padding(start = 30.dp)
                    .padding(end = 30.dp)
                    .onFocusChanged { focusState ->
                        clicadoSenha = focusState.isFocused

                        if (!focusState.isFocused && senhaCadastro.isEmpty()) {
                        }
                    }
                    .onFocusChanged { focusState ->
                        clicadoSenha = focusState.isFocused
                    },
                shape = RoundedCornerShape(40.dp),

                )

//            Confirmar Senha
            OutlinedTextField(
                value = confirmarSenhaCadastro,
                onValueChange = { confirmarSenhaCadastro = it },
                maxLines = 1,
                placeholder = { Text(text = "Confirmar senha") },
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
                            contentDescription = "Esconder e Mostrar Senha"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .padding(start = 30.dp)
                    .padding(end = 30.dp)
                    .onFocusChanged { focusState ->
                        clicadoConfirmarSenha = focusState.isFocused

                        if (!focusState.isFocused && confirmarSenhaCadastro.isEmpty()) {

                        }
                    }
                    .onFocusChanged { focusState ->
                        clicadoConfirmarSenha = focusState.isFocused
                    },
                shape = RoundedCornerShape(40.dp),
            )

        }


//        Esse row tem o rodapé
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .background(color = Color.Blue) // <- cor de fundo. Alterem depois
        ) {
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .padding(top = 20.dp, bottom = 15.dp),

                onClick = {
                    //Sisteminha de inserir credenciais no banco.
                    if (nomeCadastro.isEmpty() || sobrenomeCadastro.isEmpty() || emailCadastro.isEmpty() || senhaCadastro.isEmpty() || confirmarSenhaCadastro.isEmpty()) {
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

                }

            ) {
                Text(text = "Cadastrar")
            }
        }


    }


}