package com.example.artspace_mohdfirdausbinabdullah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace_mohdfirdausbinabdullah.ui.theme.FirArtSpaceTheme
import androidx.lifecycle.viewmodel.compose.viewModel

/*Esto es una aplicación que muestra una galería de personajes de la serie Hannibal, Breaking Bad y The Walking Dead

Para crearlo hemos usado:
- Column para organizar los elementos en una columna
- Card para crear las tarjetas con las imágenes y las descripciones
- Button para crear los botones de navegación
- Spacer para añadir espacio entre los elementos
- Modifier para darles estilo a los elementos*/

// Actividad principal que inicia la aplicación
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita el diseño de borde a borde, es decir, que la pantalla se vea como una ventana
        setContent {
            FirArtSpaceTheme {
                // Scaffold proporciona la estructura básica de la UI, se le pasa un modifier para que ocupe todo el tamaño
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Se le pasa el innerPadding para que se pueda usar en el resto del código
                    ArtSpaceApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Composable principal que organiza toda la galería
@Composable
// Esta función es el composable principal que organiza toda la galería, 
// modifier: Modifier = Modifier es un parámetro opcional que se le pasa al composable para que se pueda usar en el resto del código,
// es opcional porque se le puede pasar un modifier para darles estilo
fun ArtSpaceApp(modifier: Modifier = Modifier) {
    // Inicializamos el ViewModel
    val viewModel: ArtSpaceViewModel = viewModel()
    
    // Obtenemos el estado del ViewModel
    val currentArtworkIndex = viewModel.currentArtworkIndex.value
    val currentArtwork = viewModel.artworkList[currentArtworkIndex]

    // Columna principal que contiene todos los elementos
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Tarjeta con la imagen del personaje, se le pasa el personaje actual y se le da un weight de 1 para que ocupe todo el ancho
        ArtImageCard(
            artwork = currentArtwork, // si artwork es igual a currentArtwork, se muestra la imagen del personaje actual
            modifier = Modifier
                .weight(1f) // se le da un weight de 1 para que ocupe todo el ancho
                .fillMaxWidth() // se le da un fillMaxWidth para que ocupe todo el ancho
        )
        Spacer(modifier = Modifier.height(16.dp)) // se añade un espacio vertical de 16dp
        // Descripción del personaje y actor
        ArtworkDescription(artwork = currentArtwork) // se le pasa el personaje actual y se muestra su descripción
        Spacer(modifier = Modifier.height(16.dp)) // se añade un espacio vertical de 16dp
        // Botones de navegación con lógica circular
        NavigationButtons(
            // Lógica para ir al personaje anterior
            onPreviousClick = { viewModel.previousArtwork() },
            // Lógica para ir al siguiente personaje
            onNextClick = { viewModel.nextArtwork() }
        )
    }
}

// Composable que muestra la imagen del personaje en una tarjeta elevada
@Composable
// Esta función es el composable que muestra la imagen del personaje en una tarjeta elevada, 
// artwork: Artwork es el personaje que se va a mostrar, 
// modifier: Modifier se pone para que se pueda usar en el resto del código, nos ayuda a darle estilo
fun ArtImageCard(artwork: Artwork, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        // elevation es la elevación de la tarjeta
        // CardDefaults.cardElevation es una función que devuelve un valor de elevación de tarjeta
        // se le da un defaultElevation de 8dp para que se vea más elevada
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Image(
            painter = painterResource(id = artwork.imageResourceId),
            // contentDescription es una descripción de la imagen para que los lectores de pantalla puedan entender lo que es
            contentDescription = artwork.characterName,
            // contentScale es el modo de escalado de la imagen para que se ajuste al tamaño de la tarjeta
            contentScale = ContentScale.Fit,
            // Modifier es un modificador para la imagen, se le da un fillMaxSize para que ocupe todo el tamaño de la tarjeta
            // y se le añade un padding de 16dp para que haya un espacio entre la imagen y el borde de la tarjeta
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}

// Composable que muestra la información del personaje y actor
@Composable
fun ArtworkDescription(artwork: Artwork, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Nombre del personaje
            Text(
                text = "${artwork.characterName} (${artwork.year})",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            // Nombre del actor y año
            Text(
                // Se usa el simbolo del $ para que se pueda usar el valor de la variable artwork.year, ya que es un entero, 
                // si no usamos el $ no se podría usar el valor de la variable porque se confundiría con una cadena de texto
                text = artwork.actorName,
                fontSize = 16.sp
            )
        }
    }
}

// Composable para los botones de navegación
@Composable
fun NavigationButtons(
    onPreviousClick: () -> Unit, // Función que se ejecuta cuando se pulsa el botón anterior
    onNextClick: () -> Unit, // Función que se ejecuta cuando se pulsa el botón siguiente
    modifier: Modifier = Modifier // Modificador para los botones
) {
    Row(
        modifier = modifier.fillMaxWidth(), // se le da un fillMaxWidth para que ocupe todo el ancho
        horizontalArrangement = Arrangement.SpaceEvenly // se le da un espacio uniforme entre los botones
    ) {
        // Botón "Anterior"
        Button(
            onClick = onPreviousClick,
            modifier = Modifier
                .weight(1f) 
                .padding(horizontal = 4.dp) 
        ) {
            Text("Previous") // se le da un texto al botón
        }
        // Botón "Siguiente"
        Button(
            onClick = onNextClick,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp)
        ) {
            Text("Next") // se le da un texto al botón
        }
    }
}

// Vista previa para el diseñador de Android Studio
@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    FirArtSpaceTheme {
        ArtSpaceApp()
    }
}