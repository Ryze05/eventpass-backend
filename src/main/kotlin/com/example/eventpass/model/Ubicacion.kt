package com.example.eventpass.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "ubicaciones")
data class Ubicacion(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ubicacion")
    val id: Int? = null,

    val nombre: String = "",
    val direccion: String = "",
    val capacidad: Int = 0
)
