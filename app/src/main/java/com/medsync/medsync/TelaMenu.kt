package com.medsync.medsync

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medsync.R
import com.medsync.medsync.ui.theme.DarkWhite
import com.medsync.medsync.ui.theme.MedSyncTheme

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)


class TelaMenu : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedSyncTheme {
                val items = listOf(
                    BottomNavigationItem(
                        title = "Menu",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Filled.Home,
                        hasNews = false
                    ),
                    BottomNavigationItem(
                        title = "Perfil",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Filled.Home,
                        hasNews = false
                    ),
                )

                var selectedItemIndex by rememberSaveable {
                    mutableStateOf(0)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                items.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                            //  navController.navigate(item.title))
                                        },
                                        label = {
                                            Text(text = item.title)
                                        },
                                        icon = {
                                            BadgedBox(badge = {
                                                if(item.badgeCount != null) {
                                                    Badge{
                                                        Text(text = item.badgeCount.toString())
                                                    }
                                                } else if(item.hasNews){
                                                    Badge()
                                                }
                                            }
                                            ) {
                                                Icon(
                                                    imageVector = if(index == selectedItemIndex) {
                                                        item.selectedIcon
                                                    } else item.unselectedIcon,
                                                    contentDescription = item.title
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    ) {

                    }
                }

            }




            Menu()
        }
    }
}

@Composable
fun Menu() {

    //NAVEGAÇÃO
    var context = LocalContext.current

    var intentMenu = Intent(context, TelaMenu::class.java)
    var intentPerfil = Intent(context, TelaPerfil::class.java)

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(ScrollState(0))
        .background(Color(0xFFD7D6D6))
    ) {

//        Cabeçalho
    Surface(
        Modifier
            .fillMaxWidth()
            .height(200.dp), // TO-DO tem que ajustar
        color = Color.Cyan
    ) {  }

    // Menu de fato
//      Coluna amarela -> Creio eu que so as Box sejam de cor diferentes, já que isso pensei pra ser o fundo sombreado.
//      Pra dar aquela sensação de icone flutuante
        Column(Modifier.fillMaxSize().weight(1f).background(color = Color.Yellow).padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally)
        {
//            Fila verde, 2 comp.
            Row(Modifier.background(color = Color.Green).fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Box(Modifier.background(Color.Red).weight(1f).height(100.dp).clickable {  }.padding(10.dp),){
//                    Creio ser necessário ajustar isso quando colocar o texto definiito e a imagem
                    Row(){
                        Icon(imageVector = Icons.Rounded.Menu, contentDescription = "MedSync") // TO-DO tem que colocar uma imagem aqui
                        Text(text = "Olá MedSync!",)
                    }
                }
                Spacer(Modifier.width(10.dp))
                Box(Modifier.background(Color.Red).weight(1f).height(100.dp).clickable {  }.padding(10.dp),){
//                    Creio ser necessário ajustar isso quando colocar o texto definiito e a imagem
                    Row(){
                        Icon(imageVector = Icons.Rounded.Menu, contentDescription = "MedSync") // TO-DO tem que colocar uma imagem aqui
                        Text(text = "Olá MedSync!",)
                    }
                }
            }
            Row(Modifier.background(color = Color.Green).fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Box(Modifier.background(Color.Red).weight(1f).height(100.dp).clickable {  }.padding(10.dp),){
//                    Creio ser necessário ajustar isso quando colocar o texto definiito e a imagem
                    Row(){
                        Icon(imageVector = Icons.Rounded.Menu, contentDescription = "MedSync") // TO-DO tem que colocar uma imagem aqui
                        Text(text = "Olá MedSync!",)
                    }
                }
                Spacer(Modifier.width(10.dp))
                Box(Modifier.background(Color.Red).weight(1f).height(100.dp).clickable {  }.padding(10.dp),){
//                    Creio ser necessário ajustar isso quando colocar o texto definiito e a imagem
                    Row(){
                        Icon(imageVector = Icons.Rounded.Menu, contentDescription = "MedSync") // TO-DO tem que colocar uma imagem aqui
                        Text(text = "Olá MedSync!",)
                    }
                }
            }

            Row(Modifier.background(color = Color.Green).fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Box(Modifier.background(Color.Red).fillMaxWidth().height(100.dp).clickable {  }.padding(10.dp),) {
                    Row(){
                        Icon(imageVector = Icons.Rounded.Menu, contentDescription = "MedSync") // TO-DO tem que colocar uma imagem aqui
                        Text(text = "Olá MedSync!",)
                    }
                }
            }



        }   // FIM DO COLUMN DE OPÇÕES
            // SE FOR NECESSÁRIO MAIS CAIXAS, É SÓ COPIAR OS DE CIMA =)




//        barra de navegação bottom

    }


}