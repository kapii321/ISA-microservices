package com.example.playlistservice.services;

import com.example.playlistservice.event.PlaylistEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

@Service
public class PlaylistEventService {
    private final RestTemplate restTemplate;


    @Autowired
    public PlaylistEventService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void notifyPlaylistCreated(UUID playlistId, String name) {

        var payload = new PlaylistEvent(playlistId, name, "CREATE");
        restTemplate.postForObject("lb://song-service/events/playlist", payload, Void.class);
    }

    public void notifyPlaylistDeleted(UUID playlistId){
        var payload = new PlaylistEvent(playlistId, null, "DELETE");
        restTemplate.postForObject("lb://song-service/events/playlist", payload, Void.class);
    }

}
