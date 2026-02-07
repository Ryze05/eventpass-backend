package com.example.eventpass.controller.admin

import com.example.eventpass.services.AsistenteService
import com.example.eventpass.services.EventoService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.time.LocalDateTime

@Controller
class DashboardController(
    private val eventoService: EventoService,
    private val asistenteService: AsistenteService
) {

    @GetMapping("/admin/dashboard")
    fun dashboard(model: Model): String {

        val eventos = eventoService.findAllVista()
        val asistentes = asistenteService.findAll()
        val ahora = LocalDateTime.now()

        val listaFuturos = eventos.filter { it.fecha.isAfter(ahora) }
        val listaPasados = eventos.filter { it.fecha.isBefore(ahora) }

        model.addAttribute("todosLosEventos", eventos)
        model.addAttribute("listaFuturos", listaFuturos)
        model.addAttribute("listaPasados", listaPasados)
        model.addAttribute("todosLosAsistentes", asistentes)

        model.addAttribute("totalEventos", eventos.size)
        model.addAttribute("totalAsistentes", asistentes.size)
        model.addAttribute("eventosFuturos", listaFuturos.size)
        model.addAttribute("eventosPasados", listaPasados.size)

        model.addAttribute(
            "eventosFuturos",
            eventos.count { it.fecha.isAfter(LocalDateTime.now()) }
        )

        model.addAttribute(
            "eventosPasados",
            eventos.count { it.fecha.isBefore(LocalDateTime.now()) }
        )

        val topEventosData = eventoService.populares(5).map { evento ->
            val conteo = asistentes.count { it.evento?.id == evento.id }
            evento to conteo
        }

        model.addAttribute("topEventos", topEventosData)

        val asistentesPorCategoria = asistentes
            .groupBy { it.evento?.categoria?.nombre ?: "Sin Categoría" }
            .mapValues { it.value.size }

        model.addAttribute("asistentesPorCategoria", asistentesPorCategoria)

        val ultimosAsistentes = asistentes.takeLast(5).reversed()
        model.addAttribute("ultimosAsistentes", ultimosAsistentes)

        return "admin/dashboard"
    }
}