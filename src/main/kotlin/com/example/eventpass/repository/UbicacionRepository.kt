package com.example.eventpass.repository

import com.example.eventpass.model.Ubicacion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UbicacionRepository : JpaRepository<Ubicacion, Int>