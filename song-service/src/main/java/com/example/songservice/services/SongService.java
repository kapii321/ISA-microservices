package com.example.songservice.services;

import com.example.songservice.entity.Song;
import com.example.songservice.repositories.SongBackupRepository;
import com.example.songservice.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final SongBackupRepository songBackupRepository;
    @Autowired
    public SongService(SongRepository songRepository, SongBackupRepository songBackupRepository) {
        this.songRepository = songRepository;
        this.songBackupRepository = songBackupRepository;
    }

    public Optional<Song> findByID(UUID id) {
        return songRepository.findById(id);
    }

    public List<Song> findAll() {
        return songRepository.findAll();
    }

    public void create(Song song) {
        if(song.getId() == null) {
            song.setId(UUID.randomUUID());
        }
        songRepository.save(song);
        songBackupRepository.save(song);
    }

    public void delete(UUID id) {
        songRepository.findById(id).ifPresent(songRepository::delete);
        songBackupRepository.findById(id).ifPresent(songBackupRepository::delete);
    }

    public void update(Song song) {
        songRepository.save(song);
        songBackupRepository.save(song);
    }

    public List<Song> findByPlaylistId(UUID playlistId) {
        return songRepository.findByPlaylistId(playlistId);
    }

    public void restoreFromBackup() {
        List<Song> backupSongs = songBackupRepository.findAll();
        songRepository.saveAll(backupSongs);
    }
}
