package com.example.eventpass.services

import com.example.eventpass.model.Ubicacion
import com.example.eventpass.repository.UbicacionRepository
import org.springframework.stereotype.Service

@Service
class UbicacionService(
    private val repo: UbicacionRepository
) {

    fun findAll(): List<Ubicacion> {
        return repo.findAll()
    }

    fun findById(id: Int): Ubicacion {
        return repo.findById(id).orElseThrow()
    }

    fun save(ubicacion: Ubicacion): Ubicacion {
        return repo.save(ubicacion)
    }

    fun delete(id: Int) {
        return repo.deleteById(id)
    }
}
