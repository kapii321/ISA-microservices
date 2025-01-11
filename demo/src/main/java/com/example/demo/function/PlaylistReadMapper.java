package com.example.demo.function;

import com.example.demo.dto.PlaylistReadDTO;
import com.example.demo.dto.SongCollectionDTO;
import com.example.demo.dto.SongReadDTO;
import com.example.demo.entity.Playlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PlaylistReadMapper implements Function<Playlist, PlaylistReadDTO> {
    private final SongToCollectionMapper songToCollectionMapper;

    @Autowired
    public PlaylistReadMapper(SongToCollectionMapper songToCollectionMapper) {
        this.songToCollectionMapper = songToCollectionMapper;
    }

    @Override
    public PlaylistReadDTO apply(Playlist entity) {
        SongCollectionDTO songCollection = entity.getSongs() != null && !entity.getSongs().isEmpty()
                ? songToCollectionMapper.apply(entity.getSongs())
                : SongCollectionDTO.builder().build();

        return PlaylistReadDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .rank(entity.getRank())
                .songs(songCollection)
                .build();
    }
}
