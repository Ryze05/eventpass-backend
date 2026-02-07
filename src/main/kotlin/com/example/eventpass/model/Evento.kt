package com.example.eventpass.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "eventos")
data class Evento(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    val id: Int? = null,

    val titulo: String = "",

    @Column(columnDefinition = "TEXT")
    val descripcion: String = "",

    val fecha: LocalDateTime,

    @ManyToOne
    @JoinColumn(name = "id_ubicacion")
    var ubicacion: Ubicacion?,

    @Column(name = "imagen_res")
    val foto: String = "default_event.jpg",

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    var categoria: Categoria?
)

