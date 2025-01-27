package com.example.playlistservice.repositories;

import com.example.playlistservice.entity.Playlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlaylistBackupRepository extends MongoRepository<Playlist, UUID> {
}
