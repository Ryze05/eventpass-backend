package com.example.eventpass.controller.admin

import com.example.eventpass.model.Asistente
import com.example.eventpass.services.AsistenteService
import com.example.eventpass.services.EventoService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/admin/asistentes")
class AsistenteAdminController(
    private val asistenteService: AsistenteService,
    private val eventoService: EventoService
) {

    @GetMapping
    fun listar(model: Model): String {
        model.addAttribute("asistentes", asistenteService.findAll())
        return "admin/asistentes/list"
    }

    @GetMapping("/nuevo")
    fun nuevo(model: Model, request: HttpServletRequest): String {
        model.addAttribute("asistente", Asistente())
        model.addAttribute("eventos", eventoService.findAll())

        model.addAttribute("referer", request.getHeader("Referer"))

        return "admin/asistentes/form"
    }

    @GetMapping("/editar/{id}")
    fun editar(@PathVariable id: Int, model: Model): String {
        model.addAttribute("asistente", asistenteService.findById(id))
        model.addAttribute("eventos", eventoService.findAll())
        return "admin/asistentes/form"
    }

    @PostMapping("/guardar")
    fun guardar(
        asistente: Asistente,
        @RequestParam(required = false) eventoId: Int?,
        request: HttpServletRequest
    ): String {

        if (eventoId != null) {
            asistente.evento = eventoService.findById(eventoId)
        }
        asistenteService.save(asistente)

        val referer = request.getParameter("referer")
        return if (referer != null && referer.contains("/admin/dashboard")) {
            "redirect:/admin/dashboard"
        } else {
            "redirect:/admin/asistentes"
        }
    }

    @GetMapping("/borrar/{id}")
    fun borrar(@PathVariable id: Int, request: HttpServletRequest): String {
        asistenteService.delete(id)

        val referer = request.getHeader("Referer")

        return if (referer != null && referer.contains("/admin/dashboard")) {
            "redirect:/admin/dashboard"
        } else {
            "redirect:/admin/asistentes"
        }
    }
}
