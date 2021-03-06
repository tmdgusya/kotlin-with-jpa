package com.kotlin.jpa.domain

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Member(
        name: String,
    ) {

    constructor(name: String, team: Team) : this(name) {
        this.name = name;
        this.team = team;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null;

    var name: String = name;

    @CreationTimestamp
    lateinit var createdAt: LocalDateTime

    @UpdateTimestamp
    lateinit var updatedAt: LocalDateTime

    @ManyToOne
    @JoinColumn(name = "team_id")
    var team: Team? = null
}