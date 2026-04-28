package com.dgnl.ex01.repository;

import com.dgnl.ex01.model.Artwork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    Page<Artwork> findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCase(String title, String artist, Pageable pageable);
}

