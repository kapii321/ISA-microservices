package com.example.playlistservice.function;

import com.example.playlistservice.dto.PlaylistReadDTO;
import com.example.playlistservice.entity.Playlist;
import org.springframework.stereotype.Component;


import java.util.function.Function;

@Component
public class PlaylistReadMapper implements Function<Playlist, PlaylistReadDTO> {
    @Override
    public PlaylistReadDTO apply(Playlist entity) {
        return PlaylistReadDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .rank(entity.getRank())
                .build();
    }
}
