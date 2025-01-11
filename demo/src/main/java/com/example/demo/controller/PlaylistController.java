package com.example.demo.controller;


import com.example.demo.dto.*;
import com.example.demo.function.*;
import com.example.demo.entity.Playlist;
import com.example.demo.entity.Song;
import com.example.demo.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {
    private final PlaylistService playlistService;

    private final PlaylistReadMapper playlistReadMapper;
    private final PlaylistToCollectionMapper playlistToCollectionMapper;
    private final PlaylistCreateUpdateMapper playlistCreateUpdateMapper;
    private final SongToCollectionMapper songToCollectionMapper;



    @Autowired
    public PlaylistController(PlaylistService playlistService, PlaylistToCollectionMapper playlistToCollectionMapper, PlaylistReadMapper playlistReadMapper, PlaylistCreateUpdateMapper playlistCreateUpdateMapper, SongToCollectionMapper songToCollectionMapper) {
        this.playlistService = playlistService;
        this.playlistReadMapper = playlistReadMapper;
        this.playlistToCollectionMapper = playlistToCollectionMapper;
        this.playlistCreateUpdateMapper = playlistCreateUpdateMapper;
        this.songToCollectionMapper = songToCollectionMapper;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PlaylistCollectionDTO getPlaylists(){
        return playlistToCollectionMapper.apply(playlistService.getAllPlaylists());
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PlaylistReadDTO getPlaylistById(@PathVariable UUID id){
        return playlistService.getPlaylistById(id)
                .map(playlistReadMapper)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlaylist(@PathVariable UUID id){
            playlistService.getPlaylistById(id)
                    .ifPresentOrElse(
                            playlist -> playlistService.deletePlaylist(id),
                            () -> {
                                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                            }
                    );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaylistReadDTO createPlaylist(@RequestBody PlaylistCreateUpdateDTO playlistCreateUpdateDTO){
        Playlist playlist = playlistCreateUpdateMapper.apply(playlistCreateUpdateDTO);
        playlistService.createPlaylist(playlist);
        return playlistReadMapper.apply(playlist);
    }

    @GetMapping("/{id}/songs")
    @ResponseStatus(HttpStatus.OK)
    public SongCollectionDTO getSongsInPlaylist(@PathVariable UUID id){
        Playlist playlist = playlistService.getPlaylistById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<Song> songs = playlist.getSongs();

        if (songs.isEmpty()) {
            // Return a response indicating the playlist is empty
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "The playlist exists but contains no songs.");
        }

        // Map the list of songs to the DTO
        return songToCollectionMapper.apply(songs);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlaylistReadDTO updatePlaylist(@PathVariable UUID id, @RequestBody PlaylistCreateUpdateDTO dto) {
        Playlist existingPlaylist = playlistService.getPlaylistById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist not found"));
        Playlist updatedPlaylist = playlistCreateUpdateMapper.apply(dto);
        updatedPlaylist.setId(existingPlaylist.getId());
        playlistService.updatePlaylist(updatedPlaylist);
        return playlistReadMapper.apply(updatedPlaylist);
    }






}
