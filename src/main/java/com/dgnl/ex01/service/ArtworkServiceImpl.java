package com.dgnl.ex01.service;

import com.dgnl.ex01.model.Artwork;
import com.dgnl.ex01.repository.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ArtworkServiceImpl implements ArtworkService {
    private final ArtworkRepository repository;

    @Autowired
    public ArtworkServiceImpl(ArtworkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Artwork> listAll(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return repository.findAll(pageable);
        }
        String k = keyword.trim();
        return repository.findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCase(k, k, pageable);
    }

    @Override
    public Artwork save(Artwork artwork) {
        return repository.save(artwork);
    }

    @Override
    public Optional<Artwork> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

