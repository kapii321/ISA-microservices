package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.Playlist;
import com.example.demo.function.SongCreateUpdateMapper;
import com.example.demo.function.SongReadMapper;
import com.example.demo.function.SongToCollectionMapper;
import com.example.demo.entity.Song;
import com.example.demo.repositories.PlaylistRepository;
import com.example.demo.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class SongController {
    private final SongService songService;
    private final SongCreateUpdateMapper songCreateUpdateMapper;
    private final SongReadMapper songReadMapper;
    private final SongToCollectionMapper songToCollectionMapper;
    private final PlaylistRepository playlistRepository;


    @Autowired
    public SongController(SongService songService, SongCreateUpdateMapper songCreateUpdateMapper, SongReadMapper songReadMapper, SongToCollectionMapper songToCollectionMapper, PlaylistRepository playlistRepository) {
        this.songService = songService;
        this.songCreateUpdateMapper = songCreateUpdateMapper;
        this.songReadMapper = songReadMapper;
        this.songToCollectionMapper = songToCollectionMapper;
        this.playlistRepository = playlistRepository;
    }

    @GetMapping("/songs")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SongCollectionDTO getSongs(){
        return songToCollectionMapper.apply(songService.findAll());
    }

    @PostMapping("/playlists/{playlistId}/songs")
    @ResponseStatus(HttpStatus.CREATED)
    public SongReadDTO createSong(
            @PathVariable UUID playlistId,
            @RequestBody SongCreateUpdateDTO songCreateUpdateDTO) {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist not found"));

        Song song = songCreateUpdateMapper.apply(songCreateUpdateDTO);
        song.setPlaylist(playlist);
        songService.create(song);

        return songReadMapper.apply(song);
    }

    @GetMapping("/songs/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SongReadDTO getSongById(@PathVariable UUID id) {
        return songService.findByID(id)
                .map(songReadMapper)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/songs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SongReadDTO updateSongById(@PathVariable UUID id, @RequestBody SongCreateUpdateDTO dto) {
        Song existingSong = songService.findByID(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Song updatedSong = songCreateUpdateMapper.apply(dto);
        updatedSong.setId(existingSong.getId());
        updatedSong.setPlaylist(existingSong.getPlaylist()); // Keep original playlist

        songService.update(updatedSong);

        return songReadMapper.apply(updatedSong);
    }

    @DeleteMapping("/songs/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSong(@PathVariable UUID id) {
        songService.findByID(id)
                .ifPresentOrElse(
                        song -> songService.delete(id),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }


}
