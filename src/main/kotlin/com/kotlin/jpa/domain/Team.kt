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

    @OneToMany(mappedBy = "team")
    val members: List<Member> = ArrayList<Member>()
}