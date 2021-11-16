package com.kotlin.jpa.repository

import com.kotlin.jpa.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MemberRepository : JpaRepository<Member, Long> {

    fun findMemberByName(name: String): Optional<Member>

}