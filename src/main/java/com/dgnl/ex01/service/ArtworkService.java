package com.dgnl.ex01.service;

import com.dgnl.ex01.model.Artwork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ArtworkService {
    Page<Artwork> listAll(String keyword, Pageable pageable);

    Artwork save(Artwork artwork);

    Optional<Artwork> findById(Long id);

    void deleteById(Long id);
}

