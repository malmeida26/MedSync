package com.medsync.medsync

import android.os.Bundle
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.medsync.medsync.ui.theme.MedSyncTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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
    var confirmarSenhaCadastro by remember { mutableStateOf("") }

//    Firebase
    var auth = Firebase.auth



//    Coluna Base. Meu deus n mexe nesse
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
    ) {
//        Esse row tem o cabeçalho e o nome "cadastro"
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(2f)
            .background(color = Color.Green)
            .padding(30.dp), // <- cor de fundo. Alterem depois
            verticalAlignment = Alignment.Bottom

        ) {
//            Aqui ta o texto. Cor, fonte e tamanho precisam ser colocados
            Text(text = "Cadastro",
                textAlign = TextAlign.Start,
                fontSize = 50.sp,
                maxLines = 1
            )
        }

//        Esse row tem o formulario de cadastro
        Row(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.Red), // <- cor de fundo. Alterem depois
        ) {
//            Nome
            Column(modifier = Modifier
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
            Column(modifier = Modifier
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
                      //  .padding(20.dp)
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .padding(start = 30.dp)
                    .padding(end = 30.dp),
                shape = RoundedCornerShape(40.dp),
            )

//            Confirmar Senha
            OutlinedTextField(
                value = confirmarSenhaCadastro,
                onValueChange = { confirmarSenhaCadastro = it },
                maxLines = 1,
                placeholder = { Text(text = "Confirmar senha") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .padding(start = 30.dp)
                    .padding(end = 30.dp),
                shape = RoundedCornerShape(40.dp),
            )

        }


//        Esse row tem o rodapé
        Row(modifier = Modifier
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
                    Firebase.auth.createUserWithEmailAndPassword(emailCadastro, senhaCadastro)
                }

            ) {
                Text(text = "Cadastrar")
            }
        }




    }


}