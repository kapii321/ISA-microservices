package com.example.playlistservice.initializer;

import com.example.playlistservice.entity.Playlist;
import com.example.playlistservice.repositories.PlaylistBackupRepository;
import com.example.playlistservice.repositories.PlaylistRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PlaylistDataInitializer implements InitializingBean {
    private final PlaylistRepository playlistRepository;
    private final PlaylistBackupRepository playlistBackupRepository;

    @Autowired
    public PlaylistDataInitializer(PlaylistRepository playlistRepository, PlaylistBackupRepository playlistBackupRepository) {
        this.playlistRepository = playlistRepository;
        this.playlistBackupRepository = playlistBackupRepository;
    }

    @Override
    public void afterPropertiesSet() {
        initializePlaylists();
    }

    private void initializePlaylists() {
        Playlist playlist1 = Playlist.builder()
                .id(UUID.fromString("1d9c5400-a8fa-4e22-8453-d5d08c74eaf5"))
                .name("Chill Tunes")
                .rank(1)
                .build();

        Playlist playlist2 = Playlist.builder()
                .id(UUID.fromString("2f97c370-f8ea-42d7-8769-d8f1f1c2ef68"))
                .name("Workout Beats")
                .rank(2)
                .build();

        playlistRepository.save(playlist1);
        playlistBackupRepository.save(playlist1);

        playlistRepository.save(playlist2);
        playlistBackupRepository.save(playlist2);
    }
}
