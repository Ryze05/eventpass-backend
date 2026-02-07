package com.example.eventpass.repository

import com.example.eventpass.model.Asistente
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AsistenteRepository : JpaRepository<Asistente, Int>
