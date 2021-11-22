package com.kotlin.jpa.domain

import javax.persistence.*

@Entity
class Team(
    name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null;

    var name: String = name;

    @OneToMany(mappedBy = "team", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val members: MutableList<Member> = ArrayList<Member>()

    fun addMember(member: Member) {
        members.add(member);
    }
}