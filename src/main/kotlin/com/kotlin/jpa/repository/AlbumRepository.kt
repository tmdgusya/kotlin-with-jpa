package com.kotlin.jpa.repository

import com.kotlin.jpa.domain.Album
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AlbumRepository : JpaRepository<Album, Long> {

    fun findAlbumByArtist(artist: String): Optional<Album>;

}