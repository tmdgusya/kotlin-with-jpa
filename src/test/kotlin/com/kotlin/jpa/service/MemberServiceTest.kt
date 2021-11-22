package com.kotlin.jpa.service

import com.kotlin.jpa.domain.Member
import com.kotlin.jpa.domain.Team
import com.kotlin.jpa.repository.MemberRepository
import com.kotlin.jpa.repository.TeamRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Propagation
import javax.persistence.EntityManager
import javax.persistence.FlushModeType
import javax.persistence.PersistenceContext
import javax.transaction.Transactional
import kotlin.IllegalArgumentException

@SpringBootTest
internal class MemberServiceTest {

    @Autowired
    private lateinit var memberService: MemberService;

    @Autowired
    private lateinit var memberRepository: MemberRepository;

    @Autowired
    private lateinit var teamRepository: TeamRepository;

    @PersistenceContext
    private lateinit var entityManager: EntityManager;

    @AfterEach
    fun setUp() {
        memberRepository.deleteAll();
        teamRepository.deleteAll();
    }

    @Test
    @DisplayName("멤버가 생성될때 CreateAt 과 UpdatedAt 및 입력한 값들이 정확히 들어가는지 확인합니다.")
    fun addMember() {
        //given
        val testMemberName = "Roach";
        memberService.addMember(Member(testMemberName))

        //when
        val roach: Member = (memberRepository
            .findMemberByName(testMemberName)
            ?: throw NoSuchElementException("$testMemberName Not Found")) as Member;

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
        val roach: Member = (memberRepository.findMemberByName(testMemberName)
            ?: throw NoSuchElementException("$testMemberName Not Found"));

        //when
        memberService.updateMemberInfo(roach, changedMemberName);

        //then
        assertEquals(changedMemberName, roach.name);
    }

    @Test
    @DisplayName("Team 설정을 통해 Member 의 Team 의 외래키가 잘 저장되는지 확인한다.")
    fun setRelationTeamAndMembers() {
        //given
        val testMemberName = "Roach";
        val teamName= "Blue"
        val team = Team(teamName);
        teamRepository.save(team);
        memberService.addMember(Member(testMemberName, team));
        val roach: Member = (memberRepository
            .findMemberByName(testMemberName)
            ?: throw NoSuchElementException("$testMemberName Not Found"));

        //when
        val roachTeam = roach.team;

        //then
        assertEquals(teamName, roachTeam?.name);
    }

    @Test
    @DisplayName("Entity 를 생성만 했을때는 Persistence Context 에 관리되지 않습니다.")
    fun notExistInPCWhenEntityInitiatedImmediately() {
        val testMemberName = "Roach";
        val member: Member = Member(testMemberName);

        assertThrows(NoSuchElementException::class.java) {
            memberRepository
                .findMemberByName(testMemberName)
                ?.let {
                    println(it)
                }
                ?: throw NoSuchElementException("$testMemberName Not Found")
        };

    }

    @Test
    @DisplayName("EntityManager 에 Persist 하게 되면 PC 에 의해 관리됩니다.")
    @Transactional
    fun entityManagedPC() {
        //given
        val testMemberName = "Roach";
        val member: Member = Member(testMemberName);

        //when
        entityManager.persist(member);
        val entity = entityManager.find(Member::class.java, member.id);

        //then
        assertNotNull(entity);
        assertEquals(testMemberName, entity.name)
    }

    @Test
    @DisplayName("EntityManager 에서 detach 되면 PC 에 의해 관리되지 않습니다.")
    @org.springframework.transaction.annotation.Transactional
    fun entityNotManagedPCIfDetachedFromPC() {
        //given
        val testMemberName = "Roach";
        val dirtyName = "dodo"
        var member: Member = Member(testMemberName);

        //when
        entityManager.persist(member);
        member = entityManager.find(Member::class.java, member.id);
        entityManager.detach(member);

        member.name = dirtyName

        entityManager.flush();

        //then
        assertEquals(testMemberName, entityManager.find(Member::class.java, member.id).name)
        assertFalse(entityManager.contains(member));
    }

    @Test
    @DisplayName("Team 을 저장할때 Member 또한 영속화되서 같이 저장되는지 확인합니다.")
    fun persistBothParentAndChild() {
        //given
        val testMemberName = "Roach";
        val teamName = "BlueTeam"
        var member: Member = Member(testMemberName);
        val team: Team = Team(teamName);

        //when
        team.addMember(member);
        member.team = team
        val savedTeam = teamRepository.save(team);

        //then
        val findMemberByName = memberRepository
            .findMemberByName(testMemberName)
            ?: throw IllegalArgumentException("잘못된 이름입니다.");
        assertNotNull(findMemberByName);
        assertEquals(savedTeam.id, findMemberByName.team?.id ?: -1)
    }

    @Test
    @DisplayName("Team 을 저장할때 Member 또한 영속화되서 같이 저장되는지 확인합니다.")
    fun replicateBothParentAndChild() {
        //given
        val testMemberName = "Roach";
        val teamName = "BlueTeam"
        var member: Member = Member(testMemberName);
        val team: Team = Team(teamName);
        val changeName = "DODO";

        //when
        team.addMember(member);
        member.team = team
        val savedTeam = teamRepository.save(team);

        savedTeam.members[0].name = changeName
        teamRepository.save(team);

        //then
        val findMemberByName = memberRepository
            .findMemberByName(changeName)
            ?: throw IllegalArgumentException("잘못된 이름입니다.");
        assertNotNull(findMemberByName);
        assertEquals(savedTeam.id, findMemberByName.team?.id ?: -1)
        assertEquals(changeName, findMemberByName.name)
    }

}