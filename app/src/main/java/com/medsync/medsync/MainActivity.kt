package com.medsync.medsync

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.medsync.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.medsync.medsync.cadastro.TelaCadastro
import com.medsync.medsync.escqueciSenha.esqueciSenha
import com.medsync.medsync.menu.TelaMenu
import com.medsync.medsync.ui.theme.ui.theme.Blue10
import com.medsync.medsync.ui.theme.ui.theme.Blue20
import com.medsync.medsync.ui.theme.ui.theme.Blue30
import com.medsync.medsync.ui.theme.ui.theme.GreenToast
import com.medsync.medsync.ui.theme.ui.theme.RedText
import com.medsync.medsync.ui.theme.ui.theme.interBold
import com.medsync.medsync.ui.theme.ui.theme.interThin
import com.medsync.medsync.ui.theme.ui.theme.quickSand
import com.medsync.medsync.ui.theme.ui.theme.quickSandBold
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val TAG = "MainActivity"

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginTela()
        }
    }
}

@ExperimentalMaterial3Api
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

    //CADASTRO
    var clicadoEmail by remember { mutableStateOf(false) }
    var clicadoSenha by remember { mutableStateOf(false) }
    var labelEmail by remember { mutableStateOf("Email") }
    var labelSenha by remember { mutableStateOf("Senha") }

    var contexto = LocalContext.current

    // hover do botão
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val buttonColor = if(isPressed){
        Blue20
    }else{
        Blue10
    }

    val textButtonColor = if(isPressed){
        Blue10
    }else{
        Color.White
    }

    // gerenciamento
    var showToastesqueciSenha by remember { mutableStateOf(false) }
    var showToastLogando by remember { mutableStateOf(false) }
    var showToastCredenciais by remember { mutableStateOf(false) }
    var showToastCampos by remember { mutableStateOf(false) }
    var showToastBV by remember { mutableStateOf(false) }


    //NAVEGACAO
    val intentCadastro = Intent(contexto, TelaCadastro::class.java)  // Esse é o Sou Novo Aqui. Leva pra a tela de Cadastro
    val intentMenu = Intent(contexto, TelaMenu::class.java)  // Esse é o Login. Leva pra a tela de Menu


// backgrond
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {

        // colum que da margem em cima
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {  }// fim da margem top


        //Campos de entrada e Processo de Login
        Column(
            //Centraliza os column
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
                .background(Color.White)
                .padding(10.dp)
                .verticalScroll(rememberScrollState(0)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // tem a imagem, os 2 campos e o esqueci senha
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp).clickable { contexto.startActivity(intentMenu) }
                )

                //Campo para digitar Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
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

                    placeholder = { Text("Insira seu email") },
                    trailingIcon = {
                        if (clicadoEmail) {
                            labelEmail = ""
                        }
                    },
                    modifier = Modifier
                        .width(600.dp)
                        .padding(top = 50.dp)
                        .onFocusChanged { focusState ->
                            clicadoEmail = focusState.isFocused
                            if (!focusState.isFocused && email.isEmpty()) {
                                labelEmail = "Email"
                            }
                        }
                        .onFocusChanged { focusState ->
                            clicadoEmail = focusState.isFocused
                        }
                        .background(Blue20, shape = RoundedCornerShape(50.dp)),
                    shape = RoundedCornerShape(22.dp),
                    leadingIcon = {
                        Icon(imageVector = Icons.Rounded.Person, contentDescription = null, tint = Blue10)
                    }
                )//Fim textField1 (Email)

