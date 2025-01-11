package com.example.demo.function;

import com.example.demo.dto.SongCreateUpdateDTO;
import com.example.demo.entity.Playlist;
import com.example.demo.entity.Song;
import com.example.demo.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Function;

@Component
public class SongCreateUpdateMapper implements Function<SongCreateUpdateDTO, Song> {
    @Override
    public Song apply(SongCreateUpdateDTO songCreateUpdateDTO) {
        return Song.builder()
                .title(songCreateUpdateDTO.getTitle())
                .artist(songCreateUpdateDTO.getArtist())
                .length(songCreateUpdateDTO.getLength())
                .genre(songCreateUpdateDTO.getGenre())
                .build();
    }
}
