package com.example.playlistservice.initializer;

import com.example.playlistservice.entity.Playlist;
import com.example.playlistservice.services.PlaylistEventService;
import com.example.playlistservice.services.PlaylistService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PlaylistDataInitializer implements InitializingBean {
    private final PlaylistService playlistService;
    private final PlaylistEventService eventService;

    @Autowired
    public PlaylistDataInitializer(PlaylistService playlistService, PlaylistEventService eventService) {
        this.playlistService = playlistService;
        this.eventService = eventService;
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

        // Create playlists and notify song service
        playlistService.createPlaylist(playlist1);
        eventService.notifyPlaylistCreated(playlist1.getId(), playlist1.getName());

        playlistService.createPlaylist(playlist2);
        eventService.notifyPlaylistCreated(playlist2.getId(), playlist2.getName());
    }
}
