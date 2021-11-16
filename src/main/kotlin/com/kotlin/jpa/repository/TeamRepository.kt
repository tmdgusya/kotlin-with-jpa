package com.kotlin.jpa.repository

import com.kotlin.jpa.domain.Team
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TeamRepository : JpaRepository<Team, Long> {
}