package com.example.demo.services;

import com.example.demo.entity.Song;
import com.example.demo.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SongService {

    private final SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Optional<Song> findByID(UUID id) {
        return songRepository.findById(id);
    }

    public List<Song> findAll() {
        return songRepository.findAll();
    }

    public void create(Song song) {
        song.setId(UUID.randomUUID());
        songRepository.save(song);
    }

    public void delete(UUID id) {
        songRepository.findById(id).ifPresent(songRepository::delete);
    }

    public void update(Song song) {
        songRepository.save(song);
    }
}
