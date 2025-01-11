package com.example.songservice.function;

import com.example.songservice.dto.SongReadDTO;
import com.example.songservice.entity.Song;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class SongReadMapper implements Function<Song, SongReadDTO> {

    @Override
    public SongReadDTO apply(Song song) {
        // Mapping Song entity to SongReadDTO
        return SongReadDTO.builder()
                .id(song.getId())
                .title(song.getTitle())
                .artist(song.getArtist())
                .length(song.getLength())
                .genre(song.getGenre())
                .playlistId(song.getPlaylist().getId())
                .build();
    }
}
