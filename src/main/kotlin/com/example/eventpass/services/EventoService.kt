package com.example.eventpass.services

import com.example.eventpass.model.Evento
import com.example.eventpass.repository.EventoRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class EventoService(
    private val repo: EventoRepository
) {

    fun findAll(): List<Evento> {
        return repo.findAllActuales(LocalDateTime.now())
    }

    fun findAllVista(): List<Evento> {
        return repo.findAll(Sort.by(Sort.Direction.ASC, "fecha"))
    }

    fun findById(id: Int): Evento {
        return repo.findById(id).orElseThrow()
    }

    fun save(evento: Evento): Evento {
        return repo.save(evento)
    }

    fun delete(id: Int) {
        return repo.deleteById(id)
    }

    fun proximos(limit: Int): List<Evento> {
        return repo.getProximos(LocalDateTime.now(), PageRequest.of(0,limit))
    }

    fun porCategoria(id: Int): List<Evento> {
        return repo.getPorCategoria(id, LocalDateTime.now())
    }

    fun populares(limit: Int): List<Evento> {
        return repo.getPopulares(LocalDateTime.now(),PageRequest.of(0, limit))
    }
}
