package com.kotlin.jpa.domain

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("ALBUM")
class Album(artist: String) : Item() {
    private var artist: String = artist;
}