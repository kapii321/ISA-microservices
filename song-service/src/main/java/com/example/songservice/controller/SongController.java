package com.example.songservice.controller;

import com.example.songservice.dto.*;
import com.example.songservice.entity.Playlist;
import com.example.songservice.function.SongCreateUpdateMapper;
import com.example.songservice.function.SongReadMapper;
import com.example.songservice.function.SongToCollectionMapper;
import com.example.songservice.entity.Song;
import com.example.songservice.repositories.PlaylistRepository;
import com.example.songservice.services.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class SongController {
    private static final Logger logger = LoggerFactory.getLogger(SongController.class);
    @Value("${eureka.instance.instanceId}")
    private String instanceId;
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
        logger.info("Request handled by instance: {}", instanceId);
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
        logger.info("Request handled by instance: {}", instanceId);

        return songReadMapper.apply(song);
    }

    @GetMapping("/songs/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SongReadDTO getSongById(@PathVariable UUID id) {
        logger.info("Request handled by instance: {}", instanceId);
        return songService.findByID(id)
                .map(songReadMapper)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/songs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SongReadDTO updateSongById(@PathVariable UUID id, @RequestBody SongCreateUpdateDTO dto) {
        logger.info("Request handled by instance: {}", instanceId);
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
        logger.info("Request handled by instance: {}", instanceId);
        songService.findByID(id)
                .ifPresentOrElse(
                        song -> songService.delete(id),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }

    @GetMapping("/playlists/{playlistId}/songs")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SongCollectionDTO getPlaylistSongs(@PathVariable UUID playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist not found"));

        List<Song> songs = songService.findByPlaylistId(playlistId);

        if(songs.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return songToCollectionMapper.apply(songs);
    }


}
