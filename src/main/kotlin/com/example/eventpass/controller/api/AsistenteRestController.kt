package com.example.eventpass.controller.api

import com.example.eventpass.dto.AsistentePostDTO
import com.example.eventpass.model.Asistente
import com.example.eventpass.services.AsistenteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/asistentes")
class AsistenteRestController(
    private val asistenteService: AsistenteService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun crear(@RequestBody asistentePost: AsistentePostDTO): ResponseEntity<Asistente> {
        return try {
            val nuevo = asistenteService.registrarAsistente(asistentePost)
            ResponseEntity.status(HttpStatus.CREATED).body(nuevo)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }
}