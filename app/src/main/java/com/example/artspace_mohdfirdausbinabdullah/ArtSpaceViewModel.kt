package com.example.artspace_mohdfirdausbinabdullah

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ArtSpaceViewModel : ViewModel() {
    private var _currentArtworkIndex = mutableStateOf(0)
    val currentArtworkIndex: State<Int> = _currentArtworkIndex

    // Lista de personajes favoritos movida desde MainActivity
    val artworkList = listOf(
        Artwork(R.drawable.hannibal_lecter, "Hannibal Lecter", "Mads Mikkelsen", 2013),
        Artwork(R.drawable.gus_fring, "Breaking Bad", "Giancarlo Esposito", 2008),
        Artwork(R.drawable.daryl_dixon, "The Walking Dead", "Norman Reedus", 2010)
    )

    // Función para ir al personaje anterior
    fun previousArtwork() {
        _currentArtworkIndex.value = if (_currentArtworkIndex.value > 0) {
            _currentArtworkIndex.value - 1
        } else {
            artworkList.size - 1
        }
    }
    // Función para ir al siguiente personaje
    fun nextArtwork() {
        _currentArtworkIndex.value = if (_currentArtworkIndex.value < artworkList.size - 1) {
            _currentArtworkIndex.value + 1
        } else {
            0
        }
    }
} 