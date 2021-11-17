package com.kotlin.jpa.`supertype-test`

import com.kotlin.jpa.domain.Album
import com.kotlin.jpa.repository.AlbumRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional

import org.junit.jupiter.api.Assertions.*

@SpringBootTest
internal class SuperTypeTest {

    @Autowired
    private lateinit var albumRepository: AlbumRepository;

    @Test
    @DisplayName("ALBUM Entity 를 저장하고 조회쿼리를 확인한다.")
    fun superTypeTest() {
        //given
        val ARTIST_NAME = "ROACH";
        val ITEM_NAME = "ROACH_ROCK";
        val PRICE = 3000;
        val album: Album = Album(ARTIST_NAME, ITEM_NAME, PRICE);

        //when
        albumRepository.save(album);

        //then
        val artist = albumRepository.findAlbumByArtist(ARTIST_NAME).orElseThrow();
        assertEquals(artist.getArtist(), ARTIST_NAME);
    }

}