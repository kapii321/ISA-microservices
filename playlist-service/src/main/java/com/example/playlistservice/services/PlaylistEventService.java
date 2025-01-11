package com.example.playlistservice.services;

import com.example.playlistservice.event.PlaylistEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

@Service
public class PlaylistEventService {
    private final RestTemplate restTemplate;
    private final String songServiceUrl;

    public PlaylistEventService(){
        this.restTemplate = new RestTemplate();
        this.songServiceUrl = "http://localhost:8082";
    }

    public void notifyPlaylistCreated(UUID playlistId, String name) {

        var payload = new PlaylistEvent(playlistId, name, "CREATE");
        restTemplate.postForObject(songServiceUrl + "/events/playlist", payload, Void.class);
    }

    public void notifyPlaylistDeleted(UUID playlistId){
        var payload = new PlaylistEvent(playlistId, null, "DELETE");
        restTemplate.postForObject(songServiceUrl + "/events/playlist", payload, Void.class);
    }
}
