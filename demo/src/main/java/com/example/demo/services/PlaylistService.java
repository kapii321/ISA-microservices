package com.example.demo.services;

import com.example.demo.entity.Playlist;
import com.example.demo.entity.Song;
import com.example.demo.repositories.PlaylistRepository;
import com.example.demo.repositories.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
        playlist.setId(UUID.randomUUID());
        playlistRepository.save(playlist);
    }
    public void deletePlaylist(UUID id) {
        playlistRepository.findById(id).ifPresent(playlistRepository::delete);
    }

    public void updatePlaylist(Playlist playlist) {
        playlistRepository.save(playlist);
    }

    public List<Song> getSongsInPlaylist(UUID id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found: " + id));
        return playlist.getSongs();
    }




}
