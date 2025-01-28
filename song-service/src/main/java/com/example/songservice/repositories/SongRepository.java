package com.example.songservice.repositories;

import com.example.songservice.entity.Playlist;
import com.example.songservice.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SongRepository extends MongoRepository<Song, UUID> {
    List<Song> findByPlaylistId(UUID playlistId);
}
