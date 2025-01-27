package com.example.playlistservice.services;

import com.example.playlistservice.entity.Playlist;
import com.example.playlistservice.repositories.PlaylistBackupRepository;
import com.example.playlistservice.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistBackupRepository playlistBackupRepository;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository, PlaylistBackupRepository playlistBackupRepository) {
        this.playlistRepository = playlistRepository;
        this.playlistBackupRepository = playlistBackupRepository;
    }

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Optional<Playlist> getPlaylistById(UUID id) {
        return playlistRepository.findById(id);
    }

    public void createPlaylist(Playlist playlist) {
        if(playlist.getId() == null){
            playlist.setId(UUID.randomUUID());
        }
        playlistRepository.save(playlist);
        playlistBackupRepository.save(playlist);

    }
    public void deletePlaylist(UUID id) {
        playlistRepository.findById(id).ifPresent(playlistRepository::delete);
        playlistBackupRepository.findById(id).ifPresent(playlistBackupRepository::delete);
    }

    public void updatePlaylist(Playlist playlist) {
        playlistRepository.save(playlist);
        playlistBackupRepository.save(playlist);
    }

    public void restoreFromBackup() {
        List<Playlist> backupPlaylists = playlistBackupRepository.findAll();
        playlistRepository.saveAll(backupPlaylists);
    }



}
