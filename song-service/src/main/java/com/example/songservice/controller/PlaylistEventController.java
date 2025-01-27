package com.example.songservice.controller;


import com.example.songservice.entity.Playlist;
import com.example.songservice.event.PlaylistEvent;
import com.example.songservice.repositories.PlaylistBackupRepository;
import com.example.songservice.repositories.PlaylistRepository;
import com.example.songservice.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class PlaylistEventController {
    private final PlaylistRepository playlistRepository;
    private final PlaylistBackupRepository playlistBackupRepository;
    private final SongService songService;

    @Autowired
    public PlaylistEventController(PlaylistRepository playlistRepository, SongService songService, PlaylistBackupRepository playlistBackupRepository) {
        this.playlistRepository = playlistRepository;
        this.songService = songService;
        this.playlistBackupRepository = playlistBackupRepository;
    }

    @PostMapping("/playlist")
    @ResponseStatus(HttpStatus.OK)
    public void handlePlaylistEvent(@RequestBody PlaylistEvent event) {
        if ("CREATE".equals(event.eventType())){
            Playlist playlist = new Playlist(event.playlistId(), event.name());
            playlistRepository.save(playlist);
            playlistBackupRepository.save(playlist);


        } else if ("DELETE".equals(event.eventType())){
            songService.findByPlaylistId(event.playlistId())
                            .forEach(song -> songService.delete(song.getId()));
            playlistRepository.deleteById(event.playlistId());
            playlistBackupRepository.deleteById(event.playlistId());
        }
    }


}
