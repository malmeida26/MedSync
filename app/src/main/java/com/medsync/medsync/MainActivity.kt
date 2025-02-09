package com.medsync.medsync

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material.icons.twotone.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.medsync.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.medsync.medsync.cadastro.TelaCadastro
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginTela()
        }
    }
}

@Composable
fun LoginTela() {
    //LOGIN
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    val context = LocalContext.current
    val authenticationManager = remember { AuthenticationManager() }

    val coroutineScope = rememberCoroutineScope()

    //FIREBASE
    val auth = Firebase.auth

    Log.i(TAG, "onCreate: ${auth.currentUser}")

    /*
    //está criando o usuario. TO-DO: tem que automatizar

    auth.createUserWithEmailAndPassword(
        "admin@fmm.org.br",
        "123456"
    ).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Log.i(TAG, "create user: sucesso")
        }else{
            Log.i(TAG, "create user: falha -> ${task.exception}")
        }
    }
     */

    //Email cadastrado. Podem acessar
    /*auth.signInWithEmailAndPassword(
        "admin@fmm.org.br",
        "123456"
    )

     */

    val corountineScope = rememberCoroutineScope()

    //CADASTRO
    var clicadoEmail by remember { mutableStateOf(false) }
    var clicadoSenha by remember { mutableStateOf(false) }
    var labelEmail by remember { mutableStateOf("Email") }
    var labelSenha by remember { mutableStateOf("Senha") }


    var contexto = LocalContext.current


    //NAVEGACAO
    val intentCadastro = Intent(
        contexto,
        TelaCadastro::class.java
    )  // Esse é o Sou Novo Aqui. Leva pra a tela de Cadastro
    val intentMenu =
        Intent(contexto, TelaMenu::class.java)  // Esse é o Login. Leva pra a tela de Menu


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
        // .verticalScroll(ScrollState(0))
    ) {
        //Nome da Marca/Cabeçalho
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.White),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(250.dp)

            )
        }


        //Campos de entrada e Processo de Login
        Row(
            //"Fila" que contem os componenstes abaixo. Mudar o "Color.Gren" para cor desejada
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .background(Color.Green)
                .padding(30.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //No geral, aqui não precisa mexer
            //Só se quiser mudar o estilo da caixa de texto
//            OutLinedTextField. As configs dela são traqnuilas
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Campo para digitar Email
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    maxLines = 1,
                    label = { Text(labelEmail) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent
                    ),
                    placeholder = { Text("Digite seu email") },
                    trailingIcon = {
                        if (clicadoEmail) {
                            labelEmail = ""
                        }
                    },
//                    Aqui ta fezendo a verificação do focus, apaga e coloca dnv aquela
//                    placeholder se o usuario selecionar
                    modifier = Modifier
                        .onFocusChanged { focusState ->
                            clicadoEmail = focusState.isFocused
                            if (!focusState.isFocused && email.isEmpty()) {
                                labelEmail = "Email"
                            }
                        }
                        .onFocusChanged { focusState ->
                            clicadoEmail = focusState.isFocused
                        }
                        .fillMaxWidth()
                        .padding(start = 50.dp, end = 50.dp)
                )//Fim textField1 (Email)


//               Espaço entre os campos
                Spacer(modifier = Modifier.height(20.dp))


//               Campos para digitar Senha -> A mesma ideia de cima, mas coloquei pra
//               esconder a senha
                TextField(
                    value = senha,
                    onValueChange = { senha = it },
                    maxLines = 1,
                    label = { Text(labelSenha) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent
                    ),
                    visualTransformation = if (clicadoSenha) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    placeholder = { Text("Digite seu email") },
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
                        .onFocusChanged { focusState ->
                            clicadoSenha = focusState.isFocused

                            if (!focusState.isFocused && email.isEmpty()) {
                                labelSenha = "Senha"
                            }
                        }
                        .onFocusChanged { focusState ->
                            clicadoSenha = focusState.isFocused
                        }
                        .fillMaxWidth()
                        .padding(start = 50.dp, end = 50.dp)
                )// Fim textField2 (Senha)

                //Textos Clicaveis --> TO-DO: falta a navegação e as telas
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                        .background(Color.Blue),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(Color.Red)
                    ) {
                        Text(
                            text = "Sou novo aqui",
                            style = TextStyle(color = Color.LightGray),
                            maxLines = 1,
                            modifier = Modifier
                                .clickable {
                                    contexto.startActivity(intentCadastro)
                                },
                        )
                    }

                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(Color.Red)
                    ) {
                        Text(
                            text = "Esqueci minha senha",
                            style = TextStyle(color = Color.LightGray),
                            maxLines = 1,
                            modifier = Modifier
                                .clickable { },
                        )

                    }


                }

                Spacer(modifier = Modifier.height(70.dp))

                //Botao de login
                Button(
                    colors = ButtonDefaults.buttonColors(Color.Cyan),
                    modifier = Modifier.width(200.dp),
                    onClick = {
                        if (email.isEmpty() || senha.isEmpty()) {
                            Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(context, "Logando...", Toast.LENGTH_SHORT).show()
                            authenticationManager.loginWithEmail(email, senha)
                                .onEach { response ->
                                    if (response is AuthReponse.Success) {
                                        contexto.startActivity(intentMenu)
                                        Toast.makeText(context, "Bem Vindo!", Toast.LENGTH_SHORT)
                                            .show()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Falha no login! Verifique suas credenciais.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                                .launchIn(coroutineScope)
                        }
                    }

                ) {
                    Text(text = "Login")
                }

            }

        }


        //Foto/Rodapé
//        Falta fazer o rodapé -> No tablet, se deitar a tela, essa parte some
//        Mas deve ser a falta da imagem, a princípio
        Row(
            modifier = Modifier
                .background(Color.Red)
                .fillMaxSize()
        ) {
            Text(text = "Adicionar foto aqui")
        }
    }

}


class AuthenticationManager {
    private val auth = Firebase.auth

    fun CreateAccount(email: String, senha: String): Flow<AuthReponse> = callbackFlow {

        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(AuthReponse.Success)
                } else {
                    trySend(AuthReponse.Error(message = task.exception?.message ?: ""))
                }
            }
        awaitClose()
    }

    fun loginWithEmail(email: String, senha: String): Flow<AuthReponse> = callbackFlow {
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(AuthReponse.Success)
                } else {
                    trySend(AuthReponse.Error(message = task.exception?.message ?: ""))
                }

            }
        awaitClose()
    }
}

interface AuthReponse {
    data object Success : AuthReponse
    data class Error(val message: String) : AuthReponse


}