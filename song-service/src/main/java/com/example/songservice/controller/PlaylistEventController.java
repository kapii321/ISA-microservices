package com.example.songservice.controller;


import com.example.songservice.entity.Playlist;
import com.example.songservice.event.PlaylistEvent;
import com.example.songservice.repositories.PlaylistRepository;
import com.example.songservice.services.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class PlaylistEventController {
    private static final Logger logger = LoggerFactory.getLogger(PlaylistEventController.class);
    @Value("${eureka.instance.instanceId}")
    private String instanceId;
    private final PlaylistRepository playlistRepository;
    private final SongService songService;

    @Autowired
    public PlaylistEventController(PlaylistRepository playlistRepository, SongService songService) {
        this.playlistRepository = playlistRepository;
        this.songService = songService;
    }

    @PostMapping("/playlist")
    @ResponseStatus(HttpStatus.OK)
    public void handlePlaylistEvent(@RequestBody PlaylistEvent event) {
        if ("CREATE".equals(event.eventType())){
            Playlist playlist = new Playlist(event.playlistId(), event.name());
            playlistRepository.save(playlist);
            logger.info("Request handled by instance: {}", instanceId);


        } else if ("DELETE".equals(event.eventType())){
            songService.findByPlaylistId(event.playlistId())
                            .forEach(song -> songService.delete(song.getId()));
            playlistRepository.deleteById(event.playlistId());
            logger.info("Request handled by instance: {}", instanceId);
        }
    }


}
