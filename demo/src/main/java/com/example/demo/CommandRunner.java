package com.example.demo;

import com.example.demo.entity.Song;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.UUID;

@Component
public class CommandRunner implements CommandLineRunner {

    private final PlaylistService playlistService;
    private final SongService songService;
    private final DataInitializer dataInitializer;

    @Autowired
    public CommandRunner(PlaylistService playlistService, SongService songService, DataInitializer dataInitializer) {
        this.playlistService = playlistService;
        this.songService = songService;
        this.dataInitializer = dataInitializer;
    }

    @Override
    public void run(String... args) {
        System.out.println("Welcome to the command runner");
        dataInitializer.initialize();
        System.out.println("Welcome to the Music App!");

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            printCommands();
            System.out.print("Enter command: ");
            String command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "list playlists":
                    listPlaylists();
                    break;
                case "list songs":
                    listSongs();
                    break;
                case "add song":
                    addSong(scanner);
                    break;
                case "delete song":
                    deleteSong(scanner);
                    break;
                case "delete playlist":
                    deletePlaylist(scanner);
                    break;
                case "exit":
                    exit = true;
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }

        scanner.close();
    }

    private void printCommands() {
        System.out.println("\nAvailable commands:");
        System.out.println("1. list playlists - List all playlists");
        System.out.println("2. list songs - List all songs");
        System.out.println("3. add song - Add a new song to a playlist");
        System.out.println("4. delete song - Delete a song");
        System.out.println("5. delete playlist - Delete a playlist");
        System.out.println("6. exit - Exit the application\n");
    }

    private void listPlaylists() {
        var playlists = playlistService.getAllPlaylists();
        if (playlists.isEmpty()) {
            System.out.println("No playlists found.");
        } else {
            playlists.forEach(System.out::println);
        }
    }

    private void listSongs() {
        var songs = songService.findAll();
        if (songs.isEmpty()) {
            System.out.println("No songs found.");
        } else {
            songs.forEach(System.out::println);
        }
    }

    private void addSong(Scanner scanner) {
        System.out.println("Enter song details:");

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Artist: ");
        String artist = scanner.nextLine();

        System.out.print("Length (seconds): ");
        int length = Integer.parseInt(scanner.nextLine());

        System.out.print("Genre: ");
        String genre = scanner.nextLine();

        System.out.println("Available playlists:");
        listPlaylists();

        System.out.print("Enter playlist ID to add the song to: ");
        String playlistId = scanner.nextLine();

        var playlistOpt = playlistService.getPlaylistById(UUID.fromString(playlistId));
        if (playlistOpt.isPresent()) {
            var playlist = playlistOpt.get();
            Song newSong = Song.builder()
                    .id(UUID.randomUUID())
                    .title(title)
                    .artist(artist)
                    .length(length)
                    .genre(genre)
                    .playlist(playlist)
                    .build();
            songService.create(newSong);
            playlist.getSongs().add(newSong);
            playlistService.createPlaylist(playlist);
            System.out.println("Song added successfully.");
        } else {
            System.out.println("Playlist not found.");
        }
    }

    private void deleteSong(Scanner scanner) {
        System.out.print("Enter song ID to delete: ");
        String songId = scanner.nextLine();

        var songOpt = songService.findByID(UUID.fromString(songId));
        if (songOpt.isPresent()) {
            songService.delete(UUID.fromString(songId));
            System.out.println("Song deleted successfully.");
        } else {
            System.out.println("Song not found.");
        }
    }

    private void deletePlaylist(Scanner scanner) {
        System.out.print("Enter playlist ID to delete: ");
        String playlistId = scanner.nextLine();

        var playlistOpt = playlistService.getPlaylistById(UUID.fromString(playlistId));
        if (playlistOpt.isPresent()) {
            playlistService.deletePlaylist(UUID.fromString(playlistId));
            System.out.println("Playlist deleted successfully.");
        } else {
            System.out.println("Playlist not found.");
        }
    }
}

