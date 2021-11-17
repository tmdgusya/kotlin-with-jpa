package com.kotlin.jpa.service

import com.kotlin.jpa.domain.Member
import com.kotlin.jpa.repository.MemberRepository
import com.kotlin.jpa.repository.TeamRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    val memberRepository: MemberRepository,
    val teamRepository: TeamRepository
) {

    fun addMember(member: Member) {
        memberRepository.save(member);
    }

    fun updateMemberInfo(member: Member, changedMemberName: String) {
        member.name = changedMemberName;
        memberRepository.save(member);
    }

}