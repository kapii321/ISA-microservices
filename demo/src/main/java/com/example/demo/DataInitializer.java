package com.example.demo;

import com.example.demo.entity.Playlist;
import com.example.demo.entity.Song;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class DataInitializer {

    private final SongService songService;

    private final PlaylistService playlistService;

    @Autowired
    public DataInitializer(SongService songService, PlaylistService playlistService) {
        this.songService = songService;
        this.playlistService = playlistService;
    }


    public void initialize(){
        Playlist playlist1 = Playlist.builder()
                .id(UUID.fromString("1d9c5400-a8fa-4e22-8453-d5d08c74eaf5"))
                .name("Chill Tunes")
                .rank(1)
                .songs(new ArrayList<>())
                .build();

        Playlist playlist2 = Playlist.builder()
                .id(UUID.fromString("2f97c370-f8ea-42d7-8769-d8f1f1c2ef68"))
                .name("Workout Beats")
                .rank(2)
                .songs(new ArrayList<>())
                .build();

        playlistService.createPlaylist(playlist1);
        playlistService.createPlaylist(playlist2);

        System.out.println("Playlist created:");
        playlistService.getAllPlaylists().forEach(System.out::println);


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

        playlist1.getSongs().add(song1);
        playlist1.getSongs().add(song2);

        playlist2.getSongs().add(song3);
        playlist2.getSongs().add(song4);

        System.out.println("songs created:");
        songService.findAll().forEach(System.out::println);

        playlistService.updatePlaylist(playlist1);
        playlistService.updatePlaylist(playlist2);

        System.out.println("Playlist updated:");
        playlistService.getAllPlaylists().forEach(System.out::println);

        System.out.println("Playlists and Songs initialized:");
        System.out.println(playlist1.getSongs());
        System.out.println(playlist2);

        System.out.println("Services went through");
    }
}
