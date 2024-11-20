package com.example.artspace_mohdfirdausbinabdullah

// Clase de datos que define la estructura de cada obra/personaje
// Es data porque se usa para almacenar datos y no para lógica
data class Artwork( 
    val imageResourceId: Int, // ID de la imagen en recursos
    val characterName: String, // Nombre del personaje
    val actorName: String, // Nombre del actor
    val year: Int // Año de la serie
) 