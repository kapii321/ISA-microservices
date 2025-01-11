package com.example.demo.repositories;

import com.example.demo.entity.Playlist;
import com.example.demo.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SongRepository extends JpaRepository<Song, UUID> {
    Optional<Song> findByPlaylist(Playlist playlist);
}
