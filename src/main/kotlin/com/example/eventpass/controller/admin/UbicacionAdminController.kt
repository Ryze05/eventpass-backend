package com.example.eventpass.controller.admin

import com.example.eventpass.model.Ubicacion
import com.example.eventpass.services.UbicacionService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin/ubicaciones")
class UbicacionAdminController(
    private val ubicacionService: UbicacionService
) {

    @GetMapping
    fun listar(model: Model): String {
        model.addAttribute("ubicaciones", ubicacionService.findAll())
        return "admin/ubicaciones/list"
    }

    @GetMapping("/nuevo")
    fun nuevo(model: Model, request: HttpServletRequest): String {
        model.addAttribute("ubicacion", Ubicacion())

        model.addAttribute("referer", request.getHeader("Referer"))

        return "admin/ubicaciones/form"
    }

    @GetMapping("/editar/{id}")
    fun editar(@PathVariable id: Int, model: Model): String {
        model.addAttribute("ubicacion", ubicacionService.findById(id))
        return "admin/ubicaciones/form"
    }

    @PostMapping("/guardar")
    fun guardar(ubicacion: Ubicacion, request: HttpServletRequest): String {
        ubicacionService.save(ubicacion)
        val referer = request.getParameter("referer")
        return if (referer != null && referer.contains("/admin/dashboard")) {
            "redirect:/admin/dashboard"
        } else {
            "redirect:/admin/ubicaciones"
        }
    }

    @GetMapping("/borrar/{id}")
    fun borrar(@PathVariable id: Int): String {
        ubicacionService.delete(id)
        return "redirect:/admin/ubicaciones"
    }
}
