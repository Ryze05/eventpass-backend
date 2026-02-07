package com.example.eventpass.controller.api

import com.example.eventpass.model.Categoria
import com.example.eventpass.services.CategoriaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/categorias")
class CategoriaRestController(
    private val categoriaService: CategoriaService
) {
    @GetMapping
    fun todasCategorias(): ResponseEntity<List<Categoria>> {
        return try {
            val categorias = categoriaService.findAll()
            if (categorias.isEmpty()) {
                ResponseEntity.noContent().build()
            } else {
                ResponseEntity.ok(categorias)
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }
}