package com.example.eventpass.controller.admin

import com.example.eventpass.model.Categoria
import com.example.eventpass.services.CategoriaService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin/categorias")
class CategoriaAdminController(
    private val categoriaService: CategoriaService
) {
    @GetMapping
    fun listar(model: Model): String {
        model.addAttribute("categorias", categoriaService.findAll())
        return "admin/categorias/list"
    }

    @GetMapping("/nuevo")
    fun nuevo(model: Model, request: HttpServletRequest): String {
        model.addAttribute("categoria", Categoria())

        model.addAttribute("referer", request.getHeader("Referer"))

        return "admin/categorias/form"
    }

    @GetMapping("/editar/{id}")
    fun editar(@PathVariable id: Int, model: Model): String {
        model.addAttribute("categoria", categoriaService.findById(id))
        return "admin/categorias/form"
    }

    @PostMapping("/guardar")
    fun guardar(categoria: Categoria, request: HttpServletRequest): String {
        categoriaService.save(categoria)
        val referer = request.getParameter("referer")
        return if (referer != null && referer.contains("/admin/dashboard")){
            "redirect:/admin/dashboard"
        } else {
            "redirect:/admin/categorias"
        }
    }

    @GetMapping("/borrar/{id}")
    fun borrar(@PathVariable id: Int): String {
        categoriaService.delete(id)
        return "redirect:/admin/categorias"
    }
}
