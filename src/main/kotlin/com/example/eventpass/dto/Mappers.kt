package com.example.eventpass.dto

import com.example.eventpass.model.Evento

fun Evento.toDTO() = EventoResponseDTO(
    id = this.id!!,
    titulo = this.titulo,
    descripcion = this.descripcion,
    fecha = this.fecha,
    imagenRes = this.foto,
    categoriaNombre = this.categoria?.nombre,
    ubicacionNombre = this.ubicacion?.nombre
)