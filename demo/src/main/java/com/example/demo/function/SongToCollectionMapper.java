package com.example.demo.function;

import com.example.demo.dto.SongCollectionDTO;
import com.example.demo.entity.Song;
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
