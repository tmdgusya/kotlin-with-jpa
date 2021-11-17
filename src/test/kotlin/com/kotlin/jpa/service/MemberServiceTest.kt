package com.kotlin.jpa.service

import com.kotlin.jpa.domain.Member
import com.kotlin.jpa.repository.MemberRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional

@SpringBootTest
@Transactional
internal class MemberServiceTest {

    @Autowired
    private lateinit var memberService: MemberService;

    @Autowired
    private lateinit var memberRepository: MemberRepository;

    @Test
    @DisplayName("멤버가 생성될때 CreateAt 과 UpdatedAt 및 입력한 값들이 정확히 들어가는지 확인합니다.")
    fun addMember() {
        //given
        val testMemberName = "Roach";
        memberService.addMember(Member(testMemberName))

        //when
        val roach: Member = memberRepository.findMemberByName(testMemberName).orElseThrow();

        //then
        assertEquals(testMemberName, roach.name);
        assertNotNull(roach.createdAt);
        assertNotNull(roach.updatedAt);
    }

    @Test
    @DisplayName("변경 감지 기능을 통해 Member 의 이름이 Update 되는지 확인합니다.")
    fun updateMemberName() {
        //given
        val testMemberName = "Roach";
        val changedMemberName = "Dodo";
        memberService.addMember(Member(testMemberName));
        val roach: Member = memberRepository.findMemberByName(testMemberName).orElseThrow();

        //when
        memberService.updateMemberInfo(roach, changedMemberName);

        //then
        assertEquals(changedMemberName, roach.name);
    }

}