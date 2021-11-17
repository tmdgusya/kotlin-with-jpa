package com.kotlin.jpa.domain

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("MOVIE")
class Movie(director: String, actor: String) : Item() {
    private var director: String = director;
    private var actor: String = actor;
}