package com.example.eventpass.controller.api

import com.example.eventpass.dto.EventoResponseDTO
import com.example.eventpass.dto.toDTO
import com.example.eventpass.model.Evento
import com.example.eventpass.services.EventoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/eventos")
class EventoRestController(
    private val eventoService: EventoService
) {
    @GetMapping
    fun todosEventos(@RequestParam(required = false) categoria: Int?): ResponseEntity<List<EventoResponseDTO>> {
        return try {
            val eventos = (categoria?.let {
                eventoService.porCategoria(it)
            } ?: eventoService.findAll()).map { it.toDTO() }

            if (eventos.isEmpty()) {
                ResponseEntity.noContent().build()
            } else {
                ResponseEntity.ok(eventos)
            }

        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GetMapping("/proximos")
    fun proximosEventos(@RequestParam(defaultValue = "5") limit: Int): ResponseEntity<List<EventoResponseDTO>> {
        return try {
            val proximos = eventoService.proximos(limit).map { it.toDTO() }
            ResponseEntity.ok(proximos)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GetMapping("/populares")
    fun populares(@RequestParam(defaultValue = "5") limit: Int): ResponseEntity<List<EventoResponseDTO>> {
        return try {
            val populares = eventoService.populares(limit).map { it.toDTO() }
            ResponseEntity.ok(populares)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GetMapping("/{id}")
    fun detalleEvento(@PathVariable id: Int): ResponseEntity<EventoResponseDTO> {
        return try {
            val evento = eventoService.findById(id).toDTO()
            ResponseEntity.ok(evento)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }
}