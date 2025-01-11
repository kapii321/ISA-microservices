package com.example.demo.function;
import com.example.demo.dto.PlaylistCreateUpdateDTO;
import com.example.demo.entity.Playlist;
import org.springframework.stereotype.Component;


import java.util.function.Function;

@Component
public class PlaylistCreateUpdateMapper implements Function<PlaylistCreateUpdateDTO, Playlist> {

    @Override
    public Playlist apply(PlaylistCreateUpdateDTO playlistCreateUpdateDTO) {
        // Mapping PlaylistCreateUpdateDTO to Playlist entity
        return Playlist.builder()
                .name(playlistCreateUpdateDTO.getName())
                .rank(playlistCreateUpdateDTO.getRank())
                .build();
    }
}

