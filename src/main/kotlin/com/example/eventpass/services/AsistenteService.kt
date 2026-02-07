package com.example.eventpass.services

import com.example.eventpass.dto.AsistentePostDTO
import com.example.eventpass.model.Asistente
import com.example.eventpass.repository.AsistenteRepository
import org.springframework.stereotype.Service

@Service
class AsistenteService(
    private val repo: AsistenteRepository,
    private val eventoService: EventoService
) {

    fun findAll(): List<Asistente> {
        return repo.findAll()
    }

    fun findById(id: Int): Asistente {
        return repo.findById(id).orElseThrow()
    }

    fun save(asistente: Asistente): Asistente {
        return repo.save(asistente)
    }

    fun delete(id: Int) {
        return repo.deleteById(id)
    }

    fun registrarAsistente(asistentePost: AsistentePostDTO): Asistente {
        val evento = eventoService.findById(asistentePost.eventoId)

        val asistente = Asistente(
            nombre = asistentePost.nombre,
            email = asistentePost.email,
            telefono = asistentePost.telefono,
            evento = evento
        )

        return repo.save(asistente)
    }
}
