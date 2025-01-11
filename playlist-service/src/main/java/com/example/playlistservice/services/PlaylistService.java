package com.example.playlistservice.services;

import com.example.playlistservice.entity.Playlist;
import com.example.playlistservice.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
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
    }
    public void deletePlaylist(UUID id) {
        playlistRepository.findById(id).ifPresent(playlistRepository::delete);
    }

    public void updatePlaylist(Playlist playlist) {
        playlistRepository.save(playlist);
    }



}
