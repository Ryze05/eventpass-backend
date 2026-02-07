package com.example.eventpass.controller.admin

import com.example.eventpass.model.Evento
import com.example.eventpass.services.AsistenteService
import com.example.eventpass.services.CategoriaService
import com.example.eventpass.services.EventoService
import com.example.eventpass.services.UbicacionService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Controller
@RequestMapping("/admin/eventos")
class EventoAdminController(
    private val eventoService: EventoService,
    private val ubicacionService: UbicacionService,
    private val categoriaService: CategoriaService,
    private val asistenteService: AsistenteService
) {
    @GetMapping
    fun listar(model: Model): String {
        model.addAttribute("eventos", eventoService.findAllVista())
        model.addAttribute("todosLosAsistentes", asistenteService.findAll())
        return "admin/eventos/list"
    }

    @GetMapping("/nuevo")
    fun nuevo(model: Model, request: HttpServletRequest): String {
        val evento = Evento(
            fecha = LocalDateTime.now(),
            ubicacion = null,
            categoria = null
        )

        model.addAttribute("evento", evento)
        model.addAttribute("ubicaciones", ubicacionService.findAll())
        model.addAttribute("categorias", categoriaService.findAll())
        model.addAttribute("fotos", listOf("concierto.png", "conferencia.png", "exposicion.png", "festival.jpg", "fiesta.png", "teatro.jpg"))

        val fechaString = evento.fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
        model.addAttribute("fechaString", fechaString)

        model.addAttribute("referer", request.getHeader("Referer"))

        return "admin/eventos/form"
    }

    @GetMapping("/editar/{id}")
    fun editar(@PathVariable id: Int, model: Model): String {
        val evento = eventoService.findById(id)
        model.addAttribute("evento", evento)
        model.addAttribute("ubicaciones", ubicacionService.findAll())
        model.addAttribute("categorias", categoriaService.findAll())
        model.addAttribute("fotos", listOf("concierto.png", "conferencia.png", "exposicion.png", "festival.jpg", "fiesta.png", "teatro.jpg"))

        val fechaString = evento.fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
        model.addAttribute("fechaString", fechaString)

        return "admin/eventos/form"
    }

    @PostMapping("/guardar")
    fun guardar(evento: Evento, ubicacionId: Int, categoriaId: Int, request: HttpServletRequest): String {
        evento.ubicacion = ubicacionService.findById(ubicacionId)
        evento.categoria = categoriaService.findById(categoriaId)

        eventoService.save(evento)
        val referer = request.getParameter("referer")

        return if (referer != null && referer.contains("/admin/dashboard")) {
            "redirect:/admin/dashboard"
        } else {
            "redirect:/admin/eventos"
        }
    }

    @GetMapping("/borrar/{id}")
    fun borrar(@PathVariable id: Int): String {
        eventoService.delete(id)
        return "redirect:/admin/eventos"
    }
}
