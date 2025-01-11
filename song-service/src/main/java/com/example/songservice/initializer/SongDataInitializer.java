package com.example.songservice.initializer;

import com.example.songservice.entity.Playlist;
import com.example.songservice.entity.Song;
import com.example.songservice.event.PlaylistsReadyEvent;
import com.example.songservice.repositories.PlaylistRepository;
import com.example.songservice.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SongDataInitializer implements ApplicationListener<PlaylistsReadyEvent> {
    private final SongService songService;
    private final PlaylistRepository playlistRepository;
    private volatile boolean initializationComplete = false;

    @Autowired
    public SongDataInitializer(SongService songService, PlaylistRepository playlistRepository) {
        this.songService = songService;
        this.playlistRepository = playlistRepository;
    }


    @Override
    public void onApplicationEvent(PlaylistsReadyEvent event) {
        if (!initializationComplete) {
            initializeSongs();
            initializationComplete = true;
        }
    }

    private boolean arePlaylistsReady() {
        return playlistRepository.findById(UUID.fromString("1d9c5400-a8fa-4e22-8453-d5d08c74eaf5")).isPresent() &&
                playlistRepository.findById(UUID.fromString("2f97c370-f8ea-42d7-8769-d8f1f1c2ef68")).isPresent();
    }

    private void initializeSongs() {
        Playlist playlist1 = playlistRepository.findById(
                UUID.fromString("1d9c5400-a8fa-4e22-8453-d5d08c74eaf5"))
                .orElseThrow(() -> new RuntimeException("Playlist 1 not found - retrying..."));
        Playlist playlist2 = playlistRepository.findById(
                UUID.fromString("2f97c370-f8ea-42d7-8769-d8f1f1c2ef68"))
                .orElseThrow(() -> new RuntimeException("Playlist 2 not found - retrying..."));


        Song song1 = Song.builder()
                .id(UUID.fromString("36a850c8-5cfd-4a3a-9e5d-d7ac2a69dc9b"))
                .title("Run")
                .artist("Joji")
                .length(185)
                .genre("Lo-Fi")
                .playlist(playlist1)
                .build();

        Song song2 = Song.builder()
                .id(UUID.fromString("47b2d314-cf8c-40ea-85a8-4a40a1e0f308"))
                .title("Let You Down")
                .artist("Dawid Podsiadlo")
                .length(195)
                .genre("Electronic")
                .playlist(playlist1)
                .build();

        Song song3 = Song.builder()
                .id(UUID.fromString("567c9f9a-6c79-4d1c-8c3b-5d3152c7ef45"))
                .title("Count Me Out")
                .artist("Kendrick Lamar")
                .length(210)
                .genre("Rap")
                .playlist(playlist2)
                .build();

        Song song4 = Song.builder()
                .id(UUID.fromString("68e79172-b3d5-49c2-a7b4-681f287efa22"))
                .title("Middle Child")
                .artist("J. Cole")
                .length(245)
                .genre("Hip-hop/Rap")
                .playlist(playlist2)
                .build();

        songService.create(song1);
        songService.create(song2);
        songService.create(song3);
        songService.create(song4);

    }
}


