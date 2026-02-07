package com.example.eventpass.repository

import com.example.eventpass.model.Evento
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface EventoRepository : JpaRepository<Evento, Int> {

    @Query(
        """
        SELECT e FROM Evento e
        WHERE e.fecha >= :now
        ORDER BY e.fecha ASC
    """
    )
    fun getProximos(now: LocalDateTime, pageable: Pageable): List<Evento>

    @Query(
        """
        SELECT e FROM Evento e
        WHERE e.categoria.id = :id
        AND
        e.fecha >= :now
        ORDER BY e.fecha ASC
    """
    )

    fun getPorCategoria(id: Int, now: LocalDateTime): List<Evento>

    @Query(
        """
        SELECT e FROM Evento e 
        LEFT JOIN Asistente a ON a.evento = e 
        WHERE e.fecha >= :now
        GROUP BY e 
        ORDER BY COUNT(a) DESC, e.id ASC
    """
    )
    fun getPopulares(now: LocalDateTime, pageable: Pageable): List<Evento>

    @Query(
        """
        SELECT e FROM Evento e
        WHERE e.fecha >= :now
        ORDER BY e.fecha ASC
    """
    )
    fun findAllActuales(now: LocalDateTime): List<Evento>
}