//               Espaço entre os campos
                Spacer(modifier = Modifier.height(30.dp))

                // digitar senha
                OutlinedTextField(
                    value = senha,
                    onValueChange = { senha = it },
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
                    visualTransformation = if (clicadoSenha) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    placeholder = { Text("Digite sua senha") },
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
                                tint = Blue10,
                            )
                        }
                    },

                    modifier = Modifier
                        .width(600.dp)
                        .onFocusChanged { focusState ->
                            clicadoSenha = focusState.isFocused

                            if (!focusState.isFocused && email.isEmpty()) {
                                labelSenha = "Senha"
                            }
                        }
                        .onFocusChanged { focusState ->
                            clicadoSenha = focusState.isFocused
                        }
                        .background(Blue20, shape = RoundedCornerShape(50.dp)),
                    shape = RoundedCornerShape(22.dp),
                    leadingIcon = {
                        Icon(imageVector = Icons.Rounded.Lock, contentDescription = null, tint = Blue10)
                    },
                )// Fim textField2 (Senha)

                // esqueci senha
                Row(
                    modifier = Modifier
                        .width(600.dp)
                        .background(Color.White)
                        .padding(top = 5.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                        Text(
                            text = "Esqueceu sua senha?",
                            fontFamily = interThin,
                            maxLines = 1,
                            color = Blue30,
                            modifier = Modifier
                                .clickable {
                                    if(email.isNotEmpty()){
                                        val intent = Intent(context, esqueciSenha::class.java)
                                        intent.putExtra("email", email)
                                        context.startActivity(intent)
                                    }else{
                                        showToastesqueciSenha = true
                                    }


                                },
                        )
                }
                //Botao de login
                Button(
                    interactionSource = interactionSource,
                    colors = ButtonDefaults.buttonColors(buttonColor, contentColor = textButtonColor),
                    shape = RoundedCornerShape(40.dp),
                    modifier = Modifier.width(300.dp).padding(top = 70.dp),
                    onClick = {
                        if (email.isEmpty() || senha.isEmpty()) {
                            showToastCampos = true
                        } else {
                            showToastLogando = true
                            authenticationManager.loginWithEmail(email, senha)
                                .onEach { response ->
                                    if (response is AuthReponse.Success) {
                                        contexto.startActivity(intentMenu)
                                        showToastBV = true
                                    } else {
                                        showToastCredenciais = true
                                    }
                                }
                                .launchIn(coroutineScope)
                        }
                    },
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)

                ) {
                    Text(text = "Entrar", fontFamily = quickSand)
                }

            }

            /*
            // o botão de entrar
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom, modifier = Modifier.background(Color.White).fillMaxHeight().weight(2f)) {

            }

             */
        }// fim do colum centralizador


        // botao de nao tem conta/ margem em baixo
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .weight(1f)
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                fontFamily = interBold,
                text = "Não tem uma conta? Criar",
                maxLines = 1,
                color = Blue10,
                modifier = Modifier
                    .clickable { contexto.startActivity(intentCadastro) })
        }// fim do colum do n tem conta

        CustomToast(show = showToastCampos, message = "Preencha todos os campos!", texto = Color.White, icone = Color.White, backgroundColor = RedText, iconVec = Icons.Filled.Lock)
        LaunchedEffect(key1 = showToastCampos) {
            if (showToastCampos) {
                delay(2000)
                showToastCampos = false
            }
            }

        CustomToast(show = showToastCredenciais, message = "Credenciais Inválidas!", texto = Color.White, icone = Color.White, backgroundColor = RedText, iconVec = Icons.Filled.Password)
        LaunchedEffect(key1 = showToastCredenciais) {
            if (showToastCredenciais) {
                delay(2000)
                showToastCredenciais = false
            }
        }

        CustomToast(show = showToastLogando, message = "Logando...", texto = Blue10, icone = Blue10, backgroundColor = GreenToast, iconVec = Icons.Filled.Login)
        LaunchedEffect(key1 = showToastLogando) {
            if (showToastLogando) {
                delay(2000)
                showToastLogando = false
            }
        }

        CustomToast(show = showToastesqueciSenha, message = "Preencha o campo de email primeiro!", texto = Color.White, icone = Color.White, backgroundColor = RedText, iconVec = Icons.Filled.Mail)
        LaunchedEffect(key1 = showToastesqueciSenha) {
            if (showToastesqueciSenha) {
                delay(2000)
                showToastesqueciSenha = false
            }
        }

        CustomToast(show = showToastLogando, message = "Bem vindo!", texto = Blue10, icone = Blue10, backgroundColor = Blue20, iconVec = Icons.Filled.Login)
        LaunchedEffect(key1 = showToastLogando) {
            if (showToastLogando) {
                delay(2000)
                showToastLogando = false
            }
        }

    }// fim do background
}// fim da função




@Composable
fun CustomToast(show: Boolean, message: String, texto: Color, icone: Color, backgroundColor: Color, iconVec: ImageVector) {
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
                    .background(backgroundColor, shape = RoundedCornerShape(25.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = message, color = texto, fontFamily = quickSandBold)
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = iconVec,
                    contentDescription = "Success",
                    tint = icone,
                    modifier = Modifier.size(24.dp)
                )

            }
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