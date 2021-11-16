package com.kotlin.jpa.domain

import javax.persistence.*

@Entity
class Team(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String,
    @OneToMany(mappedBy = "team")
    val members: List<Member> = ArrayList<Member>()
) {
}