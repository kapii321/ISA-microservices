package com.example.demo.function;

import com.example.demo.dto.PlaylistCollectionDTO;
import com.example.demo.entity.Playlist;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class PlaylistToCollectionMapper implements Function<List<Playlist>, PlaylistCollectionDTO> {

    @Override
    public PlaylistCollectionDTO apply(List<Playlist> entities) {
        return PlaylistCollectionDTO.builder()
                .playlists(entities.stream()
                        .map(playlist -> PlaylistCollectionDTO.Playlist.builder()
                                .id(playlist.getId())
                                .name(playlist.getName())
                                .rank(playlist.getRank())
                                .build())
                        .toList())
                .build();
    }

}
