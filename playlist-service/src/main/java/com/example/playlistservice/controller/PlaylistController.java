package com.example.playlistservice.controller;


import com.example.playlistservice.dto.*;
import com.example.playlistservice.function.*;
import com.example.playlistservice.entity.Playlist;
import com.example.playlistservice.services.PlaylistEventService;
import com.example.playlistservice.services.PlaylistService;
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
@RequestMapping("/playlists")
public class PlaylistController {
    private static final Logger logger = LoggerFactory.getLogger(PlaylistController.class);
    @Value("${eureka.instance.instanceId}")
    private String instanceId;
    private final PlaylistService playlistService;

    private final PlaylistReadMapper playlistReadMapper;
    private final PlaylistToCollectionMapper playlistToCollectionMapper;
    private final PlaylistCreateUpdateMapper playlistCreateUpdateMapper;
    private final PlaylistEventService eventService;




    @Autowired
    public PlaylistController(PlaylistService playlistService, PlaylistToCollectionMapper playlistToCollectionMapper, PlaylistReadMapper playlistReadMapper, PlaylistCreateUpdateMapper playlistCreateUpdateMapper, PlaylistEventService eventService) {
        this.playlistService = playlistService;
        this.playlistReadMapper = playlistReadMapper;
        this.playlistToCollectionMapper = playlistToCollectionMapper;
        this.playlistCreateUpdateMapper = playlistCreateUpdateMapper;
        this.eventService = eventService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PlaylistCollectionDTO getPlaylists(){
        logger.info("Request handled by instance: {}", instanceId);
        return playlistToCollectionMapper.apply(playlistService.getAllPlaylists());
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PlaylistReadDTO getPlaylistById(@PathVariable UUID id){
        logger.info("Request handled by instance: {}", instanceId);
        return playlistService.getPlaylistById(id)
                .map(playlistReadMapper)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlaylist(@PathVariable UUID id){
        logger.info("Request handled by instance: {}", instanceId);
        playlistService.getPlaylistById(id)
                .ifPresentOrElse(
                        playlist -> {
                            playlistService.deletePlaylist(id);
                            eventService.notifyPlaylistDeleted(id);
                        },
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaylistReadDTO createPlaylist(@RequestBody PlaylistCreateUpdateDTO playlistCreateUpdateDTO){
        logger.info("Request handled by instance: {}", instanceId);
        Playlist playlist = playlistCreateUpdateMapper.apply(playlistCreateUpdateDTO);
        playlistService.createPlaylist(playlist);
        eventService.notifyPlaylistCreated(playlist.getId(), playlist.getName());
        return playlistReadMapper.apply(playlist);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlaylistReadDTO updatePlaylist(@PathVariable UUID id, @RequestBody PlaylistCreateUpdateDTO dto) {
        logger.info("Request handled by instance: {}", instanceId);
        Playlist existingPlaylist = playlistService.getPlaylistById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist not found"));
        Playlist updatedPlaylist = playlistCreateUpdateMapper.apply(dto);
        updatedPlaylist.setId(existingPlaylist.getId());
        playlistService.updatePlaylist(updatedPlaylist);
        return playlistReadMapper.apply(updatedPlaylist);
    }






}
