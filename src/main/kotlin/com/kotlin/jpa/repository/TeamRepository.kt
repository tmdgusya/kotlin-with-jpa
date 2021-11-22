package com.kotlin.jpa.repository

import com.kotlin.jpa.domain.Team
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TeamRepository : JpaRepository<Team, Long> {

    fun findTeamByName(name: String): Team?

}