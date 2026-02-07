package com.example.eventpass.dto

import java.time.LocalDateTime

data class EventoResponseDTO(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val fecha: LocalDateTime,
    val imagenRes: String,
    val categoriaNombre: String?,
    val ubicacionNombre: String?
)