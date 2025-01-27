package com.example.songservice.function;

import com.example.songservice.dto.SongCollectionDTO;
import com.example.songservice.entity.Song;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class SongToCollectionMapper implements Function<List<Song>, SongCollectionDTO> {

    @Override
    public SongCollectionDTO apply(List<Song> entities) {
        return SongCollectionDTO.builder()
                .songs(entities.stream()
                        .map(song -> SongCollectionDTO.Song.builder()
                                .id(song.getId())
                                .title(song.getTitle())
                                .build())
                        .toList())
                .build();

    }

}
