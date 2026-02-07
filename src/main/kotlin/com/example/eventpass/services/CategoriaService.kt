package com.example.eventpass.services

import com.example.eventpass.model.Categoria
import com.example.eventpass.repository.CategoriaRepository
import org.springframework.stereotype.Service

@Service
class CategoriaService(
    private val repo: CategoriaRepository
) {

    fun findAll(): List<Categoria> {
        return repo.findAll()
    }

    fun findById(id: Int): Categoria {
        return repo.findById(id).orElseThrow()
    }

    fun save(categoria: Categoria): Categoria {
        return repo.save(categoria)
    }

    fun delete(id: Int) {
        return repo.deleteById(id)
    }
}
