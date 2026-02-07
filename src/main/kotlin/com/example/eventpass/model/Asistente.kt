package com.example.eventpass.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table


@Entity
@Table(name = "asistentes")
data class Asistente(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistente")
    val id: Int? = null,

    val nombre: String = "",
    val email: String = "",
    val telefono: String = "",

    @ManyToOne
    @JoinColumn(name = "id_evento")
    var evento: Evento? = null
)
