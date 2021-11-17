package com.kotlin.jpa.domain

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("ALBUM")
class Album(artist: String, name: String, price: Int) : Item(name, price) {
    private var artist: String = artist;

    public fun getArtist(): String {
        return this.artist;
    }
